/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.google.common.collect.Sets;
import com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.preference.UserGroupPreference;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;


/**
 * Tests for {@link UserGroupPreferenceRepository}
 */
@Transactional
public class UserGroupPreferenceRepositoryTest extends BaseDataTest {

    private static final String GROUP_1_ID = "GROUP_1";

    private static final String USER_1_ID = "USER_1";

    @Autowired
    private UserGroupPreferenceRepository userGroupPreferenceRepository;

    /**
     * Tests that {@link UserGroupPreference}s can be retrieved by their {@code User} and {@code Group} IDs
     */
    @Test
    public void testFindByUserIdAndGroupId() {
        List<UserGroupPreference> userGroupPreferences =
                userGroupPreferenceRepository.findByUserIdAndGroupId(USER_1_ID, GROUP_1_ID);

        assertNotNull("User/Group Preferences should not be null", userGroupPreferences);
        assertSame("User/Group Preferences should contain 1 element", 1, userGroupPreferences.size());

        for (UserGroupPreference userGroupPreference : userGroupPreferences) {
            assertNotNull("User/Group Preference should not be null", userGroupPreference);
            assertEquals("User ID should be '" + USER_1_ID + "'", USER_1_ID, userGroupPreference.getUser().getId());
            assertEquals("Group ID should be '" + GROUP_1_ID + "'", GROUP_1_ID, userGroupPreference.getGroup().getId());

            Map<String, String> entries = userGroupPreference.getEntries();

            assertNotNull("User/Group Preference Entries should not be null", entries);
            assertSame("User/Group Preference Entries should contain 2 elements", 2, entries.size());
        }
    }

    /**
     * Tests that {@link UserGroupPreference}s can be retrieved by their {@code User} ID, {@code Group} ID and
     * {@link com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType}s
     */
    @Test
    public void testFindByUserIdAndGroupIdAndTypeIn() {
        List<UserGroupPreference> userGroupPreferences = userGroupPreferenceRepository
                .findByUserIdAndGroupIdAndTypeIn(USER_1_ID, GROUP_1_ID, Sets.newHashSet(UserGroupPreferenceType.FOO));

        assertNotNull("User/Group Preferences should not be null", userGroupPreferences);
        assertSame("User/Group Preferences should contain 1 element", 1, userGroupPreferences.size());

        for (UserGroupPreference userGroupPreference : userGroupPreferences) {
            assertNotNull("User/Group Preference should not be null", userGroupPreference);
            assertEquals("User ID should be '" + USER_1_ID + "'", USER_1_ID, userGroupPreference.getUser().getId());
            assertEquals("Group ID should be '" + GROUP_1_ID + "'", GROUP_1_ID, userGroupPreference.getGroup().getId());
            assertSame("User/Group Preference Type should be 'FOO'",
                    UserGroupPreferenceType.FOO, userGroupPreference.getType());

            Map<String, String> entries = userGroupPreference.getEntries();

            assertNotNull("User/Group Preference Entries should not be null", entries);
            assertSame("User/Group Preference Entries should contain 2 elements", 2, entries.size());
        }
    }
}
