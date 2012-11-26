package com.thomsonreuters.grc.fsp.ums.domain.preference;

import com.thomsonreuters.grc.fsp.common.base.type.ums.PreferenceType;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * {@code Preference} can be defined per {@code Group}, {@code User} or {@code User} /{@code Group} combination.
 * <p/>
 * Contains key/value entries and a {@code PreferenceType}
 */
@Audited
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Preference<T extends Enum<T> & PreferenceType> extends AbstractIdentityObject {

    /**
     * {@code Map} containing key/value entries
     */
    @CollectionTable(name = "p_preference_entry",
            joinColumns = {@JoinColumn(name = "preference_id")})
    @ElementCollection(fetch = FetchType.EAGER)
    @Column(name = "entry_value")
    @MapKeyColumn(name = "entry_key")
    private Map<String, String> entries = new HashMap<>();

    /**
     * Default Constructor passing builder object as a parameter
     */
    protected Preference(PreferenceBuilder builder) {
        super(builder);
        this.entries = builder.entries;
    }

    /**
     * Getter for field {@code entries}.
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
     * Builder for {@link Preference}
     */
    public static abstract class PreferenceBuilder<T extends PreferenceBuilder<T>>
            extends AbstractIdentityObjectBuilder<T> {

        private Map<String, String> entries;

        public T withEntries(Map<String, String> entries) {
            this.entries = entries;
            return self();
        }
    }
}
