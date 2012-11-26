/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.dl.repository.GroupRepository;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Custom Repository implementation for {@link GroupPreference}s
 */
public class GroupPreferenceRepositoryImpl implements GroupPreferenceRepositoryCustom {

    /**
     * {@link Splitter} used to retrieve {@link String} {@code Group} IDs from a path
     */
    private final Splitter pathSplitter = Splitter.on("/").omitEmptyStrings();

    /**
     * Query {@link String} used to retrieve inherited {@link GroupPreference}s
     */
    private final String queryString = initialiseQuery();

    /**
     * Shared Entity Manager
     */
    private EntityManager entityManager;

    /**
     * Group Repo
     */
    private GroupRepository groupRepository;

    /**
     * Returns the query used to retrieve inherited {@link GroupPreference}s
     *
     * @return {@link String} The query used to retrieve inherited {@link GroupPreference}s
     */
    private String initialiseQuery() {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append("(SELECT gp.* FROM p_group_preference gp ");
        sb.append("INNER JOIN p_group g ON (gp.group_id = g.id) ");
        sb.append("WHERE g.id IN (:groupIds) ");
        sb.append("AND gp.type IN (:types) ");
        sb.append("ORDER BY g.depth DESC) AS x ");
        sb.append("GROUP BY x.type");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GroupPreference> findInheritedByGroupIdAndTypeIn(String groupId,
                                                                 Set<GroupPreferenceType> groupPreferenceTypes) {

        Group group = groupRepository.findOne(groupId);

        if (group == null) {
            return new ArrayList<>();
        }
        List<String> groupIds = Lists.newArrayList(pathSplitter.split(group.getPath()));

        Query query = entityManager.createNativeQuery(queryString, GroupPreference.class);

        query.setParameter("groupIds", groupIds);
        query.setParameter("types", groupPreferenceTypes);

        return query.getResultList();
    }

    /**
     * Setter for field {@code entityManager}.
     *
     * @param entityManager The new value to set for field {@code entityManager}.
     */
    @Required
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * Setter for field {@code groupRepository}.
     *
     * @param groupRepository The new value to set for field {@code groupRepository}.
     */
    @Required
    public void setGroupRepository(GroupRepository groupRepository) {
        this.groupRepository = groupRepository;
    }
}
