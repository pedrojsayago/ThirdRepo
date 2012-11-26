package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Eidv Screen Settings
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23/08/12
 */
@Audited
@Entity
public class EidVerificationSetting extends AbstractScreeningSetting {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private EidVerificationSetting(Builder builder) {
        super(builder);
    }

    /**
     * Builder for {@link EidVerificationSetting}
     */
    public static class Builder extends AbstractScreeningSettingBuilder<Builder> {

        public EidVerificationSetting build() {
            return new EidVerificationSetting(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
