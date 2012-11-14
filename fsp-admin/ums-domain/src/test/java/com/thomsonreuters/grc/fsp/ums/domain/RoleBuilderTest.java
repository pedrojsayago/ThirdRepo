package com.thomsonreuters.grc.fsp.ums.domain;

import com.google.common.collect.Sets;
import com.thomsonreuters.grc.fsp.common.base.type.ums.PermissionType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class RoleBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RoleBuilderTest.class);

    @Test
    public void canCreateUserBuilder() {

        Group aGroup = new Group.Builder().withDepth(2).withPath("/1/2/").build();

        Role.Builder roleBuilder = new Role.Builder();
        Role aRole = roleBuilder
                .withClient(aGroup)
                .withName("roleName")
                .withPermissions(Sets.newHashSet(PermissionType.INVESTIGATOR))
                .build();

        LOGGER.debug("this is the role {}", aRole);

        Group otherGroup = new Group.Builder().withDepth(3).withPath("/1/2/3/").build();
        Role otherRole = roleBuilder
                .withClient(otherGroup)
                .withPermissions(Sets.newHashSet(PermissionType.SCREENER))
                .withName("roleName2")
                .build();

        LOGGER.debug("this is the role {}", otherRole);
    }
}
