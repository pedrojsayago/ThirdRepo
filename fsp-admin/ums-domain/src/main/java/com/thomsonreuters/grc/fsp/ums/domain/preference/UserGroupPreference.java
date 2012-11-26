package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.Group;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * {@code Preference} defined for a {@code User} and {@code Group}
 */
@Audited
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "type", "user_id"}))
public class UserGroupPreference extends Preference<UserGroupPreferenceType> {

    /**
     * {@code Group} that the {@code Preference} is defined for
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "group_id")
    private Group group;

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private UserGroupPreferenceType type;

    /**
     * {@code User} that the {@code Preference} is defined for
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private UserGroupPreference(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.user = builder.user;
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
    public UserGroupPreferenceType getType() {
        return type;
    }

    /**
     * Getter for field {@code user}.
     *
     * @return The current value of field {@code user}.
     */
    public User getUser() {
        return user;
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
    public void setType(UserGroupPreferenceType type) {
        this.type = type;
    }

    /**
     * Setter for field {@code user}
     *
     * @param user The user to set for field {@code user}.
     */
    public void setUser(User user) {
        this.user = user;
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
        final UserGroupPreference other = (UserGroupPreference) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.group, other.group)
                && Objects.equals(this.type, other.type)
                && Objects.equals(this.user, other.user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), group, type, user);
    }

    /**
     * Builder for {@link UserGroupPreference}
     */
    public static class Builder extends PreferenceBuilder {
        private Group group;
        private UserGroupPreferenceType type;
        private User user;

        public Builder withGroup(Group group) {
            this.group = group;
            return this;
        }

        public Builder withType(UserGroupPreferenceType type) {
            this.type = type;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public UserGroupPreference build() {
            return new UserGroupPreference(this);
        }

        @Override
        protected PreferenceBuilder self() {
            return this;
        }
    }
}
