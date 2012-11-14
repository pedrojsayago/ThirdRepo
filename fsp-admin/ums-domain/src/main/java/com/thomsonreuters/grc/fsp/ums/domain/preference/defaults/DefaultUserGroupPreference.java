package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserGroupPreferenceType;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * {@code DefaultPreference} defined for a {@code User} and {@code Group}
 */
@Audited
@Entity
@DiscriminatorValue("user_group")
public class DefaultUserGroupPreference extends DefaultPreference<UserGroupPreferenceType> {

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private UserGroupPreferenceType type;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private DefaultUserGroupPreference(Builder builder) {
        super(builder);
        this.type = builder.type;
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
     * Setter for field {@code type}
     *
     * @param type The user to set for field {@code type}.
     */
    @Override
    public void setType(UserGroupPreferenceType type) {
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
        final DefaultUserGroupPreference other = (DefaultUserGroupPreference) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.type, other.type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), type);
    }

    /**
     * Builder for {@link DefaultUserGroupPreference}
     */
    public static class Builder extends DefaultPreferenceBuilder<Builder> {

        private UserGroupPreferenceType type;

        public Builder withType(UserGroupPreferenceType type) {
            this.type = type;
            return this;
        }

        public DefaultUserGroupPreference build() {
            return new DefaultUserGroupPreference(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
