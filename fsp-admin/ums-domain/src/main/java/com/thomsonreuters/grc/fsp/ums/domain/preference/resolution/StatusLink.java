package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * Class defines the link from Resolution Status to Risks
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 21/09/12
 */
@Audited
@Entity
public class StatusLink extends ResolutionLink {

    /**
     * Reference to the StatusLabel
     */
    @ManyToOne
    @JoinColumn(name = "statusLabelId")
    private StatusLabel statusLabel;

    /**
     * if risk level selection for this status is required
     */
    @Column(columnDefinition = "BIT", length = 1)
    private Boolean riskRequired = Boolean.FALSE;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "p_status_link_risk_link",
            joinColumns = {@JoinColumn(name = "resolutionLinkId")},
            inverseJoinColumns = {@JoinColumn(name = "riskLinkId")})
    private List<RiskLink> riskLinks;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private StatusLink(Builder builder) {
        super(builder);
        this.riskLinks = builder.riskLinks;
        this.riskRequired = builder.riskRequired;
        this.statusLabel = builder.statusLabel;
    }

    /**
     * Getter for {@code statusLabel}
     *
     * @return {@link StatusLabel}
     */
    public StatusLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * Setter for {@code statusLabel}
     *
     * @param statusLabel - {@link StatusLabel}
     */
    public void setStatusLabel(StatusLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    /**
     * Getter for {@code riskLinks}
     *
     * @return {@link java.util.List} of {@link RiskLink}
     */
    public List<RiskLink> getRiskLinks() {
        return riskLinks;
    }

    /**
     * Setter for {@code riskLinks}
     *
     * @param riskLinks - {@link java.util.List} of {@link RiskLink}
     */
    public void setRiskLinks(List<RiskLink> riskLinks) {
        this.riskLinks = riskLinks;
    }

    /**
     * Getter for {@code riskRequired}
     *
     * @return - true if risk level selection is required for this status false otherwise
     */
    public Boolean getRiskRequired() {
        return riskRequired;
    }

    /**
     * Setter for {@code riskRequired}
     *
     * @param riskRequired - true if risk level selection is required for this status false
     *                     otherwise
     */
    public void setRiskRequired(Boolean riskRequired) {
        this.riskRequired = riskRequired;
    }

    /**
     * Builder for {@link StatusLink}
     */
    public static class Builder extends ResolutionLinkBuilder<Builder> {
        private StatusLabel statusLabel;
        private Boolean riskRequired = Boolean.FALSE;
        private List<RiskLink> riskLinks;

        public Builder withStatusLabel(StatusLabel statusLabel) {
            this.statusLabel = statusLabel;
            return this;
        }

        public Builder withRiskRequired(Boolean riskRequired) {
            this.riskRequired = riskRequired;
            return this;
        }

        public Builder withRiskLinks(List<RiskLink> riskLinks) {
            this.riskLinks = riskLinks;
            return this;
        }

        public StatusLink build() {
            return new StatusLink(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
