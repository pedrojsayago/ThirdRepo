package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class GroupPreferenceBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupPreferenceBuilderTest.class);

    @Test
    public void canCreateGroupPreferenceBuilder() {

        /**
         * Add new Group
         */
        Group aGroup = new Group.Builder().withName("Client").withPath("/1/2/").withDepth(2).build();

        GroupPreference.Builder groupPreferenceBuilder = new GroupPreference.Builder();
        GroupPreference groupPreference = groupPreferenceBuilder
                .withGroup(aGroup)
                .withType(GroupPreferenceType.CLIENT_FIELDS)
                .build();

        LOGGER.debug("this is the group Preference {}", groupPreference);
    }
}
