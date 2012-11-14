/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.dl.BaseDataTest;
import com.thomsonreuters.grc.fsp.ums.dl.repository.GroupRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.ReasonLabel;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.ResolutionLabel;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.RiskLabel;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.StatusLabel;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;

/**
 * Test class for Resolution Label Repository
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 30 August 2012
 */
@Transactional
public class ResolutionLabelRepositoryTest extends BaseDataTest {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private ResolutionLabelRepository resolutionLabelRepository;

    private Group clientGroup;

    @Before
    public void init() {
        clientGroup = groupRepository.findOne("GROUP_1");
        assertNotNull(clientGroup);
    }

    @Test
    public void canSaveStatusLabel() {
        StatusLabel statusLabel = new StatusLabel.Builder()
                .withPositive(Boolean.TRUE)
                .withLabel("some status")
                .withClientGroup(clientGroup)
                .withGeneratedId()
                .build();
        statusLabel = resolutionLabelRepository.save(statusLabel);

        assertResolutionLabel(statusLabel);
        assertNotNull(statusLabel.getClientGroup());
    }

    @Test
    public void canSaveRemarkLabel() {
        ReasonLabel reasonLabel = new ReasonLabel.Builder()
                .withLabel("some remark")
                .withClientGroup(clientGroup)
                .withGeneratedId()
                .build();

        reasonLabel = resolutionLabelRepository.save(reasonLabel);

        assertResolutionLabel(reasonLabel);
        assertNotNull(reasonLabel.getClientGroup());
    }

    @Test
    public void canSaveRiskLabel() {
        RiskLabel riskLabel = new RiskLabel.Builder()
                .withLabel("some remark")
                .withClientGroup(clientGroup)
                .withGeneratedId()
                .build();
        riskLabel = resolutionLabelRepository.save(riskLabel);

        assertResolutionLabel(riskLabel);
        assertNotNull(riskLabel.getClientGroup());
    }

    @Test
    public void canLoadResolutionLabels() {
        assertResolutionLabel(resolutionLabelRepository.findOne("ID_1"));

        List<ResolutionLabel> resolutionLabels = resolutionLabelRepository.findByLabel("Low");
        org.springframework.util.Assert.notEmpty(resolutionLabels);
    }

    @Test
    public void canLoadResolutionLabelByType() {
        List<StatusLabel> statusLabels = resolutionLabelRepository.findByLabelAndType("Positive", StatusLabel.class);
        org.springframework.util.Assert.notEmpty(statusLabels);

        List<RiskLabel> riskLabels = resolutionLabelRepository.findByLabelAndType("Low", RiskLabel.class);
        org.springframework.util.Assert.notEmpty(riskLabels);

        List<ReasonLabel> reasonLabels = resolutionLabelRepository.findByLabelAndType("Remark 1", ReasonLabel.class);
        org.springframework.util.Assert.notEmpty(reasonLabels);
    }

    private void assertResolutionLabel(ResolutionLabel resolutionLabel) {
        assertNotNull(resolutionLabel.getId());
        assertNotNull(resolutionLabel.getLabel());
    }
}