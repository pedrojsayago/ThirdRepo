/**
 * Copyright Thomson Reuters 2012
 */
package com.thomsonreuters.grc.fsp.ums.dl.repository.setting;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Custom Screen Setting repository to provide method for save Screening Settings and update the
 * hash field with hashCode
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23 Oct 2012
 */


@Transactional(readOnly = true, propagation = Propagation.MANDATORY)
public interface ScreeningSettingRepositoryCustom {

}
