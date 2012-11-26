/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.service.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.client.core.PreferenceService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.GroupPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.ResolutionLinkRepository;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.RiskLink;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.StatusLabel;
import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.StatusLink;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * TODO Document me!
 */
public class PreferenceServiceResolutionLinksTest extends BaseServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceServiceResolutionLinksTest.class);

    private PreferenceService preferenceService;

    private GroupPreferenceRepository mockGroupPreferenceRepository;

    private ResolutionLinkRepository mockResolutionLinkRepository;

    private Set<GroupPreferenceType> resolutionLinkTypeEnumSet = EnumSet.of(GroupPreferenceType.RESOLUTION_LINKS);

    @Before
    public void initialise() {
        preferenceService = new PreferenceServiceImpl();

        GroupService groupService = mock(GroupService.class);
        mockGroupPreferenceRepository = mock(GroupPreferenceRepository.class);
        mockResolutionLinkRepository = mock(ResolutionLinkRepository.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", mockGroupPreferenceRepository);
        setField(preferenceService, "resolutionLinkRepository", mockResolutionLinkRepository);
    }

    @Test
    public void canLoadResolutionLinks() {
        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(FOO, resolutionLinkTypeEnumSet))
                .thenReturn(Lists.newArrayList(getResolutionLinkPreference()));

        when(mockResolutionLinkRepository.findOne("UUID")).thenReturn(getResolutionLink());
        List<StatusLink> statusLinks = preferenceService.getResolutionLinksByGroup(FOO);

        verify(mockResolutionLinkRepository).findOne("UUID");
        Assert.notEmpty(statusLinks);
        assertThat(statusLinks.size(), equalTo(1));
    }

    @Test
    public void testNonExistentResolutionLink() {
        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(FOO, resolutionLinkTypeEnumSet))
                .thenReturn(Lists.newArrayList(getResolutionLinkPreference()));

        when(mockResolutionLinkRepository.findOne("UUID")).thenReturn(null);

        expectedException.expect(IllegalStateException.class);
        preferenceService.getResolutionLinksByGroup(FOO);
    }

    @Test
    public void testInvalidResolutionLinkType() {
        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(FOO, resolutionLinkTypeEnumSet))
                .thenReturn(Lists.newArrayList(getResolutionLinkPreference()));

        when(mockResolutionLinkRepository.findOne("UUID")).thenReturn(new RiskLink.Builder().build());

        expectedException.expect(IllegalStateException.class);
        preferenceService.getResolutionLinksByGroup(FOO);
    }

    @Test
    public void testResolutionLinkOrdering() {

        // The links should be returned in the order of the indexes set up when creating the
        // preferences
        final String UUID_1 = "UUID_1";
        final String UUID_2 = "UUID_2";
        Map<String, String> entries = new HashMap<>();
        entries.put("0", UUID_1);
        entries.put("1", UUID_2);

        GroupPreference preference = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.RESOLUTION_LINKS)
                .withEntries(entries)
                .build();

        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(FOO, resolutionLinkTypeEnumSet))
                .thenReturn(Lists.newArrayList(preference));

        StatusLabel statusLabel = new StatusLabel.Builder()
                .withLabel("Unspecified")
                .build();

        StatusLink statusLink = new StatusLink.Builder().withStatusLabel(statusLabel)
                .withId(UUID_1).build();

        when(mockResolutionLinkRepository.findOne(UUID_1)).thenReturn(statusLink);

        statusLabel = new StatusLabel.Builder()
                .withLabel("Positive")
                .build();

        statusLink = new StatusLink.Builder().withStatusLabel(statusLabel)
                .withId(UUID_2).build();

        when(mockResolutionLinkRepository.findOne(UUID_2)).thenReturn(statusLink);

        List<StatusLink> statusLinks = preferenceService.getResolutionLinksByGroup(FOO);

        Assert.notEmpty(statusLinks);
        assertTrue(statusLinks.size() == 2);
        assertEquals(UUID_1, statusLinks.get(0).getId());
        assertEquals(UUID_2, statusLinks.get(1).getId());
    }

    private StatusLink getResolutionLink() {
        StatusLabel statusLabel = new StatusLabel.Builder()
                .withLabel("status")
                .build();
        StatusLink statusLink = new StatusLink.Builder().withStatusLabel(statusLabel)
                .build();
        return statusLink;
    }

    private GroupPreference getResolutionLinkPreference() {
        Map<String, String> entries = ImmutableMap.of("0", "UUID");
        GroupPreference preference = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.RESOLUTION_LINKS)
                .withEntries(entries)
                .build();
        return preference;
    }
}
