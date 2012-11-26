/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link GroupPreference}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface GroupPreferenceRepository extends GroupPreferenceRepositoryCustom,
        RevisionRepository<GroupPreference, String> {

    /**
     * Finds a collection containing {@code GroupPreference}s by their {@code Group}
     *
     * @param groupId The {@link String} group ID to find by
     * @return {@link List} The collection containing matching {@code GroupPreference}s, if any exist
     */
    List<GroupPreference> findByGroupId(String groupId);

    /**
     * Finds a collection containing {@code GroupPreference}s by their {@code Group} and {@code GroupPreferenceType}
     *
     * @param groupId              The {@link String} group ID to find by
     * @param groupPreferenceTypes The {@link Set} containing {@link GroupPreferenceType}s to find by
     * @return {@link List} The collection containing matching {@code GroupPreference}s, if any exist
     */
    List<GroupPreference> findByGroupIdAndTypeIn(String groupId, Set<GroupPreferenceType> groupPreferenceTypes);
}
