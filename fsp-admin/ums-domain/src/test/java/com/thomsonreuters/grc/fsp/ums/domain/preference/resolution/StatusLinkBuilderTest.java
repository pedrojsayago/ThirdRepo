package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class StatusLinkBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusLinkBuilderTest.class);

    @Test
    public void canCreateStatusLinkBuilder() {

        StatusLabel statusLabel = new StatusLabel.Builder().withPositive(true).build();
        RiskLink riskLink = new RiskLink.Builder().build();

        StatusLink statusLink = new StatusLink.Builder()
                .withStatusLabel(statusLabel)
                .withRiskRequired(true)
                .withRiskLinks(Lists.newArrayList(riskLink))
                .build();

        LOGGER.debug("this is the Status Link {}", statusLink);
    }

}
