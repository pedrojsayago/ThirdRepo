package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.common.base.type.core.ClientFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.core.ClientSubscriptionFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;

import java.util.*;

/**
 * Util class to create data for test cases
 */
public final class DataUtil {

    /**
     * Constant for Name of Client.
     */
    public static final String CLIENT_NAME = "CLIENT_NAME";

    /**
     * Constant for ID of Client.
     */
    public static final String CLIENT_ID = "CLIENT_ID";

    /**
     * Constant for email of Admin user.
     */
    public static final String CLIENT_ADMIN_EMAIL = "admin@somewhere.com";

    /**
     * Constant for SALESFORCE-ID of Client.
     */
    public static final String SALESFORCE_ID = "ABCD1234";

    /**
     * Constant for Limit of number of cases.
     */
    public static final String CASE_LIMIT = "1000";

    /**
     * Constant for Limit of number of users.
     */
    public static final String USER_LIMIT = "50";

    /**
     * Constant for Limit of number of ongoing screenings.
     */
    public static final String OGS_LIMIT = "80";


    /**
     * Constant for Subscription Type of Client.
     */
    public static final String TYPE = "PREMIUM";

    /**
     * Constant for Subscription Products.
     */
    public static final String PRODUCTS = "WATCHLIST,EIDV";

    /**
     * Constant for Date Range.
     */
    public static final String DATE_RANGE = "2012-03-05_2013-01-31";

    /**
     * Constant for Subscription Mode.
     */
    public static final String MODE = "ACTIVE";

    /**
     * Constant for WatchList Screening Type.
     */
    public static final String WL_SCREENING_TYPE = "WL_SCREENING_TYPE";

    /**
     * Constant for minimum score threshold.
     */
    public static final String MINIMUM_SCORE_THRESHOLD = "MINIMUM_SCORE_THRESHOLD";

    /**
     * Constant for whether ongoing-screenings are inherited from initial search.
     */
    public static final String INHERIT_OGS = "INHERIT_OGS";

    /**
     * Constant for other paid subscriptions.
     */
    public static final String OTHER_PAID_SUBSCRIPTIONS = "OTHER_PAID_SUBSCRIPTIONS";

    /**
     * Constant for Id for Admin user.
     */
    public static final String USER_ID = "USER_ID";

    /**
     * Restrict instantiation of Util class externally.
     */
    private DataUtil() {

    }

    /**
     * Create list of Client details to test.
     *
     * @return List of pre-created Client groups
     */
    public static List<Group> prepareClientGroups() {
        List<Group> clients = new ArrayList<>();
        List<GroupPreference> groupPrefs = new ArrayList<>();

        final Group group = createClient();

        // setting first value
        GroupPreference gpref = new GroupPreference.Builder().build();
        gpref.setType(GroupPreferenceType.CLIENT_FIELDS);
        gpref.setGroup(group);

        groupPrefs.add(gpref);

        // setting second value
        gpref = new GroupPreference.Builder().build();
        // TODO Change this when we get a decision on preference types
        gpref.setType(GroupPreferenceType.CUSTOM_FIELD_TYPES);
        gpref.setGroup(group);
        groupPrefs.add(gpref);

        group.setPreferences(groupPrefs);

        clients.add(group);

        return clients;
    }

    /**
     * Pre-populate Group to validate against.
     *
     * @return Pre-populated Client group.
     */
    public static Group createClient() {
        final Group group = new Group.Builder().build();
        group.setName(CLIENT_NAME);
        group.setId(CLIENT_ID);
        group.setType(GroupType.CLIENT);
        group.setDepth(0);
        group.setPath("/".concat(CLIENT_ID));

        return group;
    }

    /**
     * Create Client with Client Preferences for testing all fields.
     *
     * @return Created Client group with preferences set.
     */
    public static Group createClientWithPreferences() {
        final Group createdClient = createClient();

        // set preferences
        final GroupPreference clientGroupPreference = getClientGroupPreferences();
        clientGroupPreference.setGroup(createdClient);

        final GroupPreference screeningGroupPreference = getScreeningGroupPreferences();
        screeningGroupPreference.setGroup(createdClient);

        final List<GroupPreference> groupPreferences =
            Arrays.asList(clientGroupPreference, screeningGroupPreference);
        createdClient.setPreferences(groupPreferences);

        return createdClient;
    }

