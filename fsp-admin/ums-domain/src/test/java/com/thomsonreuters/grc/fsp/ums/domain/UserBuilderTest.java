package com.thomsonreuters.grc.fsp.ums.domain;

import com.google.common.collect.Sets;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class UserBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserBuilderTest.class);

    @Test
    public void canCreateUserBuilder() {

        Group aGroup = new Group.Builder().withDepth(2).withPath("/1/2/").build();

        User.Builder userBuilder = new User.Builder();
        User anUser = userBuilder
                .withUsername("CLIENT_NAME")
                .withClient(aGroup)
                .withGroups(Sets.newHashSet(aGroup))
                .build();

        LOGGER.debug("this is the user {}", anUser);
    }
}
