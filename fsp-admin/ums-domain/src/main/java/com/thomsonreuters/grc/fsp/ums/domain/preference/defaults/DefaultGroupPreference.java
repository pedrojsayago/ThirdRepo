package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupPreferenceType;
import org.hibernate.envers.Audited;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.Objects;

/**
 * {@code DefaultPreference} defined for a {@code Group}
 */
@Audited
@Entity
@DiscriminatorValue("group")
public class DefaultGroupPreference extends DefaultPreference<GroupPreferenceType> {

    /**
     * {@code PreferenceType} for the {@code Preference}
     */
    @Enumerated(EnumType.STRING)
    private GroupPreferenceType type;

    /**
     * Default Constructor passing builder object as a parameter
     */
    private DefaultGroupPreference(Builder builder) {
        super(builder);
        type = builder.type;
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
        final DefaultGroupPreference other = (DefaultGroupPreference) obj;
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
     * Builder for {@link DefaultGroupPreference}
     */
    public static class Builder extends DefaultPreferenceBuilder<Builder> {

        /**
         * {@code PreferenceType} for the {@code Preference}
         */
        private GroupPreferenceType type;

        public Builder withType(GroupPreferenceType type) {
            this.type = type;
            return this;
        }

        public Builder defaultGroupPreference() {
            return new Builder();
        }

        public DefaultGroupPreference build() {
            return new DefaultGroupPreference(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}

