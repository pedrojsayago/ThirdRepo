package com.thomsonreuters.grc.fsp.ums.dl.repository.setting;


import com.thomsonreuters.grc.fsp.ums.domain.screening.setting.AbstractScreeningSetting;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;


/**
 * Implementation class for {@link ScreeningSettingRepositoryCustom}
 * Only overrides save, update the object's hash  with hashCode and then persist
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23 Oct 2012
 */
public class ScreeningSettingRepositoryImpl
        extends SimpleJpaRepository<AbstractScreeningSetting, String>
        implements ScreeningSettingRepositoryCustom {

    /**
     * Constructor
     *
     * @param entityManager Entity Manager
     */
    public ScreeningSettingRepositoryImpl(EntityManager entityManager) {
        super(AbstractScreeningSetting.class, entityManager);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public <S extends AbstractScreeningSetting> List<S> save(Iterable<S> entities) {

        List<S> result = new ArrayList<>();

        if (entities == null) {
            return result;
        }

        for (S entity : entities) {
            S s = save(entity);
            result.add(s);
        }
        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <S extends AbstractScreeningSetting> S save(S s) {
        /**
         * update the hash with hashCode and Save Screening Setting instance
         */
        if (s != null) {
            s.setHash(s.hashCode());
        }
        return super.save(s);
    }
}
