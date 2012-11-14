/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;


import com.thomsonreuters.grc.fsp.ums.domain.User;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Repository for {@link User}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface UserRepository extends RevisionRepository<User, String> {

    /**
     * Finds a {@code User} by its {@code username} property
     *
     * @param username The {@link String} username to find by
     * @return {@link User} The matching {@code User}, else {@code null}
     */
    User findByUsername(String username);
}
