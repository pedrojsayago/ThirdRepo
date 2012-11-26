/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.ums.domain.Group;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 * Base class for service tests
 */
public class BaseServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    protected static final String FOO = "foo";


    protected Group getGroupFoo() {
        Group group = new Group.Builder().withName("FOO").withId(FOO).build();
        return group;
    }
}
