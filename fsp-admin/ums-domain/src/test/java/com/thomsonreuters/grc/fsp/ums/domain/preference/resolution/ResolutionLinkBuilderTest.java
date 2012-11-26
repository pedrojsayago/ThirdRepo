package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;


import com.google.common.collect.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class ResolutionLinkBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ResolutionLinkBuilderTest.class);

    @Test
    public void canCreateResolutionLinkBuilder() {

        ReasonLabel reasonLabel = new ReasonLabel.Builder()
                .withLabel("newLabel")
                .withId("1")
                .build();

        ResolutionLink resolutionLink = new RiskLink.Builder()
                .withReasonRequired(true)
                .withRemarkRequired(false)
                .withReasonLabels(Lists.newArrayList(reasonLabel))
                .build();

        LOGGER.debug("this is the resolution link {}", resolutionLink);
    }
}
