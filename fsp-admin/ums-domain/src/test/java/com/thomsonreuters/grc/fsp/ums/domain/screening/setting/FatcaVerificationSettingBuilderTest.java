package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class FatcaVerificationSettingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FatcaVerificationSettingBuilderTest.class);

    @Test
    public void canCreateFatcaVerificationSettingBuilder() {
        FatcaVerificationSetting.Builder fatcaVerificationSettingBuilder =
                new FatcaVerificationSetting.Builder();
        FatcaVerificationSetting fatcaVerificationSetting = fatcaVerificationSettingBuilder
                .withHash(2)
                .withScoreThreshold(1.0)
                .build();

        LOGGER.debug("this is the fatca Verification Setting {}", fatcaVerificationSetting);
    }
}
