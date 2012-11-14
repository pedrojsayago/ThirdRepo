package com.thomsonreuters.grc.fsp.ums.service.ui.it;

import com.thomsonreuters.grc.fsp.common.base.type.core.ClientFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.core.ClientSubscriptionFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.ClientCreationResponse;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.GroupUIService;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.IdentifierRequest;
import com.thomsonreuters.grc.fsp.ums.dl.repository.GroupRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.UserRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.service.core.DataUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Integration test case for GroupService
 *
 * Note: Tests rely on data from init-data.sql
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 03/09/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:ums-service-ui-context-test.xml"})
@TransactionConfiguration(transactionManager = "data.transactionManager")
@Transactional
public class GroupServiceTest {

    /**
     * Constant for Group ID ZERO
     */
    private static final String GROUP_ZERO_ID = "0";

    /**
     * Default expected Depth value of created Client
     */
    private static final Integer NEW_CLIENT_EXPECTED_DEPTH = 1;

    /**
     * Expected number of preferences on created client
     */
    private static final int NEW_CLIENT_EXPECTED_PREFERENCE_COUNT = 2;

    /**
     * Expected clients to be loaded.
     */
    private static final String[] EXPECTED_CLIENTS = {"TR Parent", "TR Parent 1"};

    /**
     * Separator used to build the Group path string.
     */
    public static final String GROUP_PATH_SEPARATOR = "/";

    /**
     * The entry point under test.
     */
    @Autowired
    @SuppressWarnings("unused")
    private GroupUIService groupUIService;

    /**
     * Group repository to perform CRUD operations
     */
    @Autowired
    @SuppressWarnings("unused")
    private GroupRepository groupRepository;

    /**
     * User repository to perform CRUD operations
     */
    @Autowired
    @SuppressWarnings("unused")
    private UserRepository userRepository;

    @Test
    public void canLoadRootScreeningGroups() {
        IdentifierRequest identifierRequest = new IdentifierRequest();
        List<String> identifiers = new ArrayList<>();
        identifiers.add(GROUP_ZERO_ID);
        identifierRequest.setIdentifiers(identifiers);

        identifierRequest.setLazy(true);

        GroupResponse groupDTO = groupUIService.getScreeningGroup(identifierRequest);
        assertGroup(groupDTO);
    }

    @Test
    public void canLoadChildGroups() {
        IdentifierRequest identifierRequest = new IdentifierRequest();
        List<String> identifiers = new ArrayList<>();
        identifiers.add("GROUP_1");
        identifierRequest.setIdentifiers(identifiers);

        identifierRequest.setLazy(true);

        GroupResponse groupDTO = groupUIService.getScreeningGroup(identifierRequest);
        assertGroup(groupDTO);
    }

    /**
     * Test normal fetching of Client List.
     */
    @Test
    public void testLoadClientsSuccess() {
        List<ClientResponse> clientResponses = groupUIService.getClients();

        assertNotNull("ClientResponse must not be null.", clientResponses);
        assertFalse("ClientResponse must not be empty.", clientResponses.isEmpty());

        ArrayList<String> returnedClientIds = new ArrayList<>(clientResponses.size());
        for (ClientResponse response : clientResponses) {
            assertNotNull("Client Name must not be null", response.getClient());
            assertNotNull("Client Status must not be null", response.getStatus());
            returnedClientIds.add(response.getClient());
        }

        assertArrayEquals("Client ids returned not as expected", EXPECTED_CLIENTS,
                          returnedClientIds.toArray());
    }

    /**
     * Test normal Client creation.
     */
    @Test
    public void testCreateClientSuccess() {
        ClientRequest clientRequest = DataUtil.createClientRequest(true);

        ClientCreationResponse response = groupUIService.createClient(clientRequest);

        assertNotNull("Response must not be null", response);
        assertNotNull("Client id should not be null", response.getClientId());
        assertNotNull("User id should not be null", response.getUserId());

        //Check the DB for details of created objects
        String clientId = response.getClientId();
        String defaultUserId = response.getUserId();
        Group group = groupRepository.findOne(clientId);
        User user = userRepository.findOne(defaultUserId);

        assertNotNull("Failed to create Client", group);
        assertNotNull("Failed to create User", user);
        assertEquals("Client Name incorrect", clientRequest.getClientName(), group.getName());
        assertEquals("User Name not as expected", clientRequest.getClientAdminEmail(),
                     user.getUsername());

        //Default settings in AdminService
        assertEquals("Client Group Type incorrect", GroupType.CLIENT, group.getType());
        assertEquals("Client Depth incorrect", NEW_CLIENT_EXPECTED_DEPTH, group.getDepth());
        String expectedPath = GROUP_PATH_SEPARATOR.concat(clientId).concat(GROUP_PATH_SEPARATOR);
        assertEquals("Client Path incorrect", expectedPath, group.getPath());

        //Fields mapped by AdminUIService
        assertNotNull("Client Preferences must not be null", group.getPreferences());

        assertEquals("Found incorrect preferences count on newly created group",
                     NEW_CLIENT_EXPECTED_PREFERENCE_COUNT, group.getPreferences().size());
        for (GroupPreference gPref : group.getPreferences()) {
            switch (gPref.getType()) {
                case CLIENT_FIELDS:
                    validateGroupClientFields(gPref);
                    break;
                // TODO Change this when we get a decision on preference types
                case CUSTOM_FIELD_TYPES:
                    validateGroupScreeningFields(gPref);
                    break;
                default:
                    fail("Unexpected group preference");
                    break;
            }
        }
    }

