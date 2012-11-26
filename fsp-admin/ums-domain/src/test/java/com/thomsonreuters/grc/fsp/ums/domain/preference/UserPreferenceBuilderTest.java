package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class UserPreferenceBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserPreferenceBuilderTest.class);

    @Test
    public void canCreateUserPreferenceBuilder() {

        /**
         * Add new Group
         */
        Group aGroup = new Group.Builder().withName("Client").withPath("/1/2/").withDepth(2).build();

        /**
         * Add new User
         */
        User anUser = new User.Builder().withUsername("username3@thomsonreuters.com").withClient(aGroup).build();

        UserPreference.Builder userPreferenceBuilder = new UserPreference.Builder();
        UserPreference userPreference = userPreferenceBuilder
                .withUser(anUser)
                .withType(UserPreferenceType.FOO)
                .build();

        LOGGER.debug("this is the user Preference {}", userPreference);
    }
}
