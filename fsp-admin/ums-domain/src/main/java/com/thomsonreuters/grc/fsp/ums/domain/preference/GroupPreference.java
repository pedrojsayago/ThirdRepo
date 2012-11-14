package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * {@code Preference} defined for a {@code Group}
 */
@Audited
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "type"}))
public class GroupPreference extends Preference<GroupPreferenceType> {

    /**
     * {@code Group} that the {@code Preference} is defined for
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "group_id")
    private Group group;

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private GroupPreferenceType type;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private GroupPreference(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.group = builder.group;
    }

    /**
     * Getter for field {@code group}.
     *
     * @return The current value of field {@code group}.
     */
    public Group getGroup() {
        return group;
    }

    /**
     * Getter for field {@code type}.
     *
     * @return The current value of field {@code type}.
     */
    @Override
    public GroupPreferenceType getType() {
        return type;
    }

    /**
     * Setter for field {@code group}
     *
     * @param group The user to set for field {@code group}.
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Setter for field {@code type}
     *
     * @param type The user to set for field {@code type}.
     */
    @Override
    public void setType(GroupPreferenceType type) {
        this.type = type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GroupPreference other = (GroupPreference) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.group, other.group)
                && Objects.equals(this.type, other.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), group, type);
    }

    /**
     * Builder for {@link GroupPreference}
     */
    public static class Builder extends PreferenceBuilder<Builder> {
        private Group group;
        private GroupPreferenceType type;

        public Builder withGroup(Group group) {
            this.group = group;
            return self();
        }

        public Builder withType(GroupPreferenceType type) {
            this.type = type;
            return self();
        }

        public GroupPreference build() {
            return new GroupPreference(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

