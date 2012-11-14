package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.common.base.type.core.DataSet;
import com.thomsonreuters.grc.fsp.common.base.type.core.DataType;
import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.ums.client.core.PreferenceService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.setting.ScreeningSettingRepository;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.EidVerificationSetting;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.FatcaVerificationSetting;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.WatchlistScreeningSetting;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;


/**
 * Screener Setting Service Test
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 18/10/12
 */
public class ScreeningSettingTest {

    /**
     * Logger ScreenManagerTest screener Test
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningSettingTest.class);

    /**
     * Screener Setting service to be tested
     */
    private ScreeningSettingServiceImpl screeningSettingService = new ScreeningSettingServiceImpl();


    /**
     * Screening Setting ID exists in db
     */
    private static final String SCREENING_SETTING_ID = "SCREENING_SETTING_ID_1";

    /**
     * Dummy Group ID
     */
    private static final String GROUP_ID = "GROUP_ID_1";

    /**
     * Screening Setting ID exists in db
     */
    private AbstractScreeningSetting screeningSetting;

    private Map<ProviderType, String> providersScreeningSettingMap = new HashMap<>();


    /**
     * Mocked Preference Service
     */
    private PreferenceService mockedPreferenceService;

    /**
     * Mocked Screening Setting Repository
     */
    private ScreeningSettingRepository mockedScreeningSettingRepository;


