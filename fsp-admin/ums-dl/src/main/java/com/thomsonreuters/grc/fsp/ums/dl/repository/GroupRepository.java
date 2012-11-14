/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link Group}s
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface GroupRepository extends RevisionRepository<Group, String>, GroupRepositoryCustom {

    /**
     * Method retrieves all the children of the given parent group
     *
     * @param parentId - parent group identifier
     * @return - {@link List} of {@link Group}
     */
    List<Group> findByParentId(String parentId);

    /**
     * Finds a {@code Group} by its {@code name}
     *
     * @param name The {@link String} name to find by
     * @return {@link Group} The matching {@code Group}, else {@code null}
     */
    Group findByName(String name);

    /**
     * Finds a list of {@link Group} by its {@link GroupType}
     *
     * @param groupType The {@link GroupType} to find by
     * @return List of {@link Group}
     */
    List<Group> findByType(GroupType groupType);

    /**
     * Finds a {@code Group} by its Name and Type
     *
     * @param name      Client Name
     * @param groupType Group Type
     * @return Group
     */
    @Transactional(readOnly = true)
    Group findByNameAndType(String name, GroupType groupType);
}
