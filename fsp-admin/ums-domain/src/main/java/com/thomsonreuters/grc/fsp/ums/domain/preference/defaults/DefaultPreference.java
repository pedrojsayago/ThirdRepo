package com.thomsonreuters.grc.fsp.ums.domain.preference.defaults;

import com.thomsonreuters.grc.fsp.common.base.type.ums.PreferenceType;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code DefaultPreference} contains default key/value entries and a {@code PreferenceType}
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "default_preference_type")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "type"))
public abstract class DefaultPreference<T extends Enum<T> & PreferenceType>
        extends AbstractIdentityObject {

    /**
     * {@code Map} containing key/value entries
     */
    @CollectionTable(name = "p_default_preference_entry",
            joinColumns = {@JoinColumn(name = "default_preference_id")})
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "entry_value")
    @MapKeyColumn(name = "entry_key")
    private Map<String, String> entries = new HashMap<>();

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected DefaultPreference(DefaultPreferenceBuilder<? extends DefaultPreferenceBuilder<?>> builder) {
        super(builder);
        entries = builder.entries;
    }

    /**
     * Getter for field {@code entries}
     *
     * @return The current value of field {@code entries}.
     */
    public Map<String, String> getEntries() {
        return entries;
    }

    /**
     * Setter for field {@code entries}.
     *
     * @param entries The new value to set for field {@code entries}.
     */
    public void setEntries(Map<String, String> entries) {
        this.entries = entries;
    }

    /**
     * Getter for field {@code type}.
     *
     * @return The current value of field {@code type}.
     */
    public abstract T getType();

    /**
     * Setter for field {@code type}.
     *
     * @param type The new value to set for field {@code type}.
     */
    public abstract void setType(T type);

    /**
     * Builder for {@link DefaultPreference}
     */
    public abstract static class DefaultPreferenceBuilder<T extends DefaultPreferenceBuilder<T>>
            extends AbstractIdentityObjectBuilder<T> {
        private Map<String, String> entries;

        public DefaultPreferenceBuilder() {
        }

        public T withEntries(Map<String, String> entries) {
            this.entries = entries;
            return self();
        }
    }
}
