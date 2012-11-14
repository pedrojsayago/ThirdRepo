package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.google.common.collect.Lists;
import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNotNull;
import static org.springframework.util.Assert.notEmpty;

/**
 * Test class for {@link ResolutionLinkRepository}
 * <p/>
 * Note: Test case used the data from init-data.sql
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 18/09/12
 */
@Transactional
public class ResolutionLinkRepositoryTest extends BaseDataTest {

    @Autowired
    private ResolutionLinkRepository resolutionLinkRepository;

    @Autowired
    private ResolutionLabelRepository resolutionLabelRepository;

    private StatusLabel status;
    private RiskLabel risk;
    private ReasonLabel reason;

    private StatusLink statusLink;

    @Test
    public void canCreateResolutionLinks() {
        statusLink = resolutionLinkRepository.save(buildStatusLink());
        assertNotNull(statusLink.getId());
        notEmpty(statusLink.getRiskLinks());
        notEmpty(statusLink.getReasonLabels());
    }

    @Test
    public void canLoadResolutionLinks() {
        statusLink = (StatusLink) resolutionLinkRepository.findOne("b3e01813-8509-46e8-9d84-edefa6eaca3b");
        assertNotNull(statusLink);
        notEmpty(statusLink.getReasonLabels());
        notEmpty(statusLink.getRiskLinks());
    }

    @Test
    public void canDeleteResolutionLink() {
        statusLink = resolutionLinkRepository.save(buildStatusLink());
        resolutionLinkRepository.delete(statusLink.getId());
    }

    private StatusLink buildStatusLink() {
        status = (StatusLabel) resolutionLabelRepository.findOne("ID_7");
        risk = (RiskLabel) resolutionLabelRepository.findOne("ID_4");
        reason = (ReasonLabel) resolutionLabelRepository.findOne("ID_10");

        StatusLink statusLink = new StatusLink.Builder()
                .withReasonRequired(true)
                .withRemarkRequired(true)
                .withStatusLabel(status)
                .withReasonLabels(Lists.newArrayList(reason))
                .withGeneratedId()
                .build();

        RiskLink riskLink = new RiskLink.Builder()
                .withReasonRequired(false)
                .withGeneratedId()
                .build();
        risk = (RiskLabel) resolutionLabelRepository.findOne("ID_1");
        riskLink.setRiskLabel(risk);
        riskLink.setReasonLabels(resolutionLabelRepository.findByLabelAndType("Remark 1", ReasonLabel.class));
        statusLink.setRiskLinks(Lists.newArrayList(riskLink));

        return statusLink;
    }
}