    /**
     * Populate Client-field preferences.
     *
     * @return Client-Field Group Preference.
     */
    private static GroupPreference getClientGroupPreferences() {
        final GroupPreference clientGroupPreferences = new GroupPreference.Builder().build();
        clientGroupPreferences.setType(GroupPreferenceType.CLIENT_FIELDS);

        final Map<String, String> preferenceEntries = new HashMap<>();
        preferenceEntries.put(ClientFieldType.SALESFORCE_ID.name(), SALESFORCE_ID);
        preferenceEntries.put(ClientFieldType.CASE_LIMIT.name(), CASE_LIMIT);
        preferenceEntries.put(ClientFieldType.USER_LIMIT.name(), USER_LIMIT);
        preferenceEntries.put(ClientFieldType.OGS_LIMIT.name(), OGS_LIMIT);

        clientGroupPreferences.setEntries(preferenceEntries);
        return clientGroupPreferences;
    }


    /**
     * Populate Screening-setting preferences.
     *
     * @return Screening-setting Group Preference.
     */
    private static GroupPreference getScreeningGroupPreferences() {
        final GroupPreference screeningGroupPreference = new GroupPreference.Builder().build();
        // TODO Change this when we get a decision on preference types
        screeningGroupPreference.setType(GroupPreferenceType.CUSTOM_FIELD_TYPES);

        final Map<String, String> preferenceEntries = new HashMap<>();
        preferenceEntries.put(ClientSubscriptionFieldType.TYPE.name(), TYPE);
        preferenceEntries.put(ClientSubscriptionFieldType.PRODUCTS.name(), PRODUCTS);
        preferenceEntries.put(ClientSubscriptionFieldType.DATE_RANGE.name(), DATE_RANGE);
        preferenceEntries.put(ClientSubscriptionFieldType.MODE.name(), MODE);
        preferenceEntries
            .put(ClientSubscriptionFieldType.WL_SCREENING_TYPE.name(), WL_SCREENING_TYPE);
        preferenceEntries.put(ClientSubscriptionFieldType.MINIMUM_SCORE_THRESHOLD.name(),
                              MINIMUM_SCORE_THRESHOLD);
        preferenceEntries.put(ClientSubscriptionFieldType.INHERIT_OGS.name(), INHERIT_OGS);
        preferenceEntries.put(ClientSubscriptionFieldType.OTHER_PAID_SUBSCRIPTIONS.name(),
                              OTHER_PAID_SUBSCRIPTIONS);

        screeningGroupPreference.setEntries(preferenceEntries);
        return screeningGroupPreference;
    }

    /**
     * Populate ClientRequest instance for testing.
     *
     * @param addPreferences Flag on whether to include group-preference related fields.
     *
     * @return Created Client-request.
     */
    public static ClientRequest createClientRequest(boolean addPreferences) {
        final ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientName(CLIENT_NAME);
        clientRequest.setClientAdminEmail(CLIENT_ADMIN_EMAIL);

        if (addPreferences) {
            // add client settings
            clientRequest.setSalesForceId(SALESFORCE_ID);
            clientRequest.setNumberOfUser(USER_LIMIT);
            clientRequest.setNumberOfScreenings(CASE_LIMIT);
            clientRequest.setNumberOfOGS(OGS_LIMIT);

            //add screening settings
            clientRequest.setMode(MODE);
            clientRequest.setDateRange(DATE_RANGE);
            clientRequest.setProducts(PRODUCTS);
            clientRequest.setSubscriptionType(TYPE);
            clientRequest.setWlScreeningTypes(WL_SCREENING_TYPE);
            clientRequest.setMinScoreThreshold(MINIMUM_SCORE_THRESHOLD);
            clientRequest.setInheritOGS(INHERIT_OGS);
            clientRequest.setPaidSubscriptions(OTHER_PAID_SUBSCRIPTIONS);
        }

        return clientRequest;
    }


    /**
     * Populate admin user
     *
     * @return user The populated admin user.
     */
    public static User createUser() {
        final User user =  new User.Builder().build();
        user.setUsername(CLIENT_ADMIN_EMAIL);
        user.setId(USER_ID);

        return user;
    }

    /**
     * Method to create a list of client response objects
     *
     * @return List of client response objects
     */
    public static List<ClientResponse> getClientResponses() {
        List<ClientResponse> responses = new ArrayList<>();

        ClientResponse response = new ClientResponse();
        response.setClient(CLIENT_NAME);
        response.setNumberOfOGS(OGS_LIMIT);
        response.setNumberOfScreenings(CASE_LIMIT);
        response.setNumberOfUser(USER_LIMIT);

        responses.add(response);

        return responses;
    }
}
