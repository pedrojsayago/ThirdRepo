package com.thomsonreuters.grc.fsp.ums.service.core;

import com.google.common.collect.Sets;
import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.PreferenceType;
import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.client.core.PreferenceService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.CustomFieldTypeRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.DefaultPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.GroupPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.ResolutionLinkRepository;
import com.thomsonreuters.grc.fsp.ums.domain.preference.CustomFieldType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.Preference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.defaults.DefaultPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.ResolutionLink;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.StatusLink;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static org.springframework.util.Assert.*;

/**
 * Preference Service Implementation
 */
@Transactional
public class PreferenceServiceImpl implements PreferenceService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceServiceImpl.class);

    private final Map<PreferenceType, Map<String, String>> defaultPreferenceEntries = new HashMap<>();

    private CustomFieldTypeRepository customFieldTypeRepository;

    private DefaultPreferenceRepository defaultPreferenceRepository;

    private GroupPreferenceRepository groupPreferenceRepository;

    private GroupService groupService;

    private ResolutionLinkRepository resolutionLinkRepository;

    /**
     * Setter used to inject the {@link CustomFieldTypeRepository} for the service.
     *
     * @param customFieldTypeRepository the customFieldTypeRepository.
     */
    @Required
    public void setCustomFieldTypeRepository(CustomFieldTypeRepository customFieldTypeRepository) {
        this.customFieldTypeRepository = customFieldTypeRepository;
    }

    /**
     * Setter used to inject the {@link DefaultPreferenceRepository} for the service.
     *
     * @param defaultPreferenceRepository the defaultPreferenceRepository.
     */
    @Required
    public void setDefaultPreferenceRepository(DefaultPreferenceRepository defaultPreferenceRepository) {
        this.defaultPreferenceRepository = defaultPreferenceRepository;
    }

    /**
     * Setter used to inject the {@link GroupPreferenceRepository} for the service.
     *
     * @param groupPreferenceRepository the groupPreferenceRepository.
     */
    @Required
    public void setGroupPreferenceRepository(GroupPreferenceRepository groupPreferenceRepository) {
        this.groupPreferenceRepository = groupPreferenceRepository;
    }

    /**
     * Setter used to inject the {@link GroupService} for the service.
     *
     * @param groupService the groupRepository.
     */
    @Required
    public void setGroupService(GroupService groupService) {
        this.groupService = groupService;
    }

    /**
     * Setter used to inject the {@link ResolutionLinkRepository} for the service.
     *
     * @param resolutionLinkRepository the resolutionLinkRepository.
     */
    @Required
    public void setResolutionLinkRepository(ResolutionLinkRepository resolutionLinkRepository) {
        this.resolutionLinkRepository = resolutionLinkRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void afterPropertiesSet() {
        Iterable<DefaultPreference> defaultPreferences = defaultPreferenceRepository.findAll();
        for (DefaultPreference<?> defaultPreference : defaultPreferences) {
            defaultPreferenceEntries.put(defaultPreference.getType(),
                    defaultPreference.getEntries());
        }
        for (GroupPreferenceType groupPreferenceType : GroupPreferenceType.values()) {
            checkDefaultPreference(groupPreferenceType);
        }
//        TODO Uncomment when User/Group and User Preference Types are defined with default entries
//        for (UserGroupPreferenceType userGroupPreferenceType : UserGroupPreferenceType.values()) {
//            checkDefaultPreference(userGroupPreferenceType);
//        }
//        for (UserPreferenceType userPreferenceType : UserPreferenceType.values()) {
//            checkDefaultPreference(userPreferenceType);
//        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public CustomFieldType getCustomFieldType(String customFieldTypeId) {
        hasText(customFieldTypeId, "Invalid Custom Field Type ID");

        LOGGER.debug("Retrieving Custom Field Type with ID {}", customFieldTypeId);

        CustomFieldType customFieldType = customFieldTypeRepository.findOne(customFieldTypeId);

        if (customFieldType == null) {
            throw new IllegalStateException(String.format("Custom Field Type %s does not exist", customFieldTypeId));
        }
        LOGGER.debug("Retrieved Custom Field Type with ID {}:\n{}", customFieldType);

        return customFieldType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomFieldType> getCustomFieldTypes(List<String> customFieldTypeIds) {
        notEmpty(customFieldTypeIds, "Invalid Custom Field Type IDs");

        List<CustomFieldType> customFieldTypes = new ArrayList<>();

        for (CustomFieldType customFieldType : customFieldTypes) {
            customFieldTypes.add(customFieldType);
        }
        return customFieldTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<CustomFieldType> getCustomFieldTypesByGroup(String groupId) {
        GroupPreference customFieldTypePreference = getGroupPreference(groupId, GroupPreferenceType.CUSTOM_FIELD_TYPES);

        List<CustomFieldType> customFieldTypes = new ArrayList<>();

        Map<String, String> customFieldTypePreferenceEntries = new TreeMap<>(customFieldTypePreference.getEntries());

        for (String customFieldTypeId : customFieldTypePreferenceEntries.values()) {
            CustomFieldType customFieldType = getCustomFieldType(customFieldTypeId);

            if (customFieldType == null) {
                throw new IllegalStateException(
                        String.format("Custom Field Type %s does not exist", customFieldTypeId));
            }
            customFieldTypes.add(customFieldType);
        }
        return customFieldTypes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<StatusLink> getResolutionLinksByGroup(String groupId) {
        // Load preferences for type RESOLUTION_LINKS
        GroupPreference resolutionLinkPreference = getGroupPreference(groupId, GroupPreferenceType.RESOLUTION_LINKS);

        // Each value in the entry is a UUID for {@link ResolutionLink}
        List<StatusLink> statusLinks = new LinkedList<>();
        // Order the map based on the Indexes
        TreeMap<String, String> sortedMap = new TreeMap<>(resolutionLinkPreference.getEntries());
        for (String resolutionLinkId : sortedMap.values()) {
            ResolutionLink resolutionLink = resolutionLinkRepository.findOne(resolutionLinkId);

            if ((resolutionLink == null)) {
                throw new IllegalStateException(
                        String.format("Resolution link %s does not exist", resolutionLinkId));
            } else if (!(resolutionLink instanceof StatusLink)) {
                throw new IllegalStateException(
                        String.format("Invalid Resolution link type %s", resolutionLink.getClass()));
            }
            statusLinks.add((StatusLink) resolutionLink);
        }

        return statusLinks;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public GroupPreference getGroupPreference(String groupId, GroupPreferenceType groupPreferenceType) {
        return getGroupPreferences(groupId, Sets.newHashSet(groupPreferenceType)).get(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroupPreference> getGroupPreferences(String groupId) {
        groupService.getGroup(groupId);

        LOGGER.debug("Retrieving all Group Preferences for Group {}", groupId);

        // Retrieve from the Repository
        List<GroupPreference> groupPreferences = groupPreferenceRepository.findByGroupId(groupId);

        // If any are missing, use the default values
        if (groupPreferences.size() < GroupPreferenceType.values().length) {

            groupPreferences.addAll(
                    getDefaultPreferences(
                            EnumSet.allOf(GroupPreferenceType.class), groupPreferences, GroupPreference.class));
        }

        LOGGER.debug("Retrieved all Group Preferences of for Group {}:\n{}", groupId, groupPreferences);

        return groupPreferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<GroupPreference> getGroupPreferences(String groupId, Set<GroupPreferenceType> groupPreferenceTypes) {
        notEmpty(groupPreferenceTypes, "Invalid Group Preference Types");

        LOGGER.debug("Retrieving Group Preferences of Types {} for Group {}", groupPreferenceTypes, groupId);

        // Retrieve from the Repository
        List<GroupPreference> groupPreferences =
                groupPreferenceRepository.findByGroupIdAndTypeIn(groupId, groupPreferenceTypes);

        // If any are missing, use the default values
        if (groupPreferences.size() < groupPreferenceTypes.size()) {

            groupPreferences.addAll(
                    getDefaultPreferences(groupPreferenceTypes, groupPreferences, GroupPreference.class));
        }

        LOGGER.debug("Retrieved Group Preferences of Types {} for Group {}:\n{}",
                groupPreferenceTypes, groupId, groupPreferences);

        return groupPreferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Map<ProviderType, String> getScreeningSettingIds(String groupId,
                                                            ScreeningState screeningState) {
        /**
         * Prepare screening setting type to load based on Screening State
         */
        GroupPreferenceType groupPreferenceType = getScreeningSettingPreferenceType(screeningState);

        /**
         * Load preferences for type Screening Settings
         */
        GroupPreference screeningSettingPreference =
                getGroupPreference(groupId, groupPreferenceType);

        /**
         * Convert to a map of Provider & Screening Setting ids
         */
        Map<ProviderType, String> providersScreeningSettingIds = new HashMap<>();
        for (String providerString : screeningSettingPreference.getEntries().keySet()) {
            providersScreeningSettingIds.put(ProviderType.valueOf(providerString),
                    screeningSettingPreference.getEntries()
                            .get(providerString));
        }
        return providersScreeningSettingIds;
    }

    /**
     * Get the Screening Setting preference type based on the provided Screening State
     *
     * @param screeningState screening state
     * @return GroupPreferenceType representing appropriate screening Type
     */
    private GroupPreferenceType getScreeningSettingPreferenceType(ScreeningState screeningState) {
        GroupPreferenceType groupPreferenceType;
        if (ScreeningState.INITIAL.equals(screeningState)) {
            groupPreferenceType = GroupPreferenceType.SCREENING_SETTING_INITIAL;
        } else if (ScreeningState.ONGOING.equals(screeningState)) {
            groupPreferenceType = GroupPreferenceType.SCREENING_SETTING_ONGOING;
        } else if (ScreeningState.ADHOC.equals(screeningState)) {
            groupPreferenceType = GroupPreferenceType.SCREENING_SETTING_ADHOC;
        } else {
            throw new IllegalArgumentException("Screening State not supported");
        }
        return groupPreferenceType;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GroupPreference> saveGroupPreferences(List<GroupPreference> groupPreferences) {
        notNull(groupPreferences, "Group Preferences to be saved must not be null");
        notEmpty(groupPreferences, "Group Preferences to be saved must not be empty");

        List<GroupPreference> savedGroupPreferences =
                groupPreferenceRepository.save(groupPreferences);

        LOGGER.debug("Saved Group Preference {}", savedGroupPreferences);

        return savedGroupPreferences;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public GroupPreference saveScreeningSettingPreference(String groupId,
                                                          ScreeningState screeningState,
                                                          ProviderType providerType,
                                                          String screeningSettingId) {
        /**
         * Validate the arguments
         */
        notNull(groupId, "Group Id must not be null");
        notNull(screeningState, "Screening state must not be null");
        notNull(providerType, "Screening state must not be null");

        GroupPreferenceType groupPreferenceType = getScreeningSettingPreferenceType(screeningState);

        /**
         * Load Group Pref if already exists
         */
        GroupPreference screeningSettingPreference =
                getGroupPreference(groupId, groupPreferenceType);
        if (screeningSettingPreference == null) {
            /**
             * Not exists Create new Group Preference
             */
            screeningSettingPreference = new GroupPreference();
            screeningSettingPreference.setType(groupPreferenceType);
        }
        screeningSettingPreference.getEntries().put(providerType.name(), screeningSettingId);

        /**
         * Persist the Group Preference
         */
        List<GroupPreference> savedGroupPreferences =
                saveGroupPreferences(Arrays.asList(screeningSettingPreference));
        notNull(savedGroupPreferences, "Screening Setting Group Preference must be saved");
        isTrue(savedGroupPreferences.size() == 1,
                "Exactly one instance of screening setting group must be saved");

        return savedGroupPreferences.get(0);
    }

    /**
     * Retrieves the {@link Preference}s with default entries for the specified {@link PreferenceType}s
     *
     * @param requiredPreferenceTypes The {@link Set} of all required {@link PreferenceType}s
     * @param retrievedPreferences    The {@link List} of {@link Preference}s that have already been retrieved
     * @param preferenceClass         The concrete {@link Preference} {@link Class}
     * @param <T>                     The {@link PreferenceType} type
     * @param <U>                     The {@link Preference} type
     * @return {@link List} The collection containing {@link Preference}s with default entries
     */
    private <T extends Enum<T> & PreferenceType, U extends Preference<T>> List<U> getDefaultPreferences(
            Set<T> requiredPreferenceTypes, List<U> retrievedPreferences, Class<U> preferenceClass) {

        Set<T> retrievedPreferenceTypes = new HashSet<>();

        List<U> defaultPreferences = new ArrayList<>();

        for (U retrievedPreference : retrievedPreferences) {
            retrievedPreferenceTypes.add(retrievedPreference.getType());
        }
        Set<T> remainingPreferenceTypes = Sets.difference(requiredPreferenceTypes, retrievedPreferenceTypes);

        LOGGER.debug("Retrieving defaults for Types {}", remainingPreferenceTypes);

        for (T defaultPreferenceType : remainingPreferenceTypes) {

            U defaultPreference;
            try {
                defaultPreference = preferenceClass.newInstance();

                defaultPreference.setType(defaultPreferenceType);
                defaultPreference.setEntries(defaultPreferenceEntries.get(defaultPreferenceType));
            } catch (IllegalAccessException | InstantiationException e) {
                throw new IllegalArgumentException(String.format("Error instantiating %s", preferenceClass), e);
            }
            defaultPreferences.add(defaultPreference);
        }
        return defaultPreferences;
    }

    /**
     * Checks that some default entries exist for the specified {@link PreferenceType}
     *
     * @param preferenceType The {@link PreferenceType} to check for
     */
    private void checkDefaultPreference(PreferenceType preferenceType) {
        Map<String, String> entries = defaultPreferenceEntries.get(preferenceType);
        if (entries == null || entries.isEmpty()) {
            throw new IllegalStateException(
                    String.format("Could not retrieve default entries for %s", preferenceType));
        }
    }
}
