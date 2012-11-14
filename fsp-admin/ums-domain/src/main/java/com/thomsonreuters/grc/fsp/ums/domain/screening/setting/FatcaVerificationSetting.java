package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Fatca Screen Settings
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23/08/12
 */
@Audited
@Entity
public class FatcaVerificationSetting extends AbstractScreeningSetting {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private FatcaVerificationSetting(Builder builder) {
        super(builder);
    }

    /**
     * Builder for {@link FatcaVerificationSetting}
     */
    public static class Builder extends AbstractScreeningSettingBuilder<Builder> {

        public FatcaVerificationSetting build() {
            return new FatcaVerificationSetting(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
