package com.thomsonreuters.grc.fsp.ums.service.ui.util;

import com.thomsonreuters.grc.fsp.common.base.type.core.ClientFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.core.ClientSubscriptionFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.service.core.DataUtil;
import ma.glasnost.orika.MapperFacade;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test class for client mapper.
 */
public class MapperTest {

    /**
     * Instance of mapper under test.
     */
    private MapperFacade mapperFacade = new Mapper();

    /**
     * Tests the mapping of Group (with preferences) to Client Response.
     */
    @Test
    public void testMapClientToClientResponse() {
        final Group client = DataUtil.createClientWithPreferences();

        ClientResponse clientResponse = mapperFacade.map(client, ClientResponse.class);

        assertNotNull("Client Response must not be null", clientResponse);
        assertEquals("Client name must match as expected ", DataUtil.CLIENT_NAME,
                     clientResponse.getClient());
        assertEquals("Client SALESFORCE_ID must match as expected ", DataUtil.SALESFORCE_ID,
                     clientResponse.getSalesForceID());
        assertEquals("Client CASE_LIMIT must match as expected", DataUtil.CASE_LIMIT,
                     clientResponse.getNumberOfScreenings());
        assertEquals("Client USER_LIMIT must match as expected", DataUtil.USER_LIMIT,
                     clientResponse.getNumberOfUser());
        assertEquals("Client OGS_LIMIT must match as expected", DataUtil.OGS_LIMIT,
                     clientResponse.getNumberOfOGS());
    }

    /**
     * Test the mapping of Group (without preferences) to Client Response.
     */
    @Test
    public void testMapClientWithoutPreferencesToClientResponse() {
        final Group client = DataUtil.createClient();

        ClientResponse clientResponse = mapperFacade.map(client, ClientResponse.class);

        assertNotNull("Client Response must not be null", clientResponse);
        assertEquals("Client name must match as expected ", DataUtil.CLIENT_NAME,
                     clientResponse.getClient());
        assertNull("Client SALESFORCE_ID must be null", clientResponse.getSalesForceID());
        assertNull("Client CASE_LIMIT must be null", clientResponse.getNumberOfScreenings());
        assertNull("Client USER_LIMIT must be null", clientResponse.getNumberOfUser());
        assertNull("Client OGS_LIMIT must be null", clientResponse.getNumberOfOGS());
    }


    /**
     * Test the mapping of Client Request (with preference attributes) to Group.
     */
    @Test
    public void testMapClientRequestToClient() {
        // create client request
        final ClientRequest clientRequest = DataUtil.createClientRequest(true);

        // map into group entity
        final Group client = mapperFacade.map(clientRequest, Group.class);

        assertNotNull("Client response must not be null", client);

        // check preferences
        final List<GroupPreference> clientPreferences = client.getPreferences();
        assertNotNull("Client preferences must not be null", clientPreferences);
        assertFalse("Client preferences must not be empty", clientPreferences.isEmpty());

        for (GroupPreference preference : clientPreferences) {
            switch (preference.getType()) {
                case CLIENT_FIELDS:
                    checkClientSettings(preference.getEntries());
                    break;
                // TODO Change this when we get a decision on preference types
                case CUSTOM_FIELD_TYPES:
                    checkScreeningSettings(preference.getEntries());
                    break;
                default:
                    fail("Unexpected preference type found");
            }
        }
    }

    /**
     * Test the mapping of Client Request (with partially filled preferences) to Group.
     */
    @Test
    public void testMapClientRequestWithFewSettingsToClient() {
        // create client request
        final ClientRequest clientRequest = DataUtil.createClientRequest(false);

        clientRequest.setSalesForceId(DataUtil.SALESFORCE_ID);

        // map into group entity
        final Group client = mapperFacade.map(clientRequest, Group.class);

        assertNotNull("Client response must not be null", client);

        // check preferences list. The list should not be empty
        final List<GroupPreference> clientPreferences = client.getPreferences();
        assertNotNull("Client-preferences must not be null", clientPreferences);
        assertFalse("Client-preferences must not be empty", clientPreferences.isEmpty());
        assertEquals("Unexpected number of Client-preferences", 1, clientPreferences.size());
        GroupPreference groupPreference = clientPreferences.get(0);
        assertEquals("Client-preference must be CLIENT_FIELDS type",
                     GroupPreferenceType.CLIENT_FIELDS, groupPreference.getType());

        assertEquals("Unexpected number of Client-Field preferences", 1,
                     groupPreference.getEntries().size());
        assertTrue("Client-fields preference entry must be SALESFORCE_ID",
                   groupPreference.getEntries().containsKey(ClientFieldType.SALESFORCE_ID.name()));
        assertEquals("Client-fields preference entry for SALESFORCE_ID must match",
                     DataUtil.SALESFORCE_ID,
                     groupPreference.getEntries().get(ClientFieldType.SALESFORCE_ID.name()));
    }

