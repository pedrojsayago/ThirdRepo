/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.setting;

import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;
import com.thomsonreuters.grc.platform.util.database.repository.revision.RevisionRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Repository for {@link AbstractScreeningSetting},
 * Provide basic CRUD operations for Screening Setting by extending CrudRepository and
 * provides Paging and Sorting by extending  PagingAndSortingRepository
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 22 Oct 2012
 */
@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface ScreeningSettingRepository
        extends RevisionRepository<AbstractScreeningSetting, String>, ScreeningSettingRepositoryCustom {

    /**
     * Finds a list of {@link AbstractScreeningSetting} assigned by user
     *
     * @param hash HashCode of an object to load
     * @param <T>  SubTypes of  AbstractScreeningSetting (WatchlistScreening Setting Media
     *             ScreeningSetting)
     * @return Found instance of Screening Setting
     */
    <T extends AbstractScreeningSetting> List<T> findByHash(Integer hash);


    /**
     * Finds a {@code ScreeningSetting} by its Type such as WatchlistScreeningSetting,
     * MediaScreeningSetting
     * <p/>
     * screeningSettingRepository.findByType(WatchlistScreeningSetting.class);
     *
     * @param clazz ScreningSetting SubType Class
     * @param <T>   Screening Setting sub classes such Watchlist Setting, Media Settings
     * @return List of {? extends @link com.thomsonreuters.grc.fsp.domain.base.screening.setting
     *         .ScreeningSetting} The matching {@code ScreeningSetting}, else {@code null}
     */
    @Query("SELECT s FROM AbstractScreeningSetting s WHERE TYPE(s) = :clazz")
    <T extends AbstractScreeningSetting> List<T> findByType(
            @Param("clazz")
            Class<T> clazz);
}
