/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * Base class for {@code Repository} tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:ums-dl-context-test.xml")
@TransactionConfiguration(defaultRollback = false, transactionManager = "data.transactionManager")
public abstract class BaseDataTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private PlatformTransactionManager transactionManager;

    protected TransactionTemplate transactionTemplate;

    @Before
    public final void initialise() {
        transactionTemplate = new TransactionTemplate(transactionManager);
    }
}
