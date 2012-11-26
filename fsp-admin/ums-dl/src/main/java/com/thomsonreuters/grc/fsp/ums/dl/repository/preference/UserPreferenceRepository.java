/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.UserPreference;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Repository for {@link UserPreference}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface UserPreferenceRepository extends RevisionRepository<UserPreference, String> {

    /**
     * Finds a collection containing {@code UserPreference}s by their {@code User}
     *
     * @param userId The {@link String} user ID to find by
     * @return {@link List} The collection containing matching {@code UserPreference}s, if any exist
     */
    List<UserPreference> findByUserId(String userId);

    /**
     * Finds a collection containing {@code UserPreference}s by their {@code User} and {@code UserPreferenceType}
     *
     * @param userId              The {@link String} user ID to find by
     * @param userPreferenceTypes The {@link Set} containing {@link UserPreferenceType}s to find by
     * @return {@link List} The collection containing matching {@code UserPreference}s, if any exist
     */
    List<UserPreference> findByUserIdAndTypeIn(String userId, Set<UserPreferenceType> userPreferenceTypes);
}
