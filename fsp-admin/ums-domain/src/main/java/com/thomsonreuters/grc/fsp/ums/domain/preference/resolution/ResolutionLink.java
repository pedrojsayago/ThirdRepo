package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.List;

/**
 * Defines the relationship between Resolution status,risk and remarks and whether the notes and remarks field
 * is required for the given combination.
 *
 * @author Bhaskar Rao <Bhaskar.Rao@thomsonreuters.com>
 * @since 18/09/12
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "linkType")
public abstract class ResolutionLink extends AbstractIdentityObject {

    @Column(columnDefinition = "BIT", length = 1, nullable = false)
    private boolean reasonRequired = Boolean.FALSE;

    @Column(columnDefinition = "BIT", length = 1, nullable = false)
    private boolean remarkRequired = Boolean.FALSE;

    /**
     * Collection of Remarks
     */
    @ManyToMany
    @JoinTable(
            joinColumns = {@JoinColumn(name = "resolutionLinkId")},
            inverseJoinColumns = {@JoinColumn(name = "reasonLabelId")})
    private List<ReasonLabel> reasonLabels;

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected ResolutionLink(ResolutionLinkBuilder<? extends ResolutionLinkBuilder<?>> builder) {
        super(builder);
        this.reasonRequired = builder.reasonRequired;
        this.remarkRequired = builder.remarkRequired;
        this.reasonLabels = builder.reasonLabels;
    }

    /**
     * Getter for {@code remarkRequired}
     *
     * @return - true if remark field is required false otherwise
     */
    public boolean isRemarkRequired() {
        return remarkRequired;
    }

    /**
     * Getter for {@code reasonRequired}
     *
     * @return - true if note field is required false otherwise
     */
    public boolean isReasonRequired() {
        return reasonRequired;
    }

    /**
     * setter for {@code reasonRequired}
     *
     * @param reasonRequired - true if note field is required false otherwise
     */
    public void setReasonRequired(boolean reasonRequired) {
        this.reasonRequired = reasonRequired;
    }

    /**
     * setter for {@code remarkRequired}
     *
     * @param remarkRequired - true if remark field is required false otherwise
     */
    public void setRemarkRequired(boolean remarkRequired) {
        this.remarkRequired = remarkRequired;
    }

    /**
     * Getter for {@code reasonLabels}
     *
     * @return {@link java.util.List} of {@link ReasonLabel}
     */
    public List<ReasonLabel> getReasonLabels() {
        return reasonLabels;
    }

    /**
     * Setter for {@code reasonLabels}
     *
     * @param reasonLabels {@link java.util.List} of {@link ReasonLabel}
     */
    public void setReasonLabels(List<ReasonLabel> reasonLabels) {
        this.reasonLabels = reasonLabels;
    }

    /**
     * Builder for {@link ResolutionLinkBuilder}
     */
    public static abstract class ResolutionLinkBuilder<T extends ResolutionLinkBuilder<T>>
            extends AbstractIdentityObjectBuilder<T> {
        private boolean reasonRequired;
        private boolean remarkRequired;
        private List<ReasonLabel> reasonLabels;

        public T withReasonRequired(boolean reasonRequired) {
            this.reasonRequired = reasonRequired;
            return self();
        }

        public T withRemarkRequired(boolean remarkRequired) {
            this.remarkRequired = remarkRequired;
            return self();
        }

        public T withReasonLabels(List<ReasonLabel> reasonLabels) {
            this.reasonLabels = reasonLabels;
            return self();
        }
    }
}
