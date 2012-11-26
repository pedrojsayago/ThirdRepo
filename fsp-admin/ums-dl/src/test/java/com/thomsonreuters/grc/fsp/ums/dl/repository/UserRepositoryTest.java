/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;


import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.Role;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Tests repository for {@link User}
 *
 * @author Pedro Sayago &lt;pedro.sayago@thomsonreuters.com&gt;
 * @since 13 August 2012
 */
@Transactional
public class UserRepositoryTest extends BaseDataTest {

    /**
     * Case ID exists in db
     */
    private static final String CASE_ID_EXISTS = "62d21298-e07d-4a55-b7da-1176ee0f85ba";

    /**
     * Group ID exists in db
     */
    private static final String GROUP_ID = "132b05fb-ff42-4355-b8d9-5b53cffd1036";

    /**
     * User Repository to be tested
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Group Repository to Load Groups
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Different associations
     */
    private Group group;

    /**
     * Initialise Test Data
     */
    @Before
    public void init() {

        /**
         * Load Group and Group Assignable and Initialise Case
         */
        group = groupRepository.findOne(GROUP_ID);

        /**
         * Add roles to a User
         */
        Role role1 = new Role.Builder()
                .withName("roleName1")
                .withId("185d3d6d-ca86-47a4-b66f-6de705f276ff")
                .withGeneratedId()
                .build();

        Role role2 = new Role.Builder()
                .withName("roleName2")
                .withId("185d3d6d-ca86-47a4-b66f-7de705f276ff")
                .withGeneratedId()
                .build();
    }

    /**
     * Can Add User
     */
    @Test
    public void canAddUser() {
        /**
         * Add new User
         */
        User user1 = new User.Builder()
                .withUsername("username2@thomsonreuters.com")
                .withClient(group)
                .withGeneratedId()
                .build();

        User savedUser = userRepository.save(user1);
        assertNotNull("User must be saved", savedUser);
        assertNotNull("User must have uuid defined", savedUser.getId());
    }

    /**
     * Can Delete user
     */
    @Test
    public void canDeleteUser() {
        /**
         * Add new User
         */
        User user1 = new User.Builder()
                .withUsername("username3@thomsonreuters.com")
                .withClient(group)
                .withGeneratedId()
                .build();

        User savedUser = userRepository.save(user1);
        assertNotNull("User must be saved", savedUser);
        assertNotNull("User must have uuid defined", savedUser.getId());

        /**
         * Now Delete the User
         */
        String uuid = savedUser.getId();
        userRepository.delete(uuid);

        /**
         * Verify by loading the User
         */
        User deletedUser = userRepository.findOne(uuid);
        assertNull("User must have been deleted!", deletedUser);
    }

    /**
     * Can find User by the Username
     */
    @Test
    public void canFindUserByUserName() {
        User user = userRepository.findByUsername("username1@thomsonreuters.com");
        assertNotNull(user);
        assertNotNull(user.getUsername());
    }
}
