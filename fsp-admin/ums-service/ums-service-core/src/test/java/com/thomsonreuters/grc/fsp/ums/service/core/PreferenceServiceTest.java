package com.thomsonreuters.grc.fsp.ums.service.core;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.common.base.type.ums.PreferenceType;
import com.thomsonreuters.grc.fsp.ums.client.core.GroupService;
import com.thomsonreuters.grc.fsp.ums.client.core.PreferenceService;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.CustomFieldTypeRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.DefaultPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.GroupPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.domain.preference.CustomFieldType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.defaults.DefaultGroupPreference;
import com.thomsonreuters.grc.fsp.ums.domain.preference.defaults.DefaultPreference;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Tests for {@link PreferenceService}
 */
public class PreferenceServiceTest extends BaseServiceTest {

    private PreferenceService preferenceService = new PreferenceServiceImpl();

    /**
     * Test that the default preference entries are loaded at application startup
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testInitialiseDefaultPreferences() {
        PreferenceServiceImpl preferenceService = new PreferenceServiceImpl();

        List<DefaultPreference> defaultPreferences = new ArrayList<>();

        for (GroupPreferenceType groupPreferenceType : GroupPreferenceType.values()) {
            DefaultPreference defaultPreference = mock(DefaultGroupPreference.class);

            when(defaultPreference.getEntries()).thenReturn(ImmutableMap.of(FOO, FOO));
            when(defaultPreference.getType()).thenReturn(groupPreferenceType);

            defaultPreferences.add(defaultPreference);
        }

        DefaultPreferenceRepository defaultPreferenceRepository = mock(DefaultPreferenceRepository.class);

        when(defaultPreferenceRepository.findAll()).thenReturn(defaultPreferences);

        Map defaultPreferenceEntries = spy(new HashMap());

        setField(preferenceService, "defaultPreferenceEntries", defaultPreferenceEntries);
        setField(preferenceService, "defaultPreferenceRepository", defaultPreferenceRepository);

        preferenceService.afterPropertiesSet();

        verify(defaultPreferenceEntries, times(GroupPreferenceType.values().length)).put(anyObject(), anyObject());
    }

    /**
     * Test that given a missing default preference, an exception is thrown
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testInitialiseDefaultPreferencesWithNullEntries() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Could not retrieve default entries");

        PreferenceServiceImpl preferenceService = new PreferenceServiceImpl();

        DefaultPreference defaultPreference = mock(DefaultPreference.class);

        List<DefaultPreference> defaultPreferences = Lists.newArrayList(defaultPreference);

        DefaultPreferenceRepository defaultPreferenceRepository = mock(DefaultPreferenceRepository.class);

        when(defaultPreferenceRepository.findAll()).thenReturn(defaultPreferences);

        setField(preferenceService, "defaultPreferenceRepository", defaultPreferenceRepository);

        preferenceService.afterPropertiesSet();
    }

    /**
     * Test that given a default preference with no entries, an exception is thrown
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testInitialiseDefaultPreferencesWithEmptyEntries() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Could not retrieve default entries");

        PreferenceServiceImpl preferenceService = new PreferenceServiceImpl();

        DefaultPreference defaultPreference = mock(DefaultPreference.class);

        when(defaultPreference.getEntries()).thenReturn(new HashMap<String, String>());

        List<DefaultPreference> defaultPreferences = Lists.newArrayList(defaultPreference);

        DefaultPreferenceRepository defaultPreferenceRepository = mock(DefaultPreferenceRepository.class);

        when(defaultPreferenceRepository.findAll()).thenReturn(defaultPreferences);

        setField(preferenceService, "defaultPreferenceRepository", defaultPreferenceRepository);

        preferenceService.afterPropertiesSet();
    }

    /**
     * Test that a custom field type can be loaded for an ID
     */
    @Test
    public void testGetCustomFieldType() {
        CustomFieldType customFieldType = new CustomFieldType.Builder().build();

        CustomFieldTypeRepository customFieldTypeRepository = mock(CustomFieldTypeRepository.class);
        when(customFieldTypeRepository.findOne(FOO)).thenReturn(customFieldType);

        setField(preferenceService, "customFieldTypeRepository", customFieldTypeRepository);

        customFieldType = preferenceService.getCustomFieldType(FOO);

        assertNotNull(customFieldType);
    }

    /**
     * Test that given a custom field type ID with no matching custom field type, an exception is thrown
     */
    @Test
    public void testGetCustomFieldTypeWithNoMatchingTypeForId() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Custom Field Type " + FOO + " does not exist");

        CustomFieldTypeRepository customFieldTypeRepository = mock(CustomFieldTypeRepository.class);

        setField(preferenceService, "customFieldTypeRepository", customFieldTypeRepository);

        CustomFieldType customFieldType = preferenceService.getCustomFieldType(FOO);

