package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.thomsonreuters.grc.fsp.common.base.type.ums.UserPreferenceType;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * {@code DefaultPreference} defined for a {@code User}
 */
@Audited
@Entity
@DiscriminatorValue("user")
public class DefaultUserPreference extends DefaultPreference<UserPreferenceType> {

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private UserPreferenceType type;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private DefaultUserPreference(Builder builder) {
        super(builder);
        this.type = builder.type;
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
     * Setter for field {@code type}
     *
     * @param type The user to set for field {@code type}.
     */
    @Override
    public void setType(UserPreferenceType type) {
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
        final DefaultUserPreference other = (DefaultUserPreference) obj;
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
     * Builder for {@link DefaultUserPreference}
     */
    public static class Builder extends DefaultPreferenceBuilder<Builder> {
        private UserPreferenceType type;

        public Builder withType(UserPreferenceType type) {
            this.type = type;
            return this;
        }

        public static Builder defaultUserPreference() {
            return new Builder();
        }

        public DefaultUserPreference build() {
            return new DefaultUserPreference(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
