/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.setting;


import com.thomsonreuters.grc.fsp.common.base.type.core.DataSet;
import com.thomsonreuters.grc.fsp.common.base.type.core.DataType;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;
import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.WatchlistScreeningSetting;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Test Screening Setting Repository
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 22 Oct 2012
 */
@Transactional
public class ScreeningSettingRepositoryTest extends BaseDataTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ScreeningSettingRepositoryTest.class);

    /**
     * Screening Setting ID exists in db
     */
    private static final String SCREENING_SETTING_EXISTS = "SCREENING_SETTING_ID_1";

    /**
     * Screening Setting Repository to be tested
     */
    @Autowired
    private ScreeningSettingRepository screeningSettingRepository;


    /**
     * Watchlist screening setting
     */
    private WatchlistScreeningSetting watchlistScreeningSetting;

    /**
     * Source IDs
     */
    private Set<String> sourceIds = new HashSet<>();

    /**
     * Exclusion Source List
     */
    private Set<String> excludedSourceIds = new HashSet<>();


    /**
     * Source Type ids
     */
    private Set<String> sourceTypeIds = new HashSet<>();

    /**
     * Initialise Test Data
     */
    @Before
    public void init() {

        /**
         * Init Sources, exclusion list and Source Type Ids
         */
        sourceIds.add("b_trwc_1");
        sourceIds.add("b_trwc_2");
        excludedSourceIds.add("b_trwc_10");
        excludedSourceIds.add("b_trwc_05");
        sourceTypeIds.add("t_trwc_1");
        sourceTypeIds.add("t_trwc_2");

        /**
         * Init watchlist screening settings
         */
        watchlistScreeningSetting = new WatchlistScreeningSetting.Builder().withGeneratedId().build();
        watchlistScreeningSetting.setId("id1");
        watchlistScreeningSetting.setScoreThreshold(90.0);
        watchlistScreeningSetting.setDataSet(DataSet.WORLDCHECK);
        watchlistScreeningSetting.setDataType(DataType.PREMIUM_PLUS);
        watchlistScreeningSetting.setLowQualityAkaEnabled(Boolean.TRUE);

        /**
         * Exclusion List
         */
        watchlistScreeningSetting.getExcludedSourceIds().addAll(excludedSourceIds);
        /**
         * Provider Source Type Category
         */
        watchlistScreeningSetting.getSourceIds().addAll(sourceIds);
        /**
         * Sources
         */
        watchlistScreeningSetting.getSourceTypeIds().addAll(sourceTypeIds);
    }


    /**
     * Can Load Screening setting by id
     */
    @Test
    @Transactional(readOnly = true)
    public void canLoadScreeningSetting() {

        /**
         * ACT
         */
        AbstractScreeningSetting abstractScreeningSetting =
                screeningSettingRepository.findOne(SCREENING_SETTING_EXISTS);

        /**
         * VERIFY
         */
        Assert.assertNotNull("Screening Setting of id SCREENING_SETTING_ID_1 must not be null",
                abstractScreeningSetting);
        Assert.assertTrue("SCREENING_SETTING_ID_1 must represent the Watchlist Screening Settings",
                abstractScreeningSetting instanceof WatchlistScreeningSetting);
        WatchlistScreeningSetting watchlistScreeningSetting1 =
                (WatchlistScreeningSetting) abstractScreeningSetting;

        Assert.assertEquals("HashCode must be same",
                Integer.valueOf(watchlistScreeningSetting1.hashCode()),
                watchlistScreeningSetting1.getHash());
    }

    /**
     * Can Load Screening settings by Hash
     */
    @Test
    @Transactional(readOnly = true)
    public void canLoadScreeningSettingByHash() {

        /**
         * ACT
         */
        List<? extends AbstractScreeningSetting> abstractScreeningSettings =
                screeningSettingRepository.findByHash(-718419470);

        /**
         * VERIFY
         */
        Assert.assertNotNull("Screening Settings for hash -718419470 must not be null",
                abstractScreeningSettings);
        Assert.assertFalse("Screening Settings for hash -718419470 must not be Empty",
                abstractScreeningSettings.isEmpty());
        Assert.assertEquals("Screening Settings for hash -718419470 must have 2 result found", 2,
                abstractScreeningSettings.size());
    }


    /**
     * Can save Screening setting by id
     */
    @Test
    public void canSaveScreeningSetting() {

        /**
         * ACT
         */
        WatchlistScreeningSetting savedWatchlistScreeningSetting =
                screeningSettingRepository.save(watchlistScreeningSetting);

        /**
         * VERIFY
         */
        Object[] params =
                {watchlistScreeningSetting, sourceIds, excludedSourceIds, sourceTypeIds, 90.0,
                        DataSet.WORLDCHECK, DataType.PREMIUM_PLUS, Boolean.TRUE};
        assertWatchlistScreening(savedWatchlistScreeningSetting, params);
    }

    /**
     * Can save Screening setting by id
     * Case 1 use save(S)
     */
    @Test
    public void canSaveScreeningSettings() {

        List<Object[]> paramsList = getParams();
        Set<Integer> existingHash = new HashSet<>();

        for (Object[] params : paramsList) {

            /**
             * ACT
             */
            WatchlistScreeningSetting savedWatchlistScreeningSetting =
                    screeningSettingRepository.save((WatchlistScreeningSetting) params[0]);

            /**
             * VERIFY
             */
            assertWatchlistScreening(savedWatchlistScreeningSetting, params);
            Assert.assertFalse("Hash must be unique so must not be present ",
                    existingHash.contains(savedWatchlistScreeningSetting.getHash()));
            existingHash.add(savedWatchlistScreeningSetting.getHash());
        }
    }

    /**
     * Can save Screening setting by id
     * Case 2 use save(Iterable<S>)
     */
    @Test
    public void canSaveCollectionScreeningSettings() {

        /**
         * Prepare DATA
         */
        List<Object[]> paramsList = getParams();
        List<WatchlistScreeningSetting> watchlistScreeningSettings = new ArrayList<>();
        for (Object[] params : paramsList) {
            watchlistScreeningSettings.add((WatchlistScreeningSetting) params[0]);
        }

        /**
         * ACT
         */
        List<WatchlistScreeningSetting> savedScreeningSettings =
                screeningSettingRepository.save(watchlistScreeningSettings);

        /**
         * Verify
         */
        int i = 0;
        for (WatchlistScreeningSetting watchlistScreeningSetting1 : savedScreeningSettings) {
            assertWatchlistScreening(watchlistScreeningSetting1, paramsList.get(i++));
        }
    }


    /**
     * Assert Watchlist screening settings
     *
     * @param screeningSetting saved screening settings
     * @param params           params uses in the construction of Watchlist Screening
     *                         Settings
     */
    private void assertWatchlistScreening(WatchlistScreeningSetting screeningSetting,
                                          Object[] params) {
        Assert.assertEquals("Saved Screening setting must have version 0", 0,
                screeningSetting.getVersion());
        Assert.assertNotNull("Saved Screening setting must not be null", screeningSetting);
        Assert
                .assertNotNull("Saved Screening setting ID must not be null", screeningSetting.getId());

        Assert.assertEquals("source Ids must be same after save", params[1],
                screeningSetting.getSourceIds());
        Assert.assertEquals("source exclusion Ids must be same after save", params[2],
                screeningSetting.getExcludedSourceIds());
        Assert.assertEquals("source Type Ids must be same after save", params[3],
                screeningSetting.getSourceTypeIds());
        Assert.assertEquals("Score must be same after save", params[4],
                screeningSetting.getScoreThreshold());
        Assert.assertEquals("DataSet must be same after save", params[5],
                screeningSetting.getDataSet());
        Assert.assertEquals("Data Type Ids must be same after save", params[6],
                screeningSetting.getDataType());
        Assert.assertEquals("lowAkaEnabled Flag must be same after save", params[7],
                screeningSetting.getLowQualityAkaEnabled());

        Assert.assertNotNull("Saved Screening setting Hash must not be null",
                screeningSetting.getHash());
        Assert.assertEquals("HashCode must be same after save",
                Integer.valueOf(screeningSetting.hashCode()),
                screeningSetting.getHash());
    }


    /**
     * Helper method to get test data
     *
     * @return List of Object[] containing watchlist screening settings and items used to prepare
     *         the settings to be used in validations
     */
    private List<Object[]> getParams() {

        /**
         * Screening Setting 1 & 2
         */
        Set<String> sourceIdsSet1 = new HashSet<>(Arrays.asList("b_trwc_1", "b_trwc_2"));
        Set<String> excludedSourceIdsSet1 = new HashSet<>(Arrays.asList("b_trwc_10", "b_trwc_11"));
        Set<String> sourceTypeIdsSet1 = new HashSet<>(Arrays.asList("t_trwc_1", "t_trwc_2"));
        WatchlistScreeningSetting watchlistScreeningSetting1 =
                getWatchlistScreeningSetting(sourceIdsSet1, excludedSourceIdsSet1, sourceTypeIdsSet1,
                        50.0, DataSet.WORLDCHECK, DataType.PREMIUM, Boolean.TRUE);
        WatchlistScreeningSetting watchlistScreeningSetting2 =
                getWatchlistScreeningSetting(sourceIdsSet1, excludedSourceIdsSet1, sourceTypeIdsSet1,
                        51.0, DataSet.WORLDCHECK, DataType.PREMIUM, Boolean.TRUE);

        /**
         * Screening Setting 3 & 4
         */
        Set<String> sourceIdsSet2 = new HashSet<>(Arrays.asList("b_trwc_3", "b_trwc_4"));
        Set<String> excludedSourceIdsSet2 = new HashSet<>(Arrays.asList("b_trwc_19", "b_trwc_20"));
        Set<String> sourceTypeIdsSet2 = new HashSet<>(Arrays.asList("t_trwc_3", "t_trwc_4"));

        WatchlistScreeningSetting watchlistScreeningSetting3 =
                getWatchlistScreeningSetting(sourceIdsSet2, excludedSourceIdsSet2, sourceTypeIdsSet2,
                        60.0, DataSet.WORLDCHECK, DataType.PREMIUM, Boolean.TRUE);
        WatchlistScreeningSetting watchlistScreeningSetting4 =
                getWatchlistScreeningSetting(sourceIdsSet2, excludedSourceIdsSet2, sourceTypeIdsSet2,
                        61.0, DataSet.WORLDCHECK, DataType.PREMIUM, Boolean.TRUE);

        /**
         * Prepare Params List
         */
        List<Object[]> argsList = new ArrayList<>();
        argsList.add(new Object[]{watchlistScreeningSetting1, sourceIdsSet1, excludedSourceIdsSet1,
                sourceTypeIdsSet1, 50.0, DataSet.WORLDCHECK, DataType.PREMIUM,
                Boolean.TRUE});
        argsList.add(new Object[]{watchlistScreeningSetting2, sourceIdsSet1, excludedSourceIdsSet1,
                sourceTypeIdsSet1, 51.0, DataSet.WORLDCHECK, DataType.PREMIUM,
                Boolean.TRUE});
        argsList.add(new Object[]{watchlistScreeningSetting3, sourceIdsSet2, excludedSourceIdsSet2,
                sourceTypeIdsSet2, 60.0, DataSet.WORLDCHECK, DataType.PREMIUM,
                Boolean.TRUE});
        argsList.add(new Object[]{watchlistScreeningSetting4, sourceIdsSet2, excludedSourceIdsSet2,
                sourceTypeIdsSet2, 61.0, DataSet.WORLDCHECK, DataType.PREMIUM,
                Boolean.TRUE});

        return argsList;
    }


    /**
     * Helper method prepare Watchlist settings
     *
     * @param sourceIds         source ids
     * @param excludedSourceIds excludedSourceIds
     * @param sourceTypeIds     sourceTypeIds
     * @param score             score
     * @param dataSet           DATA-SET GRC/WORLD-CHECK
     * @param dataType          DATA-TYPE(STANDARD, PREMIUM, PREMIUM_PLUS)
     * @param lowAkaEnabled     ENABLE low aka
     * @return WatchlistScreeningSetting
     */
    private WatchlistScreeningSetting getWatchlistScreeningSetting(Set<String> sourceIds,
                                                                   Set<String> excludedSourceIds,
                                                                   Set<String> sourceTypeIds,
                                                                   Double score, DataSet dataSet,
                                                                   DataType dataType,
                                                                   Boolean lowAkaEnabled) {
        /**
         * Init watchlist screening settings
         */
        WatchlistScreeningSetting watchlistScreeningSetting =
                new WatchlistScreeningSetting.Builder().withGeneratedId().build();
        watchlistScreeningSetting.setScoreThreshold(score);
        watchlistScreeningSetting.setDataSet(dataSet);
        watchlistScreeningSetting.setDataType(dataType);
        watchlistScreeningSetting.setLowQualityAkaEnabled(lowAkaEnabled);
        watchlistScreeningSetting.getExcludedSourceIds().addAll(excludedSourceIds);
        watchlistScreeningSetting.getSourceIds().addAll(sourceIds);
        watchlistScreeningSetting.getSourceTypeIds().addAll(sourceTypeIds);
        return watchlistScreeningSetting;
    }
}
