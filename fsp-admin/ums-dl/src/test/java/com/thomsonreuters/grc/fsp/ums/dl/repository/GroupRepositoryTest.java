/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;

import com.thomsonreuters.grc.fsp.common.base.type.core.ClientFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.springframework.util.Assert.hasText;
import static org.springframework.util.Assert.notEmpty;

/**
 * Tests repository for {@link Group}
 * <p/>
 * Note: Tests rely on data from fsp-db.xml
 *
 * @author Pedro Sayago &lt;pedro.sayago@thomsonreuters.com&gt;
 * @since 13 August 2012
 */
@Transactional
public class GroupRepositoryTest extends BaseDataTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupRepositoryTest.class);

    /**
     * constant for client name
     */
    private static final String CLIENT_NAME = "Client 1";

    /**
     * constant for sales force id for client
     */
    private static final String SALESFORCE_ID = "123456789";

    /**
     * constant for case limit for client
     */
    private static final String CASE_LIMIT = "10000";

    /**
     * constant for user limit for client
     */
    private static final String USER_LIMIT = "100";

    /**
     * constant for ogs limit for client
     */
    private static final String OGS_LIMIT = "100";


    /**
     * Group Repository to be tested
     */
    @Autowired
    GroupRepository groupRepository;

    private static final String GROUP_ID = "GROUP_1";

    /**
     * Can Add Group
     */
    @Test
    public void canAddGroup() {
        Group group = createGroup();
        LOGGER.debug("this is my groupId {}", group.getId());

        group = groupRepository.save(group);
        assertNotNull("Group must be saved", group);
        assertNotNull("Group must have uuid defined", group.getId());
    }

    @Test
    public void canFetchGroup() {
        Group group = groupRepository.findOne(GROUP_ID);
        assertNotNull(group);
        assertNotNull(group.getId());
        assertNotNull(group.getName());
        assertNotNull(group.getPath());
    }

    @Test
    public void canLoadGroupsByPathPrefix() {
        String pathPrefix = "/GROUP_1";
        List<String> groupIds = groupRepository.findByPathStartingWith(pathPrefix);
        validatePath(groupIds, pathPrefix);

        pathPrefix = "/";
        groupIds = groupRepository.findByPathStartingWith(pathPrefix);
        validatePath(groupIds, pathPrefix);

        groupIds = groupRepository.findByPathStartingWith("?");
        assertTrue(groupIds.isEmpty());
    }

    @Test
    public void canDeleteGroup() {
        Group group = createGroup();
        group = groupRepository.save(group);

        groupRepository.delete(group.getId());

        group = groupRepository.findOne(group.getId());
        assertNull(group);
    }

    @Test
    public void canFetchChildren() {
        List<Group> children = groupRepository.findByParentId(GROUP_ID);
        notEmpty(children);

        for (Group child : children) {
            assertNotNull(child.getParent());
            assertEquals(GROUP_ID, child.getParent().getId());
        }

        children = groupRepository.findByParentId("abc");
        assertTrue(children.isEmpty());
    }

    @Test
    public void canAddClient() {
        Group client = new Group.Builder()
                .withType(GroupType.CLIENT)
                .withName(CLIENT_NAME)
                .withPath("/1/2/")
                .withDepth(1)
                .withGeneratedId()
                .build();

        GroupPreference pref = new GroupPreference.Builder()
                .withGroup(client)
                .withType(GroupPreferenceType.CLIENT_FIELDS)
                .withGeneratedId()
                .build();

        Map<String, String> entries = new HashMap<>();
        entries.put(ClientFieldType.SALESFORCE_ID.name(), SALESFORCE_ID);
        entries.put(ClientFieldType.CASE_LIMIT.name(), CASE_LIMIT);
        entries.put(ClientFieldType.USER_LIMIT.name(), USER_LIMIT);
        entries.put(ClientFieldType.OGS_LIMIT.name(), OGS_LIMIT);
        pref.setEntries(entries);

        client.getPreferences().add(pref);
        client = groupRepository.save(client);

        Group savedClient = groupRepository.findOne(client.getId());
        assertNotNull(savedClient);
    }

    /**
     * test case to find the group by its name
     */
    @Test
    public void canFindGroupByName() {
        Group group = groupRepository.findByName("TR Parent");
        assertNotNull(group);
    }

    /**
     * test case to find the group by its type {@link com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType}
     */
    @Test
    public void canFindGroupByType() {
        List<Group> groups = groupRepository.findByType(GroupType.CLIENT);
        assertNotNull(groups);
        assertFalse(groups.isEmpty());
        for (Group group : groups) {
            assertNotNull(group.getName());
            assertEquals("found Group with type other than CLIENT ", GroupType.CLIENT.toString(),
                    group.getType().toString());
        }
    }

    private void validatePath(List<String> groupIds, String pathPrefix) {
        notEmpty(groupIds);

        LOGGER.debug("{}", groupIds);

        Group group;
        for (String groupId : groupIds) {
            group = groupRepository.findOne(groupId);
            hasText(group.getPath());
            assertTrue(group.getPath().startsWith(pathPrefix));
        }
    }

    private Group createGroup() {
        Group group = new Group.Builder()
                .withName("Client")
                .withPath("/1/2/")
                .withDepth(1)
                .withGeneratedId()
                .build();

        return group;
    }
}
