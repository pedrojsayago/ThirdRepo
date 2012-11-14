package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import com.thomsonreuters.grc.fsp.ums.domain.User;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Objects;

/**
 * {@code Preference} defined for a {@code User}
 */
@Audited
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"type", "user_id"}))
public class UserPreference extends Preference<UserPreferenceType> {

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private UserPreferenceType type;

    /**
     * {@code User} that the {@code Preference} is defined for
     */
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private UserPreference(Builder builder) {
        super(builder);
        this.type = builder.type;
        this.user = builder.user;
    }

    /**
     * Getter for field {@code type}.
     *
     * @return The current value of field {@code type}.
     */
    @Override
    public UserPreferenceType getType() {
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
     * Setter for field {@code type}
     *
     * @param type The user to set for field {@code type}.
     */
    @Override
    public void setType(UserPreferenceType type) {
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
        final UserPreference other = (UserPreference) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.type, other.type)
                && Objects.equals(this.user, other.user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), type, user);
    }

    /**
     * Builder for {@link UserPreference}
     */
    public static class Builder extends PreferenceBuilder<Builder> {
        private UserPreferenceType type;
        private User user;

        public Builder withType(UserPreferenceType type) {
            this.type = type;
            return this;
        }

        public Builder withUser(User user) {
            this.user = user;
            return this;
        }

        public UserPreference build() {
            return new UserPreference(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
