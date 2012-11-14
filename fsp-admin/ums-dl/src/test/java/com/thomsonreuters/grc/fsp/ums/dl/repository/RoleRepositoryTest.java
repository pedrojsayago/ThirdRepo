/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;

import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.Role;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Tests repository for {@link Role}
 *
 * @author Pedro Sayago &lt;pedro.sayago@thomsonreuters.com&gt;
 * @since 15 August 2012
 */
@Transactional
public class RoleRepositoryTest extends BaseDataTest {

    /**
     * Group ID exists in db
     */
    private static final String GROUP_ID = "0b36c44f-ff2a-47c0-b407-a52ae4ea02b2";

    /**
     * User Repository to be tested
     */
    @Autowired
    UserRepository userRepository;

    /**
     * Role Repository to be tested
     */
    @Autowired
    RoleRepository roleRepository;

    /**
     * Group loaded from DB
     */
    private Group group;

    /**
     * Group Repository to Load Groups
     */
    @Autowired
    private GroupRepository groupRepository;

    /**
     * Initialise Test Data
     */
    @Before
    public void init() {
        /**
         * Load Group
         */
        group = groupRepository.findOne(GROUP_ID);
    }

    /**
     * Can Add Role
     */
    @Test
    public void canAddRole() {
        /**
         * Add Role if not already exists in DB
         */
        Role role1 = new Role.Builder()
                .withClient(group)
                .withName("roleName1")
                .withGeneratedId()
                .build();

        Role savedRole = roleRepository.save(role1);
        assertNotNull("Role must be saved", savedRole);
        assertNotNull("Role must have uuid defined", savedRole.getId());
    }

    /**
     * Can Delete Role
     */
    @Test
    public void canDeleteRole() {
        /**
         * Add new Role
         */
        Role role1 = new Role.Builder()
                .withClient(group)
                .withName("roleName2")
                .withGeneratedId()
                .build();

        Role savedRole = roleRepository.save(role1);
        assertNotNull("Role must be saved", savedRole);
        assertNotNull("Role must have uuid defined", savedRole.getId());

        /**
         * Now Delete the Role
         */
        String uuid = savedRole.getId();
        roleRepository.delete(uuid);

        /**
         * Verify by loading the case
         */
        Role deletedRole = roleRepository.findOne(uuid);
        Assert.assertNull("Role must have been deleted", deletedRole);
    }

    /**
     * Can find Role by the Role Name
     */
    @Test
    public void canFindRoleByName() {
        List<Role> roles = roleRepository.findByName("roleName");
        assertNotNull(roles);
    }
}
