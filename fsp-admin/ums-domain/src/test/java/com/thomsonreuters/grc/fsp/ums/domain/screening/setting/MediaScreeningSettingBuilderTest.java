package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class MediaScreeningSettingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MediaScreeningSettingBuilderTest.class);

    @Test
    public void canCreateMediaScreeningSettingBuilder() {

        MediaScreeningSetting mediaScreeningSetting = new MediaScreeningSetting.Builder()
                .withHash(1)
                .withScoreThreshold(1.0)
                .build();
        LOGGER.debug("this is the fatca Media Screening Setting {}", mediaScreeningSetting);
    }
}
