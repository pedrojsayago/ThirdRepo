/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.domain.preference.resolution.ResolutionLabel;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link ResolutionLabel}
 * Provide basic CRUD operations for a ResolutionLabel by extending CrudRepository and
 * provides Paging and Sorting by extending  PagingAndSortingRepository
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 30 Aug 2012
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface ResolutionLabelRepository extends RevisionRepository<ResolutionLabel, String> {

    /**
     * Finds a {@code ResolutionLabel} by its Label
     *
     * @param label Label
     * @return List of {@link ResolutionLabel}
     */
    List<ResolutionLabel> findByLabel(String label);

    /**
     * Finds a {@link ResolutionLabel} by its Label and type
     *
     * @param label name of the label
     * @param clazz Class type of the label
     * @param <T>   - type of {@link ResolutionLabel}
     * @return {@link java.util.List} of type T
     */
    @Query("SELECT l FROM ResolutionLabel l WHERE l.label = :label AND TYPE(l) = :clazz")
    <T extends ResolutionLabel> List<T> findByLabelAndType(@Param("label") String label, @Param("clazz") Class<T> clazz);
}
