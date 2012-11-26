package com.thomsonreuters.grc.fsp.ums.dl.repository;

import java.util.List;

/**
 * Custom repository for Group
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 12/10/12
 */
public interface GroupRepositoryCustom {

    /**
     * Method retrieves ids of all groups where their path startsWith the given prefix
     *
     * @param pathPrefix - path prefix
     * @return - {@link java.util.List} of {@link String}
     */
    List<String> findByPathStartingWith(String pathPrefix);
}
