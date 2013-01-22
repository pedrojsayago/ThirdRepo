package com.thomsonreuters.grc.platform.showcase.jta.service;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.thomsonreuters.grc.platform.showcase.jta.dl.SampleDomainObjectRepository;
import com.thomsonreuters.grc.platform.showcase.jta.domain.SampleDomainObject;
import com.thomsonreuters.grc.platform.showcase.jta.domain.dto.SampleInboundRequest;
import com.thomsonreuters.grc.platform.showcase.jta.domain.dto.SampleOutboundResponse;

public class MessageProcessingServiceImpl implements MessageProcessingService {

    private SampleDomainObjectRepository objectRepository;

    private DownstreamService               responseSender;

    @Transactional(readOnly = false, propagation = Propagation.MANDATORY)
    public void processRequest(SampleInboundRequest request) {
        // First XA transactional participant: request (inbound messaging resource)

        // Second XA transactional participant: DB resource
        SampleDomainObject existingObject = new SampleDomainObject();
        existingObject.setId("1");
        // SampleDomainObject existingObject = objectRepository.findOne("1");

        existingObject.setSampleField(request.getRequestField());

        existingObject = objectRepository.saveAndFlush(existingObject);

        // Third XA transactional participant: response (outbound messaging resource)
        SampleOutboundResponse response = new SampleOutboundResponse();
        response.setResponseField(String.format("Set field: %s", existingObject.getSampleField()));

        // Note we explicitly invoke a send-response call rather than relying on implicit response via a return value
        // from this method. This is done as the transactional boundary is on this service method, so our transaction is
        // closed after the method returns. We however require the response sending to be a participant within the
        // transaction.
        responseSender.processFurtherMessage(response);
    }

    public SampleDomainObjectRepository getObjectRepository() {
        return objectRepository;
    }

    @Required
    public void setObjectRepository(SampleDomainObjectRepository objectRepository) {
        this.objectRepository = objectRepository;
    }

    public DownstreamService getResponseSender() {
        return responseSender;
    }

    @Required
    public void setResponseSender(DownstreamService responseSender) {
        this.responseSender = responseSender;
    }

}
