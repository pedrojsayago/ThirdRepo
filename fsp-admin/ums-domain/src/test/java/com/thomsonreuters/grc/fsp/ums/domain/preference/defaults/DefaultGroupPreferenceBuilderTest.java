package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.google.common.collect.ImmutableMap;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class DefaultGroupPreferenceBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultGroupPreferenceBuilderTest.class);

    @Test
    public void canCreateDefaultGroupPreferenceBuilder() {

        DefaultGroupPreference defaultGroupPreference = new DefaultGroupPreference.Builder()
                .withEntries(ImmutableMap.of("0", "UUID"))
                .withType(GroupPreferenceType.CLIENT_FIELDS)
                .build();

        LOGGER.debug("this is the default GroupPreference {}", defaultGroupPreference);
    }
}