        assertNotNull(customFieldType);
    }

    /**
     * Test that custom field types can be loaded for a group
     */
    @Test
    public void testGetCustomFieldTypesByGroup() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());
        GroupPreference groupPreference = new GroupPreference.Builder()
                .withEntries(ImmutableMap.of("0", FOO))
                .build();

        List<GroupPreference> groupPreferences = Lists.newArrayList(groupPreference);

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupIdAndTypeIn(FOO, EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES)))
                .thenReturn(groupPreferences);

        CustomFieldType customFieldType = (CustomFieldType) new CustomFieldType.Builder().withLabel(FOO).build();

        CustomFieldTypeRepository customFieldTypeRepository = mock(CustomFieldTypeRepository.class);

        when(customFieldTypeRepository.findOne(FOO)).thenReturn(customFieldType);

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);
        setField(preferenceService, "customFieldTypeRepository", customFieldTypeRepository);

        List<CustomFieldType> customFieldTypes = preferenceService.getCustomFieldTypesByGroup(FOO);

        assertNotNull(customFieldTypes);
        assertThat(1, equalTo(customFieldTypes.size()));

        customFieldType = customFieldTypes.get(0);

        assertNotNull(customFieldType);
        assertThat(FOO, equalTo(customFieldType.getLabel()));
    }

    /**
     * Test that given a custom field type ID with no matching custom field type, an exception is thrown
     */
    @Test
    public void testGetCustomFieldTypesByGroupWithNoMatchingTypesForId() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("Custom Field Type " + FOO + " does not exist");

        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        GroupPreference groupPreference = new GroupPreference.Builder()
                .withEntries(ImmutableMap.of("0", FOO)).build();

        List<GroupPreference> groupPreferences = Lists.newArrayList(groupPreference);

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupIdAndTypeIn(FOO, EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES)))
                .thenReturn(groupPreferences);

        CustomFieldTypeRepository customFieldTypeRepository = mock(CustomFieldTypeRepository.class);

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);
        setField(preferenceService, "customFieldTypeRepository", customFieldTypeRepository);

        preferenceService.getCustomFieldTypesByGroup(FOO);
    }

    /**
     * Test that a group preference can be loaded for a group
     */
    @Test
    public void testGetGroupPreference() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());
        GroupPreference groupPreference = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.CUSTOM_FIELD_TYPES)
                .withEntries(ImmutableMap.of("0", FOO))
                .build();

        List<GroupPreference> groupPreferences = Lists.newArrayList(groupPreference);

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupIdAndTypeIn(FOO, EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES)))
                .thenReturn(groupPreferences);

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);

        groupPreference = preferenceService.getGroupPreference(FOO, GroupPreferenceType.CUSTOM_FIELD_TYPES);

        assertNotNull(groupPreference);
        assertNotNull(groupPreference.getGroup());
        assertSame(GroupPreferenceType.CUSTOM_FIELD_TYPES, groupPreference.getType());
    }

    /**
     * Test that a group preference with default entries can be loaded for a group
     */
    @Test
    public void testGetGroupPreferenceWithDefaultEntries() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        Map<PreferenceType, Map<String, String>> defaultPreferenceEntries = new HashMap<>();
        defaultPreferenceEntries.put(GroupPreferenceType.CUSTOM_FIELD_TYPES, ImmutableMap.of("0", FOO));

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);
        setField(preferenceService, "defaultPreferenceEntries", defaultPreferenceEntries);

        GroupPreference groupPreference = preferenceService.getGroupPreference(FOO, GroupPreferenceType.CUSTOM_FIELD_TYPES);

        assertNotNull(groupPreference);
        assertNull(groupPreference.getGroup());
        assertSame(GroupPreferenceType.CUSTOM_FIELD_TYPES, groupPreference.getType());
    }

    /**
     * Test that all group preferences can be loaded for a group
     */
    @Test
    public void testGetGroupPreferencesForAllTypes() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        List<GroupPreference> groupPreferences = new ArrayList<>();

        for (GroupPreferenceType groupPreferenceType : GroupPreferenceType.values()) {

            GroupPreference groupPreference = new GroupPreference.Builder()
                    .withGroup(getGroupFoo())
                    .withType(groupPreferenceType)
                    .withEntries(ImmutableMap.of(FOO, FOO))
                    .build();

            groupPreferences.add(groupPreference);
        }

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupId(FOO)).thenReturn(groupPreferences);

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);

        groupPreferences = preferenceService.getGroupPreferences(FOO);

        assertNotNull(groupPreferences);
        assertSame(GroupPreferenceType.values().length, groupPreferences.size());

        for (GroupPreference groupPreference : groupPreferences) {
            assertNotNull(groupPreference);
            assertNotNull(groupPreference.getGroup());
        }
    }

    /**
     * Test that all group preferences can be loaded for a group, some with default entries
     */
    @Test
    public void testGetGroupPreferencesForAllTypesWithDefaultEntries() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        List<GroupPreference> groupPreferences = new ArrayList<>();

        for (GroupPreferenceType groupPreferenceType : GroupPreferenceType.values()) {

            if (GroupPreferenceType.CUSTOM_FIELD_TYPES != groupPreferenceType) {

                GroupPreference groupPreference = new GroupPreference.Builder()
                        .withGroup(getGroupFoo())
                        .withType(groupPreferenceType)
                        .withEntries(ImmutableMap.of(FOO, FOO))
                        .build();

                groupPreferences.add(groupPreference);
            }
        }

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupId(FOO)).thenReturn(groupPreferences);

        Map<PreferenceType, Map<String, String>> defaultPreferenceEntries = new HashMap<>();
        defaultPreferenceEntries.put(GroupPreferenceType.CUSTOM_FIELD_TYPES, ImmutableMap.of(FOO, FOO));

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);
        setField(preferenceService, "defaultPreferenceEntries", defaultPreferenceEntries);

        groupPreferences = preferenceService.getGroupPreferences(FOO);

        assertNotNull(groupPreferences);
        assertSame(GroupPreferenceType.values().length, groupPreferences.size());

        for (GroupPreference groupPreference : groupPreferences) {
            assertNotNull(groupPreference);

            if (GroupPreferenceType.CUSTOM_FIELD_TYPES != groupPreference.getType()) {
                assertNotNull(groupPreference.getGroup());
            } else {
                assertNull(groupPreference.getGroup());
            }
        }
    }

    /**
     * Test that group preferences can be loaded for a group
     */
    @Test
    public void testGetGroupPreferences() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        List<GroupPreference> groupPreferences = new ArrayList<>();

        GroupPreference groupPreferenceOne = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.CUSTOM_FIELD_TYPES)
                .withEntries(ImmutableMap.of(FOO, FOO))
                .build();

        groupPreferences.add(groupPreferenceOne);

        GroupPreference groupPreferenceTwo = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.SCREENABLE_ENTITY_TYPES)
                .withEntries(ImmutableMap.of(FOO, FOO))
                .build();

        groupPreferences.add(groupPreferenceTwo);

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupIdAndTypeIn(FOO,
                EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES, GroupPreferenceType.SCREENABLE_ENTITY_TYPES)))
                .thenReturn(groupPreferences);

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);

        groupPreferences = preferenceService.getGroupPreferences(FOO,
                EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES, GroupPreferenceType.SCREENABLE_ENTITY_TYPES));

        assertNotNull(groupPreferences);
        assertSame(2, groupPreferences.size());

        for (GroupPreference groupPreference : groupPreferences) {
            assertNotNull(groupPreference);
            assertNotNull(groupPreference.getGroup());
        }
    }

    /**
     * Test that group preferences can be loaded for a group, some with default entries
     */
    @Test
    public void testGetGroupPreferencesWithDefaultEntries() {
        GroupService groupService = mock(GroupService.class);

        when(groupService.getGroup(FOO)).thenReturn(getGroupFoo());

        GroupPreference groupPreferenceOne = new GroupPreference.Builder()
                .withGroup(getGroupFoo())
                .withType(GroupPreferenceType.CUSTOM_FIELD_TYPES)
                .withEntries(ImmutableMap.of(FOO, FOO))
                .build();

        List<GroupPreference> groupPreferences = Lists.newArrayList(groupPreferenceOne);

        GroupPreferenceRepository groupPreferenceRepository = mock(GroupPreferenceRepository.class);

        when(groupPreferenceRepository.findByGroupIdAndTypeIn(FOO,
                EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES, GroupPreferenceType.SCREENABLE_ENTITY_TYPES)))
                .thenReturn(groupPreferences);

        Map<PreferenceType, Map<String, String>> defaultPreferenceEntries = new HashMap<>();
        defaultPreferenceEntries.put(GroupPreferenceType.SCREENABLE_ENTITY_TYPES, ImmutableMap.of(FOO, FOO));

        setField(preferenceService, "groupService", groupService);
        setField(preferenceService, "groupPreferenceRepository", groupPreferenceRepository);
        setField(preferenceService, "defaultPreferenceEntries", defaultPreferenceEntries);

        groupPreferences = preferenceService.getGroupPreferences(FOO,
                EnumSet.of(GroupPreferenceType.CUSTOM_FIELD_TYPES, GroupPreferenceType.SCREENABLE_ENTITY_TYPES));

        assertNotNull(groupPreferences);
        assertSame(2, groupPreferences.size());

        for (GroupPreference groupPreference : groupPreferences) {
            assertNotNull(groupPreference);

            if (GroupPreferenceType.SCREENABLE_ENTITY_TYPES != groupPreference.getType()) {
                assertNotNull(groupPreference.getGroup());
            } else {
                assertNull(groupPreference.getGroup());
            }
        }
    }
}
