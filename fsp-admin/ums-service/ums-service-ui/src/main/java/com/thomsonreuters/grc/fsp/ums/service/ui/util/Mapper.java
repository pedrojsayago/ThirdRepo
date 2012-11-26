package com.thomsonreuters.grc.fsp.ums.service.ui.util;

import com.thomsonreuters.grc.fsp.common.base.type.core.ClientFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.core.ClientSubscriptionFieldType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientRequest;
import com.thomsonreuters.grc.fsp.ums.client.core.dto.ClientResponse;
import com.thomsonreuters.grc.fsp.ums.client.ui.dto.GroupResponse;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.impl.ConfigurableMapper;
import ma.glasnost.orika.metadata.ClassMapBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class defines mapping between DTO and domain objects
 */
public class Mapper extends ConfigurableMapper {

    /**
     * {@inheritDoc}
     */
    @Override
    protected void configure(MapperFactory mapperFactory) {
        configureClientResponse(mapperFactory);
        configureClientGroup(mapperFactory);
        configureGroup(mapperFactory);
    }

    /**
     * Configure Group Mapping
     *
     * @param mapperFactory Mapper Factory
     */
    private void configureGroup(MapperFactory mapperFactory) {
        // Map Group and GroupDTO
        ClassMapBuilder<Group, GroupResponse> builder =
            mapperFactory.classMap(Group.class, GroupResponse.class);
        builder.customize(new CustomMapper<Group, GroupResponse>() {
            @Override
            public void mapAtoB(Group group, GroupResponse dto, MappingContext context) {
                if (!group.isLeaf()) {
                    dto.setHasChildren(Boolean.TRUE);
                }
            }
        });
        mapperFactory.registerClassMap(builder.byDefault().toClassMap());
    }

    /**
     * maps/copies fields from ClientRequest to Group domain
     *
     * @param mapperFactory factory instance holds all mapping information
     */
    private void configureClientGroup(MapperFactory mapperFactory) {

        ClassMapBuilder<ClientRequest, Group> builder =
            mapperFactory.classMap(ClientRequest.class, Group.class).field("clientName", "name");
        builder.customize(new CustomMapper<ClientRequest, Group>() {
            @Override
            public void mapAtoB(ClientRequest clientRequest, Group group, MappingContext context) {
                mapClientFields(clientRequest, group);
                mapScreeningPreferences(clientRequest, group);
            }
        });

        mapperFactory.registerClassMap(builder.byDefault().toClassMap());
    }

    /**
     * copies subscription and watchlist preference fields from clientRequest to
     * Group
     * domain entity
     *
     * @param clientRequest request clientRequest
     * @param group         group to be created
     */
    private void mapScreeningPreferences(ClientRequest clientRequest, Group group) {
        final Map<String, String> screeningPreferenceEntries = new HashMap<>();

        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.DATE_RANGE.toString(),
                         clientRequest.getDateRange());
        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.INHERIT_OGS.toString(),
                         clientRequest.getInheritOGS());
        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.MINIMUM_SCORE_THRESHOLD.toString(),
                         clientRequest.getMinScoreThreshold());
        nullValueSafePut(screeningPreferenceEntries, ClientSubscriptionFieldType.MODE.toString(),
                         clientRequest.getMode());
        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.OTHER_PAID_SUBSCRIPTIONS.toString(),
                         clientRequest.getPaidSubscriptions());
        nullValueSafePut(screeningPreferenceEntries, ClientSubscriptionFieldType.TYPE.toString(),
                         clientRequest.getSubscriptionType());
        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.WL_SCREENING_TYPE.toString(),
                         clientRequest.getWlScreeningTypes());
        nullValueSafePut(screeningPreferenceEntries,
                         ClientSubscriptionFieldType.PRODUCTS.toString(),
                         clientRequest.getProducts());

        //Add the preferences only if the list is not empty
        if (!screeningPreferenceEntries.isEmpty()) {
            GroupPreference screeningPreferences = new GroupPreference();
            // TODO Change this when we get a decision on preference types
            screeningPreferences.setType(GroupPreferenceType.CUSTOM_FIELD_TYPES);
            screeningPreferences.setGroup(group);
            screeningPreferences.setEntries(screeningPreferenceEntries);

            group.getPreferences().add(screeningPreferences);
        }
    }

    /**
     * copies client preference fields from clientRequest to Group
     * domain entity
     *
     * @param clientRequest request clientRequest
     * @param group         group to be created
     */
    private void mapClientFields(ClientRequest clientRequest, Group group) {
        final Map<String, String> clientFieldEntries = new HashMap<>();

        nullValueSafePut(clientFieldEntries, ClientFieldType.SALESFORCE_ID.toString(),
                         clientRequest.getSalesForceId());
        nullValueSafePut(clientFieldEntries, ClientFieldType.CASE_LIMIT.toString(),
                         clientRequest.getNumberOfScreenings());
        nullValueSafePut(clientFieldEntries, ClientFieldType.USER_LIMIT.toString(),
                         clientRequest.getNumberOfUser());
        nullValueSafePut(clientFieldEntries, ClientFieldType.OGS_LIMIT.toString(),
                         clientRequest.getNumberOfOGS());

        // add the preferences only if the list is not empty
        if (!clientFieldEntries.isEmpty()) {
            GroupPreference clientFields = new GroupPreference();
            clientFields.setType(GroupPreferenceType.CLIENT_FIELDS);
            clientFields.setGroup(group);
            clientFields.setEntries(clientFieldEntries);

            group.getPreferences().add(clientFields);
        }
    }


    /**
     * copies client preference fields from Group to clientResponse
     *
     * @param mapperFactory factory instance holds all mapping information
     */
    private void configureClientResponse(MapperFactory mapperFactory) {

        ClassMapBuilder<Group, ClientResponse> builder =
            mapperFactory.classMap(Group.class, ClientResponse.class).field("name", "client");

        builder.customize(new CustomMapper<Group, ClientResponse>() {
            @Override
            public void mapAtoB(Group group, ClientResponse clientResponse,
                                MappingContext context) {
                final List<GroupPreference> groupPreferences = group.getPreferences();
                for (GroupPreference groupPreference : groupPreferences) {
                    if (GroupPreferenceType.CLIENT_FIELDS == groupPreference.getType()) {
                        clientResponse.setSalesForceID(groupPreference.getEntries().get(
                            ClientFieldType.SALESFORCE_ID.toString()));
                        clientResponse.setNumberOfOGS(
                            groupPreference.getEntries().get(ClientFieldType.OGS_LIMIT.toString()));
                        clientResponse.setNumberOfScreenings(groupPreference.getEntries().get(
                            ClientFieldType.CASE_LIMIT.toString()));
                        clientResponse.setNumberOfUser(groupPreference.getEntries().get(
                            ClientFieldType.USER_LIMIT.toString()));
                    }
                }
            }
        });
        mapperFactory.registerClassMap(builder.byDefault().toClassMap());
    }

    /**
     * A null-safe version of put into map which does not do anything if the value is null and
     * acts as a normal put otherwise.
     *
     * @param map   Map into which the key-value pair has to be put
     * @param key   The Key to be put.
     * @param value The value to be checked for null before putting into map.
     * @param <K>   The type of the key.
     * @param <V>   The value of the key.
     *
     * @return Same as the return of normal map#put operation. Null if the value itself was null.
     */
    private <K, V> V nullValueSafePut(Map<K, V> map, K key, V value) {
        if (value != null) {
            return map.put(key, value);
        } else {
            return null;
        }
    }
}
