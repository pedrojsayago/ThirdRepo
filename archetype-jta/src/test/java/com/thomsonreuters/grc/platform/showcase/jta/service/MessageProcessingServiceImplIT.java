package com.thomsonreuters.grc.platform.showcase.jta.service;

import com.thomsonreuters.grc.platform.showcase.jta.dl.SampleDomainObjectRepository;
import com.thomsonreuters.grc.platform.showcase.jta.domain.SampleDomainObject;
import com.thomsonreuters.grc.platform.showcase.jta.domain.dto.SampleInboundRequest;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.Message;
import org.springframework.integration.MessageChannel;
import org.springframework.integration.channel.AbstractMessageChannel;
import org.springframework.integration.channel.interceptor.ChannelInterceptorAdapter;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

public class MessageProcessingServiceImplIT implements ApplicationContextAware {

    private static ClassPathXmlApplicationContext brokerContext;

    private static ClassPathXmlApplicationContext applicationContext;

    private AbstractMessageChannel                clientMessageChannel;

    private AbstractMessageChannel                inboundMessageChannel;

    private AbstractMessageChannel                outboundMessageChannel;

    @Autowired
    private SampleDomainObjectRepository          sampleDomainObjectRepository;

    private TransactionTemplate                   transactionTemplate;

    private class SentMessageCounter extends ChannelInterceptorAdapter {

        private final AtomicInteger  sentMessageCount = new AtomicInteger();

        private final CountDownLatch countDownLatch;

        private volatile Message<?>  lastSeenMessage;

        private SentMessageCounter(CountDownLatch countDownLatch) {
            this.countDownLatch = countDownLatch;
        }

        @Override
        public Message<?> preSend(Message<?> message, MessageChannel channel) {
            this.lastSeenMessage = message;

            sentMessageCount.incrementAndGet();
            countDownLatch.countDown();
            return message;
        }
    }

    private final CountDownLatch     inboundMessageLatch  = new CountDownLatch(1);

    private final SentMessageCounter inboundCounter       = new SentMessageCounter(inboundMessageLatch);

    private final CountDownLatch     outboundMessageLatch = new CountDownLatch(1);

    private final SentMessageCounter outboundCounter      = new SentMessageCounter(outboundMessageLatch);

    @Autowired
    public void setTransactionManager(PlatformTransactionManager transactionManager) {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }

    @BeforeClass
    public static void setupApplicationContexts() {
        brokerContext = new ClassPathXmlApplicationContext("broker-context.xml");
        brokerContext.start();

        applicationContext = new ClassPathXmlApplicationContext("deploy-context.xml", "jms-client-context.xml");
        applicationContext.start();
    }

    @AfterClass
    public static void shutdownApplicationContexts() {
        System.out.println("Closing application context");
        if (applicationContext != null) {
            applicationContext.stop();
            applicationContext.close();
        }

        System.out.println("Closing broker context");
        if (brokerContext != null) {
            brokerContext.stop();
            brokerContext.close();
        }
    }

    @Before
    public void initialise() {
        applicationContext.getAutowireCapableBeanFactory().autowireBean(this);
        applicationContext.getAutowireCapableBeanFactory().initializeBean(this, "testCase");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.clientMessageChannel = applicationContext.getBean("integration.clientOutboundChannel",
                AbstractMessageChannel.class);

        this.inboundMessageChannel = applicationContext.getBean("integration.channel.inbound",
                AbstractMessageChannel.class);
        this.inboundMessageChannel.addInterceptor(inboundCounter);

        this.outboundMessageChannel = applicationContext.getBean("integration.channel.outbound",
                AbstractMessageChannel.class);
        this.outboundMessageChannel.addInterceptor(outboundCounter);
    }

    // @Before
    public void setUpDatabaseState() {
        SampleDomainObject sampleDomainObject = new SampleDomainObject();
        sampleDomainObject.setId("1");
        sampleDomainObject.setSampleField("Initial field state");

        sampleDomainObjectRepository.save(sampleDomainObject);
    }

    @Test
    public void messagesAreCorrectlyRouted() throws InterruptedException {

        boolean requestSent = transactionTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                return clientMessageChannel.send(MessageBuilder
                        .withPayload(new SampleInboundRequest("Sample request!")).build(), 1000);
            }

        });
        assertThat(requestSent, is(true));

        inboundMessageLatch.await(1000, TimeUnit.MILLISECONDS);
        assertThat(inboundCounter.sentMessageCount.get(), is(1));
        assertThat(inboundCounter.lastSeenMessage, is(not(nullValue())));
        assertThat(inboundCounter.lastSeenMessage.getPayload(), is(not(nullValue())));
        assertThat(inboundCounter.lastSeenMessage.getPayload(), is(instanceOf(SampleInboundRequest.class)));
        assertThat(((SampleInboundRequest) inboundCounter.lastSeenMessage.getPayload()).getRequestField(),
                is(equalTo("Sample request!")));

        outboundMessageLatch.await(1000, TimeUnit.MILLISECONDS);
        assertThat(outboundCounter.sentMessageCount.get(), is(1));
        assertThat(outboundCounter.lastSeenMessage, is(not(nullValue())));
        assertThat(outboundCounter.lastSeenMessage.getPayload(), is(not(nullValue())));
        assertThat(outboundCounter.lastSeenMessage.getPayload(), is(instanceOf(String.class)));
        assertThat((String) outboundCounter.lastSeenMessage.getPayload(), containsString("Sample request!"));

        SampleDomainObject persistedObject = sampleDomainObjectRepository.findOne("1");
        assertThat(persistedObject, is(not(nullValue())));
        assertThat(persistedObject.getSampleField(), is(equalTo("Sample request!")));
    }

}
