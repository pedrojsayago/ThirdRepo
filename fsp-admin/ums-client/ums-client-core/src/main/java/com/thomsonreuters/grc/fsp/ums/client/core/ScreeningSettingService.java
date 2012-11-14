package com.thomsonreuters.grc.fsp.ums.client.core;

import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;

import java.util.Map;

/**
 * Screening Setting Service Definition
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 26/10/12
 */
public interface ScreeningSettingService {

    /**
     * Get Screen Setting based on the screenSettingId, it can be WatchlistScreeningSetting/
     * MediaScreeningSetting
     *
     * @param screenSettingId unique screen setting identifier
     * @return ScreeningSetting
     */
    AbstractScreeningSetting getScreeningSetting(String screenSettingId);

    /**
     * Get Group Screening Setting by provider type and screening State
     *
     * @param groupId        Group whose screening settings are required
     * @param screeningState Screening State (Initial /Ongoing /Adhoc)
     * @return Map<ProviderType, AbstractScreeningSetting>
     */
    Map<ProviderType, AbstractScreeningSetting> getScreeningSetting(String groupId,
                                                                    ScreeningState screeningState);


    /**
     * Get Group Screening Setting by provider type and screening State
     *
     * @param groupId        Group whose screening settings are required
     * @param screeningState Screening State (Initial /Ongoing /ADHOC)
     * @param providerType   Provider Type whose screening settings are required
     * @return AbstractScreeningSetting
     */
    AbstractScreeningSetting getScreeningSetting(String groupId, ScreeningState screeningState,
                                                 ProviderType providerType);


    /**
     * Get Group Screening Setting by provider type and screening State
     *
     * @param groupId                  Group whose screening settings to be saved
     * @param screeningState           Screening State (Initial /Ongoing /ADHOC)
     * @param providerType             Watchlist /Media /EIDV
     * @param abstractScreeningSetting Abstract Screening Setting to be saved
     * @return AbstractScreeningSetting
     */
    AbstractScreeningSetting saveScreeningSetting(String groupId, ScreeningState screeningState,
                                                  ProviderType providerType,
                                                  AbstractScreeningSetting abstractScreeningSetting);

    /**
     * Get Group Screening Setting by provider type and screening State
     *
     * @param groupId              Group whose screening settings to be saved
     * @param screeningState       Screening State (Initial /Ongoing /ADHOC)
     * @param screeningSettingsMap Screening Settings Map by providers
     * @return AbstractScreeningSetting
     */
    Map<ProviderType, AbstractScreeningSetting> saveScreeningSettings(String groupId,
                                                                      ScreeningState screeningState,
                                                                      Map<ProviderType, AbstractScreeningSetting> screeningSettingsMap);
}
