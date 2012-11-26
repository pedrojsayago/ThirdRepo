package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import com.thomsonreuters.grc.fsp.ums.domain.Group;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created with IntelliJ IDEA.
 * User: pedro
 */
@Transactional
public class RiskLabelBuilderTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RiskLabelBuilderTest.class);

    @Test
    public void canCreateRiskLabelBuilder() {

        Group aGroup = new Group.Builder().withName("Client").withPath("/1/2/").withDepth(2).build();

        RiskLabel riskLabel = new RiskLabel.Builder()
                .withLabel("myLabel")
                .withClientGroup(aGroup)
                .build();

        LOGGER.debug("this is the Risk Label {}", riskLabel);
    }

}
