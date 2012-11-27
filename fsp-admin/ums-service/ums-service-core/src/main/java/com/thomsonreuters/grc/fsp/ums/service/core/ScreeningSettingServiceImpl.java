package com.thomsonreuters.grc.fsp.ums.service.core;


import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.ums.client.core.PreferenceService;
import com.thomsonreuters.grc.fsp.ums.client.core.ScreeningSettingService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.setting.ScreeningSettingRepository;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Screening Setting Service Implementation
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 16/10/12
 */
@Transactional
public class ScreeningSettingServiceImpl implements ScreeningSettingService {

    /**
     * Screening Setting Repository
     */
    private ScreeningSettingRepository screeningSettingRepository;


    /**
     * Preference Service
     */
    private PreferenceService preferenceService;

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractScreeningSetting getScreeningSetting(String screenSettingId) {
        LOGGER.debug("Fetching Screening Setting with id {}", screenSettingId);
        Assert.notNull(screenSettingId, "Screening setting id must not be null");

        return screeningSettingRepository.findOne(screenSettingId);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<ProviderType, AbstractScreeningSetting> getScreeningSetting(String groupId,
                                                                           ScreeningState screeningState) {
        LOGGER.debug("Fetching {} Screening Setting for Group {}", screeningState, groupId);
        Assert.notNull(groupId, "Group id must not be null");
        Assert.notNull(screeningState, "Screening state not be null");

        /**
         * Load Screening Setting Ids for provided screening state
         */
        Map<ProviderType, String> screeningSettingIdsMap =
                preferenceService.getScreeningSettingIds(groupId, screeningState);

        /**
         * Load screening settings from Repo and store in map
         */
        Map<ProviderType, AbstractScreeningSetting> screeningSettingMap = new HashMap<>();
        for (ProviderType providerType : screeningSettingIdsMap.keySet()) {
            String screeningSettingId = screeningSettingIdsMap.get(providerType);
            if (screeningSettingId != null) {
                AbstractScreeningSetting screeningSetting =
                        screeningSettingRepository.findOne(screeningSettingId);
                if (screeningSetting != null) {
                    screeningSettingMap.put(providerType, screeningSetting);
                }
            }
        }

        return screeningSettingMap;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractScreeningSetting getScreeningSetting(String groupId,
                                                        ScreeningState screeningState,
                                                        ProviderType providerType) {
        LOGGER.debug("Fetching {} {} Screening Setting for Group {}", providerType, screeningState,
                screeningState, groupId);
        /**
         * Assert Request
         */
        assertRequest(groupId, screeningState, providerType);

        /**
         * Load the Screening Settings ids from the Preference
         */
        Map<ProviderType, String> screeningSettingIdsMap =
                preferenceService.getScreeningSettingIds(groupId, screeningState);
        String screeningSettingId = screeningSettingIdsMap.get(providerType);
        if (screeningSettingId != null) {
            return getScreeningSetting(screeningSettingId);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AbstractScreeningSetting saveScreeningSetting(String groupId,
                                                         ScreeningState screeningState,
                                                         ProviderType providerType,
                                                         AbstractScreeningSetting abstractScreeningSetting) {
        LOGGER.debug("Save {} {} Screening Setting {} for Group {}", providerType, screeningState,
                abstractScreeningSetting, groupId);
        /**
         * Assert Request
         */
        assertRequest(groupId, screeningState, providerType);

        /**
         * Save Screening Settings
         */
        AbstractScreeningSetting savedScreeningSetting = null;
        String screeningSettingId = null;
        if (abstractScreeningSetting != null) {
            savedScreeningSetting = saveScreeningSetting(abstractScreeningSetting);
            screeningSettingId = savedScreeningSetting.getId();
        }

        /**
         * Save Screening Setting Id as Group Preference for the given Provider and Screening State
         */
        LOGGER
                .debug("Saving {} {} Screening Setting Id {} as Group Pref for Group {} ", providerType,
                        screeningState, screeningSettingId, groupId);
        GroupPreference groupPreference = preferenceService
                .saveScreeningSettingPreference(groupId, screeningState, providerType,
                        screeningSettingId);
        Assert.notNull(groupPreference, "Saved Group Preference must not be null");
        return savedScreeningSetting;
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public Map<ProviderType, AbstractScreeningSetting> saveScreeningSettings(String groupId,
                                                                             ScreeningState screeningState,
                                                                             Map<ProviderType, AbstractScreeningSetting> screeningSettingsMap) {
        LOGGER.debug("Save {} Screening Settings {} for Group {}", screeningState,
                screeningSettingsMap, groupId);

        Map<ProviderType, AbstractScreeningSetting> savedScreeningSettings = new HashMap<>();

        /**
         * For each of the Provider Save the Screening Setting and Group Preference
         */
        for (ProviderType providerType : screeningSettingsMap.keySet()) {
            AbstractScreeningSetting screeningSetting = screeningSettingsMap.get(providerType);
            AbstractScreeningSetting savedScreeningSetting =
                    saveScreeningSetting(groupId, screeningState, providerType, screeningSetting);
            savedScreeningSettings.put(providerType, savedScreeningSetting);
        }

        return savedScreeningSettings;
    }


    /**
     * Save Screening Setting into the DB using Repository.
     * Only save if the instance not present in the DB, if the instance already existing return
     * the existing instance
     *
     * @param screeningSetting Screening Setting instance to be Persisted
     * @return Persisted Screening Setting instance
     */
    private AbstractScreeningSetting saveScreeningSetting(
            AbstractScreeningSetting screeningSetting) {

        AbstractScreeningSetting savedScreeningSetting = null;
        LOGGER.debug("Checking... if Screening Setting {} already exists", screeningSetting);
        /**
         * Find if the same setting exists already exist then return the setting
         */
        List<AbstractScreeningSetting> screeningSettings =
                screeningSettingRepository.findByHash(screeningSetting.hashCode());
        if (!screeningSettings.isEmpty()) {
            for (AbstractScreeningSetting abstractScreeningSetting : screeningSettings) {
                /**
                 * In some cases hash is same for different objects verify that objects are
                 * actually Equal
                 */
                if (abstractScreeningSetting.equals(screeningSetting)) {
                    LOGGER.debug("Screening Setting Found, returning existing instance with id {}",
                            screeningSetting.getId());
                    savedScreeningSetting = abstractScreeningSetting;
                    break;
                }
            }
        }

        /**
         * If Settings not found Persist the Screening Settings
         */
        if (savedScreeningSetting == null) {
            LOGGER.debug("Screening Settings Not Found, saving screening setting {}",
                    screeningSetting);
            savedScreeningSetting = screeningSettingRepository.save(screeningSetting);
        }

        return savedScreeningSetting;
    }

    /**
     * Validate group id, screening state and provider Type
     *
     * @param groupId        group id to be validated
     * @param screeningState screening state to be validated
     * @param providerType   provider type to be validated
     */
    private void assertRequest(String groupId, ScreeningState screeningState,
                               ProviderType providerType) {
        Assert.notNull(groupId, "Group id must not be null");
        Assert.notNull(screeningState, "Screening State must not be null");
        Assert.notNull(providerType, "Provider Type must not be null");
    }


    /**
     * Inject Screening Setting Repo
     *
     * @param screeningSettingRepository screening setting repo
     */
    @Required
    public void setScreeningSettingRepository(
            ScreeningSettingRepository screeningSettingRepository) {
        this.screeningSettingRepository = screeningSettingRepository;
    }

    /**
     * Inject Preference service
     *
     * @param preferenceService Preference Service
     */
    @Required
    public void setPreferenceService(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }
}
