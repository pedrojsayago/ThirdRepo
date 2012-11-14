/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;

import com.thomsonreuters.grc.fsp.ums.domain.Role;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link Role}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface RoleRepository extends RevisionRepository<Role, String> {

    /**
     * Finds a {@code Role} by its {@code Role name} property
     *
     * @param name Owned Group Id
     * @return List of {@link Role} The matching {@code Role}, else {@code null}
     */
    List<Role> findByName(String name);
}