    /**
     * Method to validate the Client-fields.
     *
     * @param clientFieldPreferences CLIENT_FIELD preferences for the Client.
     */
    private void validateGroupClientFields(GroupPreference clientFieldPreferences) {
        Map<String, String> clientFieldEntries = clientFieldPreferences.getEntries();
        assertNotNull("ClientFieldEntries must not be null", clientFieldEntries);

        assertEquals("Salesforce Id should be as expected", DataUtil.SALESFORCE_ID,
                     clientFieldEntries.get(ClientFieldType.SALESFORCE_ID.name()));
        assertEquals("Case Limit should be as expected", DataUtil.CASE_LIMIT,
                     clientFieldEntries.get(ClientFieldType.CASE_LIMIT.name()));
        assertEquals("User Limit should be as expected", DataUtil.USER_LIMIT,
                     clientFieldEntries.get(ClientFieldType.USER_LIMIT.name()));
        assertEquals("OGS Limit should be as expected", DataUtil.OGS_LIMIT,
                     clientFieldEntries.get(ClientFieldType.OGS_LIMIT.name()));
    }

    /**
     * Method to validate the Screening-settings.
     *
     * @param screeningFieldPrefs SCREENING_SETTINGS preferences for the Client.
     */
    private void validateGroupScreeningFields(GroupPreference screeningFieldPrefs) {
        Map<String, String> screeningFieldEntries = screeningFieldPrefs.getEntries();
        assertNotNull("ScreeningFieldEntries must not be null", screeningFieldEntries);
        assertEquals("Mode should be as expected", DataUtil.MODE,
                     screeningFieldEntries.get(ClientSubscriptionFieldType.MODE.name()));
        assertEquals("DATE_RANGE should be as expected", DataUtil.DATE_RANGE,
                     screeningFieldEntries.get(ClientSubscriptionFieldType.DATE_RANGE.name()));
        assertEquals("PRODUCTS should be as expected", DataUtil.PRODUCTS,
                     screeningFieldEntries.get(ClientSubscriptionFieldType.PRODUCTS.name()));
        assertEquals("TYPE should be as expected", DataUtil.TYPE,
                     screeningFieldEntries.get(ClientSubscriptionFieldType.TYPE.name()));
        assertEquals("WL_SCREENING_TYPE should be as expected", DataUtil.WL_SCREENING_TYPE,
                     screeningFieldEntries
                         .get(ClientSubscriptionFieldType.WL_SCREENING_TYPE.name()));
        assertEquals("MINIMUM_SCORE_THRESHOLD should be as expected",
                     DataUtil.MINIMUM_SCORE_THRESHOLD, screeningFieldEntries
            .get(ClientSubscriptionFieldType.MINIMUM_SCORE_THRESHOLD.name()));
        assertEquals("INHERIT_OGS should be as expected", DataUtil.INHERIT_OGS,
                     screeningFieldEntries.get(ClientSubscriptionFieldType.INHERIT_OGS.name()));
        assertEquals("OTHER_PAID_SUBSCRIPTIONS should be as expected",
                     DataUtil.OTHER_PAID_SUBSCRIPTIONS, screeningFieldEntries
            .get(ClientSubscriptionFieldType.OTHER_PAID_SUBSCRIPTIONS.name()));
    }

    /**
     * Test Client creation with duplicate name.
     *
     * TODO Change the expected exception to some asserts on the response object when UI error
     * handling has been finalised
     */
    @Test(expected = IllegalArgumentException.class)
    public void testCreateClientWithDuplicateName() {
        ClientRequest request = DataUtil.createClientRequest(false);
        request.setClientName(EXPECTED_CLIENTS[0]); //Use one of the names known to be in test-data

        ClientCreationResponse response = groupUIService.createClient(request);
    }

    private void assertGroup(GroupResponse groupDTO) {
        assertNotNull(groupDTO);
        assertNotNull(groupDTO.getId());
        assertNotNull(groupDTO.getName());
        assertNotNull(groupDTO.getHasChildren());
        org.springframework.util.Assert.notEmpty(groupDTO.getChildren());
    }

}
