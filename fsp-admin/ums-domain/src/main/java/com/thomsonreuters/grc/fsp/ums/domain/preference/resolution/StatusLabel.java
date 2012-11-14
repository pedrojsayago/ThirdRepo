package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;

/**
 * Defines StatusLabel associated with a {@code Result}
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 17/09/12
 */
@Audited
@Entity
public class StatusLabel extends ResolutionLabel {

    /**
     * Indicates if this status is identified as 'Positive'
     */
    @Column(columnDefinition = "BIT", length = 1)
    private Boolean positive = Boolean.FALSE;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private StatusLabel(Builder builder) {
        super(builder);
        this.positive = builder.positive;
    }

    /**
     * Getter for {@code positive}
     *
     * @return - true if positive false otherwise
     */
    public Boolean getPositive() {
        return positive;
    }

    /**
     * Setter for {@code positive}
     *
     * @param positive - True if this status is positive false otherwise
     */
    public void setPositive(Boolean positive) {
        this.positive = positive;
    }

    /**
     * Builder for {@link StatusLabel}
     */
    public static class Builder extends ResolutionLabelBuilder<Builder> {
        private Boolean positive = Boolean.FALSE;

        public Builder withPositive(Boolean positive) {
            this.positive = positive;
            return self();
        }

        public static Builder statusLabel() {
            return new Builder();
        }

        public StatusLabel build() {
            return new StatusLabel(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
