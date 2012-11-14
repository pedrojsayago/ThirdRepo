package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class AbstractScreeningSettingBuilderTest {


    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractScreeningSettingBuilderTest.class);

    @Test
    public void canCreateAbstractScreeningSettingBuilder() {

        AbstractScreeningSetting abstractScreeningSetting = new EidVerificationSetting.Builder()
                .withHash(2)
                .withScoreThreshold(1.0)
                .build();

        LOGGER.debug("this is the Abstract Screening Setting {}", abstractScreeningSetting);
    }
}
