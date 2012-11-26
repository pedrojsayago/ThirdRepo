/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.dl.repository.UserRepository;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.fsp.ums.domain.preference.UserPreference;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;


/**
 * Tests repository for {@link UserPreference}
 *
 * @author Pedro Sayago &lt;pedro.sayago@thomsonreuters.com&gt;
 * @since 20 August 2012
 */
@Transactional
@Ignore
public class UserPreferenceRepositoryTest extends BaseDataTest {

    private static final Map<String, String> map = new HashMap<String, String>();

    /**
     * User Repository to Load Users
     */
    @Autowired
    private UserRepository userRepository;

    /**
     * Preference Repository to be tested
     */
    @Autowired
    UserPreferenceRepository preferenceRepository;

    /**
     * Different associations
     */
    private String entityType;
    private User user;
    private String flag;
    private String userId = "USER_ID";

    /**
     * Initialise Test Data
     */
    @Before
    public void init() {
        /**
         * Load User
         */
        user = userRepository.findByUsername("username1@thomsonreuters.com");

        /**
         * Load Map
         */
        entityType = "INDIVIDUAL";
        flag = "flag";
        map.put(entityType, flag);
    }

    /**
     * Can Find a User Preference for given UserPreferenceType and User
     */
    @Test
    public void canFindByTypeAndUser() {
//        /**
//         * Add new User Preference
//         */
//        UserPreference userPreference = new UserPreference();
//        userPreference.setType(UserPreferenceType.FOO);
//        userPreference.setUser(user);
//        userPreference.setEntries(map);
//
//        UserPreference savedUser = preferenceRepository.save(userPreference);
//        assertNotNull("User not saved in db", savedUser.getId());
//
//        List<UserPreference> userPreferences = preferenceRepository.findByUserIdAndTypeContaining("USER_ID",
//                UserPreferenceType.FOO);
//        assertTrue(!userPreferences.isEmpty());
    }

    /**
     * Can Add User Preference
     */
    @Test
    public void canAddUserPreferences() {
        /**
         * Add new User Preference
         */
        UserPreference userPreference = new UserPreference.Builder()
                .withUser(user)
                .withType(UserPreferenceType.FOO)
                .withEntries(map)
                .withGeneratedId()
                .build();

        UserPreference savedUser = preferenceRepository.save(userPreference);
        assertNotNull("User not saved in db", savedUser.getId());
    }

    /**
     * Can Delete User Preference
     */
    @Test
    public void canDeleteUserPreferences() {
        UserPreference userPreference = new UserPreference.Builder()
                .withUser(user)
                .withType(UserPreferenceType.FOO)
                .withEntries(map)
                .withGeneratedId()
                .build();

        UserPreference savedUser = preferenceRepository.save(userPreference);
        assertNotNull("User not saved in db", savedUser.getId());

        /**
         * Now Delete the User Preference
         */
        String uuid = savedUser.getId();
        preferenceRepository.delete(uuid);

        /**
         * Verify by loading the Name
         */
        UserPreference deletedUser = preferenceRepository.findOne(uuid);
        assertNull("User must have been deleted!!!", deletedUser);
    }
}
