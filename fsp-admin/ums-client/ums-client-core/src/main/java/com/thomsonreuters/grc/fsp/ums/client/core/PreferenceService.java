package com.thomsonreuters.grc.fsp.ums.client.core;

import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.CustomFieldType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.StatusLink;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Preference Service Definition
 */
public interface PreferenceService {

    /**
     * Retrieves the {@link CustomFieldType} for the specified {@code CustomFieldType} ID
     *
     * @param customFieldTypeId The {@link String} {@code CustomFieldType} ID to find by
     * @return {@link CustomFieldType} The {@link CustomFieldType}
     */
    CustomFieldType getCustomFieldType(String customFieldTypeId);

    /**
     * Retrieves the {@link CustomFieldType}s for the specified {@code CustomFieldType} IDs
     *
     * @param customFieldTypeIds The collection containing {@link String} {@code CustomFieldType} IDs to find by
     * @return {@link List} The collection containing matching {@link CustomFieldType}s
     */
    List<CustomFieldType> getCustomFieldTypes(List<String> customFieldTypeIds);

    /**
     * Retrieves the {@link CustomFieldType}s for the specified {@code Group} ID
     *
     * @param groupId The {@link String} {@code Group} ID to find by
     * @return {@link List} The collection containing matching {@link CustomFieldType}s
     */
    List<CustomFieldType> getCustomFieldTypesByGroup(String groupId);

    /**
     * Method retrieves {@link StatusLink}s for the specified {@code Group} ID
     *
     * @param groupId - The {@link String} {@code Group} ID to find by
     * @return {@link List} The collection containing {@link StatusLink}s
     */
    List<StatusLink> getResolutionLinksByGroup(String groupId);

    /**
     * Retrieves the {@link GroupPreference} for the specified {@code Group} ID and {@link GroupPreferenceType}
     *
     * @param groupId             The {@link String} {@code Group} ID to find by
     * @param groupPreferenceType The {@link GroupPreferenceType} to find by
     * @return {@link GroupPreference} The matching {@link GroupPreference}
     */
    GroupPreference getGroupPreference(String groupId, GroupPreferenceType groupPreferenceType);

    /**
     * Retrieves the {@link GroupPreference}s for the specified {@code Group} ID
     *
     * @param groupId The {@link String} {@code Group} ID to find by
     * @return {@link List} The collection containing matching {@link GroupPreference}s
     */
    List<GroupPreference> getGroupPreferences(String groupId);

    /**
     * Retrieves the {@link GroupPreference}s for the specified {@code Group} ID and {@link GroupPreferenceType}s
     *
     * @param groupId              The {@link String} {@code Group} ID to find by
     * @param groupPreferenceTypes The {@link Set} containing {@link GroupPreferenceType}s to find by
     * @return {@link List} The collection containing matching {@link GroupPreference}s
     */
    List<GroupPreference> getGroupPreferences(String groupId, Set<GroupPreferenceType> groupPreferenceTypes);


    /**
     * Retrieves the {@link String} specified Screening Setting Id for the specified {@code
     * Group} ID
     *
     * @param groupId        The {@link String} {@code Group} ID to find by
     * @param screeningState The {@link ScreeningState }(Initial /Ongoing /Adhoc)
     * @return Setting Ids by providers e.g <WATCHLIST,123456> <MEDIA,789456>
     */
    Map<ProviderType, String> getScreeningSettingIds(String groupId, ScreeningState screeningState);

    /**
     * Saves the {@link GroupPreference}
     *
     * @param groupPreferences The {@link Set} containing {@link GroupPreference}s to be saved
     * @return {@link List} The collection containing matching {@link GroupPreference}s
     */
    List<GroupPreference> saveGroupPreferences(List<GroupPreference> groupPreferences);


    /**
     * Save Screening Setting Preference
     *
     * @param groupId            The {@link String} {@code Group} to save preference for
     * @param screeningState     The {@link ScreeningState } type of setting (Initial /Ongoing)
     * @param providerType       The {@link ProviderType } type the provider
     * @param screeningSettingId The {@link String } Screening Setting Id
     * @return {@link GroupPreference}s
     */
    GroupPreference saveScreeningSettingPreference(String groupId, ScreeningState screeningState,
                                                   ProviderType providerType,
                                                   String screeningSettingId);

}
