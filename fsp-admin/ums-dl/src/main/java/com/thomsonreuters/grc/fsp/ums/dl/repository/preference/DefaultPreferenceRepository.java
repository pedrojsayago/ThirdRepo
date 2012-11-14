/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.domain.preference.defaults.DefaultPreference;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link DefaultPreference}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface DefaultPreferenceRepository extends RevisionRepository<DefaultPreference, String> {
}
