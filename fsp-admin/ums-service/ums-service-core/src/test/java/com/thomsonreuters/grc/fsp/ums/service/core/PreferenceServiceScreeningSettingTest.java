package com.thomsonreuters.grc.fsp.ums.service.core;

import com.thomsonreuters.grc.fsp.common.base.type.core.ProviderType;
import com.thomsonreuters.grc.fsp.common.base.type.core.state.ScreeningState;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.dl.repository.preference.GroupPreferenceRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.mockito.Mockito.*;


/**
 * Preference Screening Setting test
 * Can Get Screening Setting Id for a Group
 * Can save screening setting ids for a Group
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 29/10/12
 */
public class PreferenceServiceScreeningSettingTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PreferenceServiceScreeningSettingTest.class);

    /**
     * Dummy Group ID
     */
    private static final String GROUP_ID = "GROUP_ID_1";

    /**
     * Dummy Watchlist Screening Setting
     */
    private static final String WATCHLIST_SCREENING_SETTING_ID = "WATCHLIST_SCREENING_SETTING_ID_1";

    /**
     * Dummy Media Screening Setting
     */
    private static final String MEDIA_SCREENING_SETTING_ID = "MEDIA_SCREENING_SETTING_ID_1";

    /**
     * Preference Service To be tested
     */
    private PreferenceServiceImpl preferenceService = new PreferenceServiceImpl();

    /**
     * Mocked Group Preference Repo
     */
    private GroupPreferenceRepository mockGroupPreferenceRepository;


    /**
     * Can Save Screening Setting Preference
     */
    @Test
    public void canSaveGroupPreference() {

        /**
         * Prepare Data
         */
        ScreeningState screeningState = ScreeningState.ONGOING;
        ProviderType providerType = ProviderType.WATCHLIST;
        GroupPreference groupPreference =
                getGroupPreference(GroupPreferenceType.SCREENING_SETTING_ONGOING);

        Set<GroupPreferenceType> groupPreferenceTypes =
                new HashSet<>(Arrays.asList(groupPreference.getType()));

        /**
         * Set expectations
         */
        when(mockGroupPreferenceRepository.save(Arrays.asList(groupPreference)))
                .thenReturn(Arrays.asList(groupPreference));

        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(GROUP_ID, groupPreferenceTypes))
                .thenReturn(Arrays.asList(groupPreference));

        /**
         * Act
         */
        GroupPreference savedGroupPreference = preferenceService
                .saveScreeningSettingPreference(GROUP_ID, screeningState, providerType,
                        WATCHLIST_SCREENING_SETTING_ID);

        /**
         * Verify
         */
        verify(mockGroupPreferenceRepository, times(1))
                .findByGroupIdAndTypeIn(GROUP_ID, groupPreferenceTypes);
        verify(mockGroupPreferenceRepository, times(1)).save(Arrays.asList(groupPreference));

        Assert.assertNotNull("saved Group Pref must not be null", savedGroupPreference);
    }

    /**
     * Can Get Screening Setting ids
     */
    @Test
    public void canGetScreeningSettingIds() {

        /**
         * Prepare Data
         */
        ScreeningState screeningState = ScreeningState.INITIAL;

        GroupPreference groupPreference =
                getGroupPreference(GroupPreferenceType.SCREENING_SETTING_INITIAL);
        groupPreference.getEntries()
                .put(ProviderType.WATCHLIST.name(), WATCHLIST_SCREENING_SETTING_ID);
        groupPreference.getEntries().put(ProviderType.MEDIA.name(), MEDIA_SCREENING_SETTING_ID);
        Set<GroupPreferenceType> groupPreferenceTypes =
                new HashSet<>(Arrays.asList(groupPreference.getType()));

        /**
         * Set expectations
         */
        when(mockGroupPreferenceRepository.findByGroupIdAndTypeIn(GROUP_ID, groupPreferenceTypes))
                .thenReturn(Arrays.asList(groupPreference));

        /**
         * Act
         */
        Map<ProviderType, String> initialScreeningSettingIds =
                preferenceService.getScreeningSettingIds(GROUP_ID, screeningState);

        /**
         * Verify
         */
        verify(mockGroupPreferenceRepository, times(1))
                .findByGroupIdAndTypeIn(GROUP_ID, groupPreferenceTypes);

        Assert.assertNotNull("Group Pref must not be null", initialScreeningSettingIds);

        Assert.assertEquals("Watchlist Screening Settings must be Present",
                initialScreeningSettingIds.get(ProviderType.WATCHLIST),
                WATCHLIST_SCREENING_SETTING_ID);
        Assert.assertEquals("Media Screening Settings must be Present",
                initialScreeningSettingIds.get(ProviderType.MEDIA),
                MEDIA_SCREENING_SETTING_ID);
    }

    /**
     * Get Dummy Group Preference
     *
     * @return GroupPreference
     */
    GroupPreference getGroupPreference(GroupPreferenceType groupPreferenceType) {

        Group group = new Group.Builder()
                .withId(GROUP_ID)
                .build();
        GroupPreference groupPreference = new GroupPreference.Builder()
                .withGroup(group)
                .withType(groupPreferenceType)
                .build();

        groupPreference.getEntries()
                .put(ProviderType.WATCHLIST.name(), WATCHLIST_SCREENING_SETTING_ID);
        return groupPreference;
    }


    /**
     * Initialise Test Data
     */
    @Before
    public void init() {

        mockGroupPreferenceRepository = Mockito.mock(GroupPreferenceRepository.class);
        preferenceService.setGroupPreferenceRepository(mockGroupPreferenceRepository);
    }
}