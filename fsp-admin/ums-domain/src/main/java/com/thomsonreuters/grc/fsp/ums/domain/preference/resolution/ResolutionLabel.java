package com.thomsonreuters.grc.fsp.ums.domain.preference.resolution;

import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.hibernate.envers.Audited;

import javax.persistence.*;

/**
 * Defines the user defined or default labels for Risk, Resolution and Reason,
 * These labels are associated to group as preferences.
 * <p/>
 * These are used as reference when resolving results {@code Result}
 *
 * @author Muhammad Iqbal &lt;muhammadasif.iqbal@thomsonreuters.com&gt;
 * @since 30/08/12
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Table(uniqueConstraints = @UniqueConstraint
        (name = "uniqueLabelPerTypeAndGroup", columnNames = {"groupId", "label", "labelType"}))
@DiscriminatorColumn(name = "labelType")
public abstract class ResolutionLabel extends AbstractIdentityObject {

    /**
     * Risk, Resolution or remark
     */
    @Column(nullable = false)
    private String label;

    /**
     * Represents the client or company this label is associated with
     * <p/>
     * A null value represents a default label
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "groupId")
    private Group clientGroup;

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected ResolutionLabel(ResolutionLabelBuilder builder) {
        super(builder);
        this.label = builder.label;
        this.clientGroup = builder.clientGroup;
    }

    /**
     * Get Label
     *
     * @return get label of State
     */
    public String getLabel() {
        return label;
    }

    /**
     * Set Label
     *
     * @param label - use defined name for the label
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Getter for {@code clientGroup}
     *
     * @return - {@link Group}
     */
    public Group getClientGroup() {
        return clientGroup;
    }

    /**
     * Setter for {@code clientGroup}
     *
     * @param clientGroup -represents the client or company this label is associated with
     */
    public void setClientGroup(Group clientGroup) {
        this.clientGroup = clientGroup;
    }

    /**
     * Builder for {@link ResolutionLabelBuilder}
     */
    public abstract static class ResolutionLabelBuilder<T extends ResolutionLabelBuilder<T>>
            extends AbstractIdentityObjectBuilder<T> {
        private String label;
        private Group clientGroup;

        public T withLabel(String label) {
            this.label = label;
            return self();
        }

        public T withClientGroup(Group clientGroup) {
            this.clientGroup = clientGroup;
            return self();
        }
    }
}
