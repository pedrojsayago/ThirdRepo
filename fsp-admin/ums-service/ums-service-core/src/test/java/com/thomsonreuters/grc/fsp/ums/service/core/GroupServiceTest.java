package com.thomsonreuters.grc.fsp.ums.service.core;

import com.google.common.collect.Lists;
import com.mysema.query.types.Predicate;
import com.thomsonreuters.grc.fsp.common.base.exception.ObjectNotFoundException;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.dl.repository.GroupRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.QGroup;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test class for group service
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 24 August 2012
 */
public class GroupServiceTest extends BaseServiceTest {

    private Logger logger = LoggerFactory.getLogger(getClass());

    // Class to be tested
    @InjectMocks
    private GroupServiceImpl groupService = new GroupServiceImpl();

    @SuppressWarnings("unused")
    @Mock
    private GroupRepository mockGroupRepository;

    private static final String PATH_DELIMITER = "/";

    private Group group;
    private static final String GROUP_ID = "GROUP_ID";

    private Answer<Group> savedGroup = new Answer<Group>() {
        @Override
        public Group answer(InvocationOnMock invocationOnMock) throws Throwable {
            Object args[] = invocationOnMock.getArguments();
            Group group = (Group) args[0];

            if (null == group.getId()) {
                group.setId(UUID.randomUUID().toString());
            }
            return group;
        }
    };

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void canAddClientGroup() {
        // Return the group which was created within the method
        when(mockGroupRepository.save(any(Group.class))).thenAnswer(savedGroup);

        Group clientGroup = groupService.addClientGroup("test");

        verify(mockGroupRepository, times(2)).save(any(Group.class));
        assertGroup(clientGroup);
        assertEquals(GroupType.CLIENT, clientGroup.getType());
        assertEquals(getExpectedPath(clientGroup), clientGroup.getPath());
        assertEquals((Integer) 1, clientGroup.getDepth());
    }

    @Test
    public void canAddChildGroup() {
        // set up mock objects
        Group parent = createGroup();
        String parentPath = PATH_DELIMITER + GROUP_ID + PATH_DELIMITER;
        parent.setPath(parentPath);
        parent.setDepth(1);
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(parent);

        // Return the group which was created within the method
        when(mockGroupRepository.save(any(Group.class))).thenAnswer(savedGroup);

        String groupName = "GROUP_NAME";
        Group childGroup = groupService.addGroup(GROUP_ID, groupName);
        logger.debug("Path is {}", childGroup.getPath());

        verify(mockGroupRepository).findOne(GROUP_ID);
        verify(mockGroupRepository, times(3)).save(any(Group.class));
        assertGroup(childGroup);
        assertEquals("Incorrect group name", groupName, childGroup.getName());
        assertEquals("Incorrect Path set for child group", getExpectedPath(childGroup),
                childGroup.getPath());
        assertEquals((Integer) getExpectedDepth(childGroup), childGroup.getDepth());
        assertTrue(childGroup.isLeaf());
    }

    @Test
    public void testInvalidGroupName_addGroup() {
        String groupName = "Child groupName";
        Predicate groupPredicate = QGroup.group.name.eq(groupName).and(
            QGroup.group.parent.id.eq(GROUP_ID));

        Group parentGroup =  new Group.Builder().build();
        parentGroup.setId(GROUP_ID);

        Group group = createGroup();
        group.setPath("1");
        group.setDepth(1);
        group.setParent(parentGroup);
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(group);
        when(mockGroupRepository.findOne(groupPredicate)).thenReturn(group);

        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Group with the given name already exists");
        groupService.addGroup(GROUP_ID, groupName);
    }

    @Test
    public void testForInvalidParent_addGroup() {

        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(new Group.Builder().build());
        try {
            groupService.addGroup(GROUP_ID, "groupName");
            fail("Expected IllegalArgumentException for invalid parent path");
        } catch (IllegalArgumentException exp) {
        }

        Group parentGroup = new Group.Builder().withPath(GROUP_ID).withDepth(null).build();

        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(parentGroup);
        try {
            groupService.addGroup(GROUP_ID, "test");
            fail("Expected IllegalArgumentException for invalid parent depth");
        } catch (IllegalArgumentException exp) {
        }
    }

