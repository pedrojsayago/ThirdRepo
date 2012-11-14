package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class WatchListScreeningSettingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(WatchListScreeningSettingBuilderTest.class);

    @Test
    public void canCreateWatchListScreeningSettingBuilder() {

        WatchlistScreeningSetting watchlistScreeningSetting = new WatchlistScreeningSetting.Builder()
                .withHash(1)
                .withScoreThreshold(1.0)
                .build();
        LOGGER.debug("this is the fatca watchList Screening Setting {}", watchlistScreeningSetting);
    }
}