    /**
     * Test the mapping of Client Request (without preference attributes) to Group.
     */
    @Test
    public void testMapClientRequestWithoutSettingsToClient() {
        // create client request
        final ClientRequest clientRequest = DataUtil.createClientRequest(false);

        // map into group entity
        final Group client = mapperFacade.map(clientRequest, Group.class);

        assertNotNull("Client response must not be null", client);

        // check preferences list. The list should be empty
        final List<GroupPreference> clientPreferences = client.getPreferences();
        assertNotNull("Client preferences must not be null", clientPreferences);
        assertTrue("Client preferences must be Empty", clientPreferences.isEmpty());
    }

    /**
     * This method checks the group preferences for client-fields.
     *
     * @param clientFields map of client-fields key vs value
     */
    private void checkClientSettings(Map<String, String> clientFields) {
        assertNotNull("Client-fields must not be null ", clientFields);

        assertEquals("Client-field SALESFORCE_ID should match expected", DataUtil.SALESFORCE_ID,
                     clientFields.get(ClientFieldType.SALESFORCE_ID.name()));
        assertEquals("Client-field USER_LIMIT should match expected", DataUtil.USER_LIMIT,
                     clientFields.get(ClientFieldType.USER_LIMIT.name()));
        assertEquals("Client-field CASE_LIMIT should match expected", DataUtil.CASE_LIMIT,
                     clientFields.get(ClientFieldType.CASE_LIMIT.name()));
        assertEquals("Client-field OGS_LIMIT Searches should be match expected", DataUtil.OGS_LIMIT,
                     clientFields.get(ClientFieldType.OGS_LIMIT.name()));
    }

    /**
     * This method checks the group preferences for screening-settings.
     *
     * @param screeningSettings map of screening-settings key vs value
     */
    private void checkScreeningSettings(Map<String, String> screeningSettings) {
        assertNotNull("Screening settings must not be null", screeningSettings);

        assertEquals("Screening settings MODE should match expected", DataUtil.MODE,
                     screeningSettings.get(ClientSubscriptionFieldType.MODE.name()));
        assertEquals("Screening settings DATE_RANGE should match expected", DataUtil.DATE_RANGE,
                     screeningSettings.get(ClientSubscriptionFieldType.DATE_RANGE.name()));
        assertEquals("Screening settings PRODUCTS should match expected", DataUtil.PRODUCTS,
                     screeningSettings.get(ClientSubscriptionFieldType.PRODUCTS.name()));
        assertEquals("Screening settings TYPE should match expected", DataUtil.TYPE,
                     screeningSettings.get(ClientSubscriptionFieldType.TYPE.name()));
        assertEquals("screening settings WL_SCREENING_TYPE should match expected",
                     DataUtil.WL_SCREENING_TYPE,
                     screeningSettings.get(ClientSubscriptionFieldType.WL_SCREENING_TYPE.name()));
        assertEquals("Screening settings MINIMUM_SCORE_THRESHOLD should match expected",
                     DataUtil.MINIMUM_SCORE_THRESHOLD, screeningSettings
            .get(ClientSubscriptionFieldType.MINIMUM_SCORE_THRESHOLD.name()));
        assertEquals("Screening settings INHERIT_OGS should match expected", DataUtil.INHERIT_OGS,
                     screeningSettings.get(ClientSubscriptionFieldType.INHERIT_OGS.name()));
        assertEquals("Screening settings OTHER_PAID_SUBSCRIPTIONS should match expected",
                     DataUtil.OTHER_PAID_SUBSCRIPTIONS, screeningSettings
            .get(ClientSubscriptionFieldType.OTHER_PAID_SUBSCRIPTIONS.name()));
    }
}
