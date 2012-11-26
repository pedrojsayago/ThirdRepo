package com.thomsonreuters.grc.fsp.ums.service.ui;

import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.client.core.UserService;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.ClientCreationResponse;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.IdentifierRequest;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.fsp.ums.service.core.DataUtil;
import com.thomsonreuters.grc.fsp.ums.service.ui.util.Mapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * Test class for GroupUIService
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 30/08/12
 */
public class TestGroupUIService {

    /**
     * Class under test
     */
    @InjectMocks
    private GroupUIServiceImpl groupUIService = new GroupUIServiceImpl();

    /**
     * Constant for Group ID ZERO
     */
    private static final String GROUP_ZERO_ID = "0";

    /**
     * Create a mapper instance
     */
    @Mock
    @SuppressWarnings("unused")
    private Mapper mockMapper;

    @Mock
    @SuppressWarnings("unused")
    private UserService mockUserService;

    @Mock
    @SuppressWarnings("unused")
    private GroupService mockGroupService;

    @Before
    public void init() {
        initMocks(this);
    }

    @Test
    public void canCreateServiceInstance() {
        assertNotNull(groupUIService);
    }

    @Test
    public void canValidatePreconditions() {
        try {
            groupUIService.getScreeningGroup(null);
            fail("Expected IllegalArgumentException: IdentifierRequest is Null");
        } catch (IllegalArgumentException exp) {}

        IdentifierRequest identifierRequest = new IdentifierRequest();
        try {
            groupUIService.getScreeningGroup(identifierRequest);
            fail("Expected IllegalArgumentException: No identifiers set within IdentifierRequest");
        } catch (IllegalArgumentException exp) {}

        identifierRequest = buildIdentifierQuery(null);
        try {
            groupUIService.getScreeningGroup(identifierRequest);
            fail("Expected IllegalArgumentException: No identifiers set within IdentifierRequest");
        } catch (IllegalArgumentException exp) {}

    }

    @Test
    public void canThrowUserNotFoundForInvalidUser() {
        IdentifierRequest identifierRequest = buildIdentifierQuery(GROUP_ZERO_ID);

        // Set expectation
        when(mockUserService.getUserByName(anyString())).thenReturn(null);

        try {
            groupUIService.getScreeningGroup(identifierRequest);
            fail("Expected IllegalArgumentException: User not found");
        } catch (IllegalArgumentException exp) {}

    }

    @Test
    @Ignore // TODO This only works with a real mapper class...
    public void canGetScreeningGroupsForUser() {
        User user = new User();
        user.setId("user_name");
        String userName = "msrrmadmin";
        user.setUsername(userName);

        Set<Group> groups = new HashSet<>();
        Group group = new Group();
        group.setId("1");
        group.setName("groupName");
        group.setParent(null);
        groups.add(group);

        user.setGroups(groups);

        // Set expectation
        when(mockUserService.getUserByName(userName)).thenReturn(user);

        IdentifierRequest identifierRequest = buildIdentifierQuery(GROUP_ZERO_ID);
        GroupResponse groupDTO = groupUIService.getScreeningGroup(identifierRequest);

        assertNotNull(groupDTO);
        assertSame(GROUP_ZERO_ID, groupDTO.getId());
        assertTrue(groupDTO.getHasChildren());
        org.springframework.util.Assert.notEmpty(groupDTO.getChildren());
    }

