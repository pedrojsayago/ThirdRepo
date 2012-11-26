/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.UserGroupPreference;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link UserGroupPreference}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface UserGroupPreferenceRepository extends RevisionRepository<UserGroupPreference, String> {

    /**
     * Finds a collection containing {@code UserGroupPreference}s by their {@code Group} and {@code User}
     *
     * @param userId  The {@link String} user ID to find by
     * @param groupId The {@link String} group ID to find by
     * @return {@link List} The collection containing matching {@code UserGroupPreference}s, if any exist
     */
    List<UserGroupPreference> findByUserIdAndGroupId(String userId, String groupId);

    /**
     * Finds a collection containing {@code GroupPreference}s by their {@code Group} and {@code UserGroupPreferenceType}
     *
     * @param userId                   The {@link String} user ID to find by
     * @param groupId                  The {@link String} group ID to find by
     * @param userGroupPreferenceTypes The {@link Set} containing {@link UserGroupPreferenceType}s to find by
     * @return {@link List} The collection containing matching {@code UserGroupPreference}s, if any exist
     */
    List<UserGroupPreference> findByUserIdAndGroupIdAndTypeIn(String userId, String groupId,
                                                              Set<UserGroupPreferenceType> userGroupPreferenceTypes);
}
