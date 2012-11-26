package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.google.common.collect.ImmutableMap;
import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class DefaultUserPreferenceBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultUserPreferenceBuilderTest.class);

    @Test
    public void canCreateDefaultUserPreferenceBuilder() {

        DefaultUserPreference.Builder defaultUserPreferenceBuilder =
                new DefaultUserPreference.Builder();
        DefaultUserPreference defaultUserPreference = defaultUserPreferenceBuilder
                .withEntries(ImmutableMap.of("0", "UUID"))
                .withType(UserPreferenceType.FOO)
                .build();

        LOGGER.debug("this is the Default User Preference {}", defaultUserPreference);
    }
}