    @Test
    @Ignore // TODO This only works with a real mapper class...
    public void canGetChildGroups() {

        String groupId = "GROUP_ID";
        IdentifierRequest identifierRequest = buildIdentifierQuery(groupId);

        // build dummy groups
        Group group = new Group();
        group.setName("parent group");
        group.setId(groupId);

        List<Group> groups = new ArrayList<>();
        Group childGroup = new Group();
        childGroup.setId("2");
        childGroup.setName("child group 1");
        childGroup.setParent(group);
        groups.add(childGroup);

        childGroup.setId("3");
        childGroup.setName("child group 2");
        childGroup.setParent(group);
        groups.add(childGroup);

        group.setChildren(groups);
        group.setLeaf(false);

        // Set expectation
        when(mockGroupService.getGroup(groupId)).thenReturn(group);

        GroupResponse groupDTO = groupUIService.getScreeningGroup(identifierRequest);

        assertNotNull(groupDTO);
        assertSame(groupId, groupDTO.getId());
        assertTrue(groupDTO.getHasChildren());
        org.springframework.util.Assert.notEmpty(groupDTO.getChildren());
        assertSame(2, groupDTO.getChildren().size());
    }

    /**
     * Test for successfully getting Client List
     */
    @Test
    public void testGetClientListSuccess() {
        List<Group> clientGroups = DataUtil.prepareClientGroups();
        // Set expectation
        when(mockGroupService.loadClients()).thenReturn(clientGroups);
        when(mockMapper.mapAsList(clientGroups, ClientResponse.class))
            .thenReturn(DataUtil.getClientResponses());

        List<ClientResponse> clientResponses = groupUIService.getClients();

        // verify mocks
        verify(mockMapper).mapAsList(clientGroups, ClientResponse.class);
        verify(mockGroupService).loadClients();

        Assert.assertNotNull("Admin client found", clientResponses);
        Assert.assertFalse("Admin client not found ", clientResponses.isEmpty());
        for (ClientResponse response : clientResponses) {
            Assert.assertEquals(DataUtil.CLIENT_NAME, response.getClient());
        }
    }

    /**
     * Test that an empty client List is returned if there are no clients
     */
    @Test
    public void testGetEmptyClientListIfThereAreNoClients() {
        // Set expectation
        List<Group> clientLists = new ArrayList<>();
        when(mockGroupService.loadClients()).thenReturn(clientLists);

        List<ClientResponse> clientResponses = groupUIService.getClients();

        verify(mockGroupService).loadClients();

        Assert.assertNotNull("Client response Should not be null", clientResponses);
        Assert.assertTrue("Client response Should be empty", clientResponses.isEmpty());
    }


    /**
     * Test for successfully creating a client
     */
    @Test
    public void testCreateClientSuccess() {

        //construct ClientRequest
        ClientRequest clientRequest = DataUtil.createClientRequest(true);

        //construct Group
        Group group = DataUtil.createClient();

        //construct User
        User user = new User.Builder().build();
        user.setUsername(DataUtil.CLIENT_ADMIN_EMAIL);
        User returnUser = DataUtil.createUser();

        //set the expectation
        when(mockMapper.map(clientRequest, Group.class)).thenReturn(group);
        when(mockGroupService.createClient(group)).thenReturn(group);
        when(mockUserService.createUser(user)).thenReturn(returnUser);

        ClientCreationResponse response = groupUIService.createClient(clientRequest);
        //verify the mocks
        verify(mockMapper).map(clientRequest, Group.class);
        verify(mockGroupService).createClient(group);
        verify(mockUserService).createUser(user);

        Assert.assertNotNull("Client creation response should not be null", response);
        Assert.assertNotNull("Client Id from create client response should not be null",
                             response.getClientId());
        Assert.assertNotNull("User Id from create Client response should not be null",
                             response.getUserId());
        Assert.assertEquals("Client id should match", DataUtil.CLIENT_ID, response.getClientId());
        Assert.assertEquals("User id should match", DataUtil.USER_ID, response.getUserId());
    }

    private IdentifierRequest buildIdentifierQuery(String groupId) {

        IdentifierRequest identifierRequest = new IdentifierRequest();
        List<String> identifiers = new ArrayList<>();
        identifiers.add(groupId);
        identifierRequest.setIdentifiers(identifiers);
        identifierRequest.setLazy(true);

        return identifierRequest;
    }
}
