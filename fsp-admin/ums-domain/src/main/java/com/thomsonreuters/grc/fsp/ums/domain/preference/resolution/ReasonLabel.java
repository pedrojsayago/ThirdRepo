package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import org.hibernate.envers.Audited;

import javax.persistence.Entity;

/**
 * Defines the remark associated with the {@code Result}
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 19/09/12
 */
@Audited
@Entity
public class ReasonLabel extends ResolutionLabel {

    /**
     * Default Constructor passing builder object as a parameter
     */
    private ReasonLabel(Builder reasonLabelBuilder) {
        super(reasonLabelBuilder);
    }

    /**
     * Builder for {@link ReasonLabel}
     */
    public static class Builder extends ResolutionLabelBuilder<Builder> {

        public ReasonLabel build() {
            return new ReasonLabel(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
