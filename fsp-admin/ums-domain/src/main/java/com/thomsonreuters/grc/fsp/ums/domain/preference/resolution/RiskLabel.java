package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Defines the Risk associated with the {@code Result}
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 19/09/12
 */
@Audited
@Entity
public class RiskLabel extends ResolutionLabel {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private RiskLabel(Builder riskLabelBuilder) {
        super(riskLabelBuilder);
    }

    /**
     * Builder for {@link RiskLabel}
     */
    public static class Builder extends ResolutionLabelBuilder<Builder> {

        public RiskLabel build() {
            return new RiskLabel(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