    @Test
    public void testPreConditions_addGroup() {
        try {
            groupService.addGroup(GROUP_ID, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exp) {
        }

        try {
            groupService.addGroup(null, null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exp) {
        }

        try {
            groupService.addGroup("", "");
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exp) {
        }
    }

    @Test
    public void testGroupNotFound() {
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(null);

        expectedException.expect(ObjectNotFoundException.class);
        groupService.addGroup(GROUP_ID, "test");
    }

    @Test
    public void canLoadGroupById() {
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(createGroup());

        group = groupService.getGroup(GROUP_ID);
        verify(mockGroupRepository).findOne(GROUP_ID);

        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(null);
        expectedException.expect(ObjectNotFoundException.class);
        groupService.getGroup(GROUP_ID);
    }

    @Test
    public void canLoadChildren() {
        List<Group> children = new ArrayList<>();
        Group group = new Group.Builder().build();
        children.add(group);

        when(mockGroupRepository.findByParentId(GROUP_ID)).thenReturn(children);
        Assert.notEmpty(groupService.getChildren(GROUP_ID));

        try {
            groupService.getChildren(null);
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException exp) {
        }
    }

    @Test
    public void canGetClientHierarchy() {
        Group group = (Group) new Group.Builder().withId(GROUP_ID).build();

        StringBuilder path = new StringBuilder(PATH_DELIMITER);
        path.append(GROUP_ID);
        path.append(PATH_DELIMITER);
        path.append("2");
        group.setPath(path.toString());

        // Set expectation
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(group);
        when(mockGroupRepository.findByPathStartingWith(PATH_DELIMITER + GROUP_ID))
                .thenReturn(Lists.newArrayList("2, 3"));

        List<String> groupIds = groupService.getClientHierarchy(GROUP_ID);
        verify(mockGroupRepository).findByPathStartingWith(PATH_DELIMITER + GROUP_ID);
        Assert.notEmpty(groupIds);
    }

    @Test
    public void canGetHierarchy_validations() {
        Group group = (Group) new Group.Builder().withId(GROUP_ID).build();

        // Set expectation
        when(mockGroupRepository.findOne(GROUP_ID)).thenReturn(group);

        try {
            groupService.getClientHierarchy(GROUP_ID);
            fail("Expected IllegalArgumentException due to invalid path");
        } catch (IllegalArgumentException exp) {
        }

        group.setPath("//");
        try {
            groupService.getClientHierarchy(GROUP_ID);
            fail("Expected IllegalArgumentException due to invalid path");
        } catch (IllegalArgumentException exp) {
        }

    }

    /**
     * Test normal load of clients.
     */
    @Test
    public void testLoadClientsSuccess() {
        List<Group> fixtureGroups = DataUtil.prepareClientGroups();
        when(mockGroupRepository.findByType(GroupType.CLIENT)).thenReturn(fixtureGroups);

        List<Group> groupDetails = groupService.loadClients();

        verify(mockGroupRepository).findByType(GroupType.CLIENT);

        assertNotNull("Clients list is null.", groupDetails);
        assertFalse("Clients list is unexpectedly empty.", groupDetails.isEmpty());
        assertArrayEquals("Returned clients list is incorrect.", fixtureGroups.toArray(),
                          groupDetails.toArray());
    }

    /**
     * Test loading of clients when there are none.
     */
    @Test
    public void testLoadClientsReturnsEmptyListIfThereAreNoClients() {
        when(mockGroupRepository.findByType(GroupType.CLIENT)).
            thenReturn(Collections.<Group>emptyList());

        List<Group> groupDetails = groupService.loadClients();

        verify(mockGroupRepository).findByType(GroupType.CLIENT);

        assertNotNull("Clients list is null.", groupDetails);
        assertTrue("Unexpected non-empty clients-list.", groupDetails.isEmpty());
    }

    /**
     * Test normal client creation.
     */
    @Test
    public void testCreateClientSuccess() {
        final Group client = new Group.Builder().build();
        client.setName(DataUtil.CLIENT_NAME);
        client.setId(DataUtil.CLIENT_ID); //Pre-set the id for verification after test.

        when(mockGroupRepository.findByNameAndType(client.getName(), client.getType()))
            .thenReturn(null);
        when(mockGroupRepository.save(client)).thenReturn(client);

        Group createdClient = groupService.createClient(client);

        verify(mockGroupRepository, times(2)).save(client);

        //Assert the fields explicitly set by the service
        assertNotNull(createdClient);
        assertEquals("Unexpected GroupType in Client.", GroupType.CLIENT, createdClient.getType());
        assertEquals("Unexpected Depth in Client.", getExpectedDepth(createdClient),
                     createdClient.getDepth().intValue());
        assertEquals("Unexpected Path set in Client.", getExpectedPath(createdClient),
                     createdClient.getPath());
    }

    /**
     * Test Client Creation validation for duplicate-name.
     *
     * TODO Remove the expected exception and assert against the response object when the UI
     * error handling has been resolved
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateClientFailsForDuplicateClientName() {
        final Group client = DataUtil.createClient();

        Predicate groupNamePredicate = QGroup.group.name.eq(client.getName()).and(
            QGroup.group.parent.id.isNull());
        when(mockGroupRepository.findOne(groupNamePredicate)).thenReturn(client);

        groupService.createClient(client);
    }

    private void assertGroup(Group savedGroup) {
        assertNotNull(savedGroup);
        assertNotNull("Path not set for group", savedGroup.getPath());
        assertNotNull(savedGroup.getName());
    }

    private String getExpectedPath(Group group) {
        StringBuilder expectedPath = new StringBuilder();

        if (group.getParent() == null) {
            expectedPath.append(PATH_DELIMITER);
            expectedPath.append(group.getId());
            expectedPath.append(PATH_DELIMITER);
        } else {
            expectedPath.append(group.getParent().getPath());
            expectedPath.append(group.getId());
            expectedPath.append(PATH_DELIMITER);
        }
        return expectedPath.toString();
    }

    private int getExpectedDepth(Group group) {
        if (group.getParent() == null) {
            return 1;
        } else {
            return group.getParent().getDepth() + 1;
        }
    }

    private Group createGroup() {
        Group group = (Group) new Group.Builder().withName("group name").withId(GROUP_ID).build();
        return group;
    }

}
