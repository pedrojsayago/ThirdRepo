package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.ResolutionLink;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository definition for {@link ResolutionLink}
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 18/09/12
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface ResolutionLinkRepository extends RevisionRepository<ResolutionLink, String> {
}
