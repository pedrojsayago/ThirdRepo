package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.google.common.collect.ImmutableMap;
import com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class DefaultUserGroupPreferenceBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserGroupPreferenceBuilderTest.class);

    @Test
    public void canCreateDefaultUserGroupPreferenceBuilder() {

        DefaultUserGroupPreference.Builder defaultUserGroupPreferenceBuilder =
                new DefaultUserGroupPreference.Builder();
        DefaultUserGroupPreference defaultUserGroupPreference = defaultUserGroupPreferenceBuilder
                .withType(UserGroupPreferenceType.FOO)
                .withEntries(ImmutableMap.of("0", "UUID"))
                .build();

        LOGGER.debug("this is the default UserGroupPreference {}", defaultUserGroupPreference);
    }
}
