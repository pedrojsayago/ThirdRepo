/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.preference;

import com.thomsonreuters.grc.fsp.ums.domain.preference.CustomFieldType;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link CustomFieldType}
 * Provide basic CRUD operations for a StateLabel by extending CrudRepository and
 * provides Paging and Sorting by extending  PagingAndSortingRepository
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 30 Aug 2012
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface CustomFieldTypeRepository extends RevisionRepository<CustomFieldType, String> {

    /**
     * Finds a {@code CustomFieldType} by its Label
     *
     * @param label Label
     * @return List of {@link .CustomFieldType} The matching {@code CustomFieldType}, else {@code null}
     */
    List<CustomFieldType> findByLabel(String label);
}
