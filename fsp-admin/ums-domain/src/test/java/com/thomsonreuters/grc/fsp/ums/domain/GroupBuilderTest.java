package com.thomsonreuters.grc.fsp.ums.domain;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class GroupBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupBuilderTest.class);

    @Test
    public void canCreateGroupBuilder() {

        Group.Builder groupBuilder = new Group.Builder();
        Group aGroup = groupBuilder
                .withLeaf(true)
                .withPath("/1/2")
                .withDepth(2)
                .withName("CLIENTGROUP")
                .build();

        LOGGER.debug("this is the group {}", aGroup);
    }
}
