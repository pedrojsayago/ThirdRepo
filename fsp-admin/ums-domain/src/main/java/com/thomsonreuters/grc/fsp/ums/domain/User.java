package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.fsp.ums.domain.preference.UserPreference;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.*;

/**
 * {@code User} has operable {@link Group}s, {@link UserPreference}s and {@link Role}s
 */
@Audited
@Entity
public class User extends AbstractIdentityObject {

    /**
     * {@code Client} that the {@code User} is part of
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Group client;

    /**
     * Username for the {@code User}
     * <p/>
     * Must be an email address
     */
    @Column(nullable = false, unique = true)
    private String username;

    /**
     * Collection containing {@code Group}s that the {@code User} can operate on
     */
    @ManyToMany
    @JoinTable(name = "p_user_group",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "group_id")})
    private Set<Group> groups = new HashSet<>();

    /**
     * Collection containing {@code UserPreference} for the {@code User}
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", orphanRemoval = true)
    private List<UserPreference> preferences = new ArrayList<>();

    /**
     * Collection containing {@code Client}-specific {@code Role}s
     */
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "p_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles = new HashSet<>();

    /**
     * Default Constructor passing builder object as a parameter
     */
    private User(Builder builder) {
        super(builder);
        this.client = builder.client;
        this.username = builder.username;
        this.groups = builder.groups;
        this.preferences = builder.preferences;
        this.roles = builder.roles;
    }

    /**
     * Getter for field {@code client}.
     *
     * @return The current value of field {@code client}.
     */
    public Group getClient() {
        return client;
    }

    /**
     * Getter for field {@code username}.
     *
     * @return The current value of field {@code username}.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Gets Different groups that an user can have..
     *
     * @return Value of Different groups that an user can have..
     */
    public Set<Group> getGroups() {
        return groups;
    }

    /**
     * Getter for field {@code preferences}.
     *
     * @return The current value of field {@code preferences}.
     */
    public List<UserPreference> getPreferences() {
        return preferences;
    }

    /**
     * Getter for field {@code roles}.
     *
     * @return The current value of field {@code roles}.
     */
    public Set<Role> getRoles() {
        return roles;
    }

    /**
     * Setter for field {@code client}.
     *
     * @param client The new value to set for field {@code client}.
     */
    public void setClient(Group client) {
        this.client = client;
    }

    /**
     * Setter for field {@code username}.
     *
     * @param username The new value to set for field {@code username}.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Setter for field {@code groups}.
     *
     * @param groups The new value to set for field {@code groups}.
     */
    public void setGroups(Set<Group> groups) {
        this.groups = groups;
    }

    /**
     * Setter for field {@code preferences}.
     *
     * @param preferences The new value to set for field {@code preferences}.
     */
    public void setPreferences(List<UserPreference> preferences) {
        this.preferences = preferences;
    }

    /**
     * Setter for field {@code roles}.
     *
     * @param roles The new value to set for field {@code roles}.
     */
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
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
        final User other = (User) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.username, other.username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link User}
     */
    public static class Builder extends AbstractIdentityObjectBuilder<Builder> {
        /**
         * {@code Client} that the {@code User} is part of
         */
        private Group client;

        /**
         * Username for the {@code User}
         * <p/>
         * Must be an email address
         */
        private String username;

        /**
         * Collection containing {@code Group}s that the {@code User} can operate on
         */
        private Set<Group> groups = new HashSet<>();

        /**
         * Collection containing {@code UserPreference} for the {@code User}
         */
        private List<UserPreference> preferences = new ArrayList<>();

        /**
         * Collection containing {@code Client}-specific {@code Role}s
         */
        private Set<Role> roles = new HashSet<>();

        public Builder withClient(Group client) {
            this.client = client;
            return self();
        }

        public Builder withUsername(String username) {
            this.username = username;
            return self();
        }

        public Builder withGroups(Set<Group> groups) {
            this.groups = groups;
            return self();
        }

        public Builder withPreferences(List<UserPreference> preferences) {
            this.preferences = preferences;
            return self();
        }

        public Builder withRoles(Set<Role> roles) {
            this.roles = roles;
            return self();
        }

        public User build() {
            return new User(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
