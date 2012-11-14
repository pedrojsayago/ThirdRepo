package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class EidVerificationSettingBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(EidVerificationSettingBuilderTest.class);

    @Test
    public void canCreateEidVerificationSettingBuilder() {

        EidVerificationSetting eidVerificationSetting = new EidVerificationSetting.Builder()
                .withHash(2)
                .withScoreThreshold(1.0)
                .build();

        LOGGER.debug("this is the eidVerification Setting {}", eidVerificationSetting);
    }
}
