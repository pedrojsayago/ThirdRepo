package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Class defines the link from risk to remarks
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 21/09/12
 */
@Audited
@Entity
public class RiskLink extends ResolutionLink {

    /**
     * Reference to the RiskLabel
     */
    @ManyToOne
    @JoinColumn(name = "riskLabelId")
    private RiskLabel riskLabel;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private RiskLink(Builder builder) {
        super(builder);
        this.riskLabel = builder.riskLabel;
    }

    /**
     * Getter for {@code riskLabel}
     *
     * @return {@link RiskLabel}
     */
    public RiskLabel getRiskLabel() {
        return riskLabel;
    }

    /**
     * Setter for {@code RiskLabel}
     *
     * @param riskLabel - {@link RiskLabel}
     */
    public void setRiskLabel(RiskLabel riskLabel) {
        this.riskLabel = riskLabel;
    }

    /**
     * Builder for {@link RiskLink}
     */
    public static class Builder extends ResolutionLinkBuilder<Builder> {
        private RiskLabel riskLabel;

        public Builder withRiskLabel(RiskLabel riskLabel) {
            this.riskLabel = riskLabel;
            return this;
        }

        public RiskLink build() {
            return new RiskLink(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