    /**
     * Can Get Screening Setting by ID
     */
    @Test
    public void canGetScreeningSettingById() {

        /**
         * Mock Screening Setting Repo
         */
        Mockito.when(mockedScreeningSettingRepository.findOne(SCREENING_SETTING_ID)).
                thenReturn(screeningSetting);

        /**
         * ACT
         */
        AbstractScreeningSetting abstractScreeningSetting =
                screeningSettingService.getScreeningSetting(SCREENING_SETTING_ID);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1)).findOne(SCREENING_SETTING_ID);

        LOGGER.info(abstractScreeningSetting.toString());
    }

    /**
     * Can Get Watchlist Screening Setting by Group & Screening State
     */
    @Test
    public void canGetWatchlistScreeningSettingByGroup() {

        /**
         * Mock Screening Setting Repo
         */
        Mockito
                .when(mockedPreferenceService.getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL)).
                thenReturn(providersScreeningSettingMap);
        Mockito.when(mockedScreeningSettingRepository.findOne(SCREENING_SETTING_ID)).
                thenReturn(screeningSetting);

        /**
         * ACT
         */
        AbstractScreeningSetting watchlistScreeningSetting = screeningSettingService
                .getScreeningSetting(GROUP_ID, ScreeningState.INITIAL, ProviderType.WATCHLIST);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1)).findOne(SCREENING_SETTING_ID);
        Mockito.verify(mockedPreferenceService, times(1))
                .getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL);

        Assert.assertNotNull("Watchlist settings define in Group Prefs, It must not be null",
                watchlistScreeningSetting);
    }

    /**
     * Can Get Media Screening Setting by Group & Provider (though media settings defined as null)
     */
    @Test
    public void canGetMediaScreeningSettingByGroup() {

        /**
         * Mock Screening Setting Repo
         */
        Mockito
                .when(mockedPreferenceService.getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL)).
                thenReturn(providersScreeningSettingMap);

        /**
         * ACT
         */
        AbstractScreeningSetting mediaScreeningSetting = screeningSettingService
                .getScreeningSetting(GROUP_ID, ScreeningState.INITIAL, ProviderType.MEDIA);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(0)).findOne(SCREENING_SETTING_ID);
        Mockito.verify(mockedPreferenceService, times(1))
                .getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL);

        Assert.assertNull("No media settings define in Group Prefs, It must be null",
                mediaScreeningSetting);
    }

    /**
     * Can Get EID Screening Setting by Group & Provider (though eid settings not defined at all
     * not even as null, no entry for EID present in the Group Prefs)
     */
    @Test
    public void canGetEIDScreeningSettingByGroup() {

        /**
         * Mock Pref Service
         */
        Mockito
                .when(mockedPreferenceService.getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL)).
                thenReturn(providersScreeningSettingMap);

        /**
         * ACT
         */
        AbstractScreeningSetting mediaScreeningSetting = screeningSettingService
                .getScreeningSetting(GROUP_ID, ScreeningState.INITIAL, ProviderType.EIDV);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(0)).findOne(SCREENING_SETTING_ID);
        Mockito.verify(mockedPreferenceService, times(1))
                .getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL);

        Assert.assertNull("No EID settings define in Group Prefs, It must be null",
                mediaScreeningSetting);
    }

    /**
     * Can Get Map of Screening Setting by Group & Screening State
     */
    @Test
    public void canGetScreeningSettingsByGroup() {

        /**
         * Mock Screening Setting Repo
         */
        Mockito
                .when(mockedPreferenceService.getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL)).
                thenReturn(providersScreeningSettingMap);
        Mockito.when(mockedScreeningSettingRepository.findOne(SCREENING_SETTING_ID)).
                thenReturn(screeningSetting);

        /**
         * ACT
         */
        Map<ProviderType, AbstractScreeningSetting> screeningSettingsMap =
                screeningSettingService.getScreeningSetting(GROUP_ID, ScreeningState.INITIAL);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1)).findOne(SCREENING_SETTING_ID);
        Mockito.verify(mockedPreferenceService, times(1))
                .getScreeningSettingIds(GROUP_ID, ScreeningState.INITIAL);

        Assert.assertNotNull("Screening Setting map must not be null", screeningSettingsMap);
        Assert.assertEquals("Screening Setting map must have Watchlist Setting defined", 1,
                screeningSettingsMap.size());
        Assert.assertEquals("Screening Setting map must have Watchlist Setting defined",
                screeningSetting, screeningSettingsMap.get(ProviderType.WATCHLIST));
    }

    /**
     * Can save Screening Setting by Group & Screening State which already existing in DB
     */
    @Test
    public void canSaveExistingScreeningSetting() {

        /**
         * Test Data
         */
        AbstractScreeningSetting screeningSetting1 = getScreeningSetting();
        GroupPreference groupPreference = new GroupPreference.Builder().build();

        /**
         * Mock Screening Setting Repo
         */
        Mockito.when(mockedScreeningSettingRepository.findByHash(screeningSetting1.hashCode())).
                thenReturn(Arrays.asList(screeningSetting));

        Mockito.when(mockedPreferenceService
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST,
                        screeningSetting.getId())).
                thenReturn(groupPreference);

        /**
         * ACT
         */
        AbstractScreeningSetting savedScreeningSetting = screeningSettingService
                .saveScreeningSetting(GROUP_ID, ScreeningState.INITIAL, ProviderType.WATCHLIST,
                        screeningSetting1);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1))
                .findByHash(screeningSetting1.hashCode());
        Mockito.verify(mockedPreferenceService, times(1))
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST, screeningSetting.getId());
        /**
         * Setting already existing there must not be any call to actually save settings in DB
         */
        Mockito.verify(mockedScreeningSettingRepository, times(0)).save(screeningSetting1);

        Assert.assertNotNull("Screening Setting must not be null", savedScreeningSetting);
    }

    /**
     * Can save Screening Setting by Group & Screening State which already existing in DB
     */
    @Test
    public void canSaveNonExistingScreeningSetting() {

        /**
         * Test Data
         */
        AbstractScreeningSetting screeningSetting1 = getScreeningSetting();
        GroupPreference groupPreference = new GroupPreference.Builder().build();

        /**
         * Mock Screening Setting Repo, FindByHash found no setting in DB
         */
        Mockito.when(mockedScreeningSettingRepository.findByHash(screeningSetting1.hashCode())).
                thenReturn(new ArrayList<AbstractScreeningSetting>());
        Mockito.when(mockedScreeningSettingRepository.save(screeningSetting1)).
                thenReturn(screeningSetting1);
        Mockito.when(mockedPreferenceService
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST,
                        screeningSetting.getId())).
                thenReturn(groupPreference);

        /**
         * ACT
         */
        AbstractScreeningSetting savedScreeningSetting = screeningSettingService
                .saveScreeningSetting(GROUP_ID, ScreeningState.INITIAL, ProviderType.WATCHLIST,
                        screeningSetting1);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1))
                .findByHash(screeningSetting1.hashCode());
        Mockito.verify(mockedPreferenceService, times(1))
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST, screeningSetting.getId());
        /**
         * save non-existing settings, must actually save setting into DB
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1)).save(screeningSetting1);

        Assert.assertNotNull("Screening Setting must not be null", savedScreeningSetting);
    }

    /**
     * Can save Screening Setting by Group & Screening State which already existing in DB
     */
    @Test
    public void canSaveScreeningSettings() {

        /**
         * Test Data
         */
        GroupPreference groupPreference = new GroupPreference.Builder().build();
        AbstractScreeningSetting watchlistScreeningSetting = getScreeningSetting();
        AbstractScreeningSetting eidvScreeningSetting = new EidVerificationSetting.Builder().build();
        AbstractScreeningSetting fatcaVerificationSetting = new FatcaVerificationSetting.Builder().build();
        eidvScreeningSetting.setId("eidv_id");
        Map<ProviderType, AbstractScreeningSetting> screeningSettingsMap = new HashMap<>();
        screeningSettingsMap.put(ProviderType.WATCHLIST, watchlistScreeningSetting);
        screeningSettingsMap.put(ProviderType.MEDIA, null);
        screeningSettingsMap.put(ProviderType.EIDV, eidvScreeningSetting);

        /**
         * Mock Screening Setting Repo, FindByHash
         */
        Mockito
                .when(mockedScreeningSettingRepository.findByHash(watchlistScreeningSetting.hashCode()))
                .thenReturn(Arrays.asList(screeningSetting));
        Mockito.when(mockedScreeningSettingRepository.findByHash(eidvScreeningSetting.hashCode()))
                .thenReturn(Arrays.asList(fatcaVerificationSetting));
        Mockito.when(mockedScreeningSettingRepository.save(eidvScreeningSetting)).
                thenReturn(eidvScreeningSetting);
        Mockito.when(mockedPreferenceService
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST,
                        watchlistScreeningSetting.getId())).
                thenReturn(groupPreference);
        Mockito.when(mockedPreferenceService
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.EIDV,
                        eidvScreeningSetting.getId())).
                thenReturn(groupPreference);
        Mockito.when(mockedPreferenceService
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.MEDIA, null)).
                thenReturn(groupPreference);

        /**
         * ACT
         */
        Map<ProviderType, AbstractScreeningSetting> savedScreeningSettings = screeningSettingService
                .saveScreeningSettings(GROUP_ID, ScreeningState.INITIAL, screeningSettingsMap);

        /**
         * Verify
         */
        Mockito.verify(mockedScreeningSettingRepository, times(1))
                .findByHash(watchlistScreeningSetting.hashCode());
        Mockito.verify(mockedScreeningSettingRepository, times(1))
                .findByHash(eidvScreeningSetting.hashCode());
        /**
         * Group Preference must be save for each provider with appropriate Setting Id
         */
        Mockito.verify(mockedPreferenceService, times(1))
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL,
                        ProviderType.WATCHLIST, screeningSetting.getId());
        Mockito.verify(mockedPreferenceService, times(1))
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL, ProviderType.MEDIA,
                        null);
        Mockito.verify(mockedPreferenceService, times(1))
                .saveScreeningSettingPreference(GROUP_ID, ScreeningState.INITIAL, ProviderType.EIDV,
                        eidvScreeningSetting.getId());
        /**
         * save eid settings but not watchlist. watchlist setting already present in DB
         */
        Mockito.verify(mockedScreeningSettingRepository, times(0)).save(watchlistScreeningSetting);
        Mockito.verify(mockedScreeningSettingRepository, times(1)).save(eidvScreeningSetting);

        Assert.assertNotNull("Screening Setting map must not be null", savedScreeningSettings);
        Assert.assertEquals("Screening Setting map must have 3 settings Watchlist, Media & EIDV", 3,
                savedScreeningSettings.size());
        Assert.assertEquals("Screening Setting map must have Watchlist Setting defined",
                watchlistScreeningSetting,
                savedScreeningSettings.get(ProviderType.WATCHLIST));
        Assert.assertEquals("Media Screening Settings must be null", null,
                savedScreeningSettings.get(ProviderType.MEDIA));
    }


    /**
     * Initialise Test Data
     */
    @Before
    public void init() {
        /**
         * Mock the Preference Service and Screening Setting Repo
         */
        mockedScreeningSettingRepository = mock(ScreeningSettingRepository.class);
        mockedPreferenceService = mock(PreferenceService.class);

        screeningSettingService.setPreferenceService(mockedPreferenceService);
        screeningSettingService.setScreeningSettingRepository(mockedScreeningSettingRepository);

        screeningSetting = getScreeningSetting();

        providersScreeningSettingMap.put(ProviderType.WATCHLIST, "SCREENING_SETTING_ID_1");
        providersScreeningSettingMap.put(ProviderType.MEDIA, null);
    }

    /**
     * Get Dummy Screening setting
     *
     * @return Abstract Screen
     */
    private AbstractScreeningSetting getScreeningSetting() {
        AbstractScreeningSetting screeningSetting = new WatchlistScreeningSetting.Builder().build();
        screeningSetting.setId(SCREENING_SETTING_ID);
        screeningSetting.setScoreThreshold(80.0);
        WatchlistScreeningSetting watchlistScreeningSetting =
                ((WatchlistScreeningSetting) screeningSetting);
        watchlistScreeningSetting.setDataSet(DataSet.WORLDCHECK);
        watchlistScreeningSetting.setDataType(DataType.PREMIUM_PLUS);
        watchlistScreeningSetting.setLowQualityAkaEnabled(Boolean.FALSE);
        watchlistScreeningSetting
                .setSourceIds(new HashSet<>(Arrays.asList("b_trwc_1", "b_trwc_2")));
        watchlistScreeningSetting
                .setExcludedSourceIds(new HashSet<>(Arrays.asList("b_trwc_10", "b_trwc_20")));
        watchlistScreeningSetting
                .setSourceTypeIds(new HashSet<>(Arrays.asList("t_trwc_4", "t_trwc_5")));
        return screeningSetting;
    }
}