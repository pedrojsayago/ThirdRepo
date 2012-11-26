/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;

import java.util.List;
import java.util.Set;

/**
 * Custom Repository for {@link GroupPreference}s
 */
public interface GroupPreferenceRepositoryCustom {

    /**
     * Finds a collection containing {@code GroupPreference}s by their {@code Group} and {@code GroupPreferenceType}
     * </p>
     * The {@code GroupPreference}s returned are inherited from {@code Group}s higher in the hierarchy
     * if not defined on the specified {@code Group}
     *
     * @param groupId              The {@link String} group ID to find by
     * @param groupPreferenceTypes The {@link Set} containing {@link GroupPreferenceType}s to find by
     * @return {@link List} The collection containing matching {@code GroupPreference}s, if any exist
     */
    List<GroupPreference> findInheritedByGroupIdAndTypeIn(String groupId,
                                                          Set<GroupPreferenceType> groupPreferenceTypes);
}
