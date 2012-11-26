package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import com.thomsonreuters.grc.fsp.ums.domain.Group;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
public class ReasonLabelBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReasonLabelBuilderTest.class);

    @Test
    public void canCreateReasonLabelBuilder() {

        Group aGroup = new Group.Builder().withName("Client").withPath("/1/2/").withDepth(2).build();
        ReasonLabel reasonLabel = (ReasonLabel) new ReasonLabel.Builder()
                .withLabel("MyLabel")
                .withClientGroup(aGroup)
                .build();

        LOGGER.debug("this is the reason Label {}", reasonLabel);
    }

}
