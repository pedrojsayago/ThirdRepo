package com.thomsonreuters.grc.fsp.ums.dl.repository;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Implementation class for {@link GroupRepositoryCustom}
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 12/10/12
 */
public class GroupRepositoryImpl implements GroupRepositoryCustom {

    /**
     * Instance of EntityManager
     */
    private EntityManager entityManager;

    /**
     * Setter for {@code entityManager}
     *
     * @param entityManager
     */
    @Required
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<String> findByPathStartingWith(String pathPrefix) {
        Assert.hasText(pathPrefix);

        StringBuilder pathBuilder = new StringBuilder(pathPrefix);
        pathBuilder.append("%");

        // Fetch all groups where path begins with the given prefix
        TypedQuery<String> query =
                entityManager.createQuery("SELECT id FROM Group g WHERE g.path like :pathPrefix", String.class);
        query.setParameter("pathPrefix", pathBuilder.toString());

        return query.getResultList();
    }
}
