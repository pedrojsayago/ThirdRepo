package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class StatusLabelBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusLabelBuilderTest.class);

    @Test
    public void canCreateStatusLabelBuilder() {

        StatusLabel statusLabel = new StatusLabel.Builder()
                .withPositive(true)
                .withLabel("newLabel")
                .build();

        LOGGER.debug("this is the Status Label {}", statusLabel);
    }
}
