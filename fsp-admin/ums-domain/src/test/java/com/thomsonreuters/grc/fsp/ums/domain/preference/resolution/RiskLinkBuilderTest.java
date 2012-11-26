package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class RiskLinkBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskLinkBuilderTest.class);

    @Test
    public void canCreateRiskLinkBuilder() {

        RiskLink.Builder riskLinkBuilder = new RiskLink.Builder();
        RiskLink riskLink = riskLinkBuilder
                .withReasonRequired(true)
                .withRiskLabel(new RiskLabel.Builder().build())
                .build();

        LOGGER.debug("this is the Risk Link {}", riskLink);
    }

}
