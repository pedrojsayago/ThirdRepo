package com.thomsonreuters.grc.fsp.ums.domain.screening.setting;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Media Screen Settings
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 23/08/12
 */
@Audited
@Entity
public class MediaScreeningSetting extends AbstractScreeningSetting {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private MediaScreeningSetting(Builder builder) {
        super(builder);
    }

    /**
     * Builder for {@link MediaScreeningSetting}
     */
    public static class Builder extends AbstractScreeningSettingBuilder<Builder> {

        public MediaScreeningSetting build() {
            return new MediaScreeningSetting(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
