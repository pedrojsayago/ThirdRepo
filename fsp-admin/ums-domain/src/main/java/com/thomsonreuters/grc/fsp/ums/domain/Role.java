package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.fsp.common.base.type.ums.PermissionType;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.EnumSet;
import java.util.Objects;
import java.util.Set;

/**
 * {@code Role} is a {@code Group}-specific container for {@code PermissionType}s
 */
@Audited
@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"client_id", "name"}))
public class Role extends AbstractIdentityObject {

    /**
     * {@code Group} that defined the {@code Role}
     */
    @ManyToOne(optional = false)
    @JoinColumn(name = "client_id")
    private Group client;

    /**
     * Name for the {@code Role}
     */
    @Column(nullable = false)
    private String name;

    /**
     * Collection containing {@code PermissionType}s
     */
    @CollectionTable(name = "p_role_permission",
            joinColumns = {@JoinColumn(name = "role_id")},
            uniqueConstraints = {@UniqueConstraint(columnNames = {"role_id", "permission"})})
    @Column(name = "permission")
    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<PermissionType> permissions = EnumSet.noneOf(PermissionType.class);

    /**
     * Default Constructor passing builder object as a parameter
     */
    private Role(Builder builder) {
        super(builder);
        this.client = builder.client;
        this.name = builder.name;
        this.permissions = builder.permissions;
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
     * Getter for field {@code name}.
     *
     * @return The current value of field {@code name}.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for field {@code permissions}.
     *
     * @return The current value of field {@code permissions}.
     */
    public Set<PermissionType> getPermissions() {
        return permissions;
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
     * Setter for field {@code name}.
     *
     * @param name The new value to set for field {@code name}.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Setter for field {@code permissions}.
     *
     * @param permissions The new value to set for field {@code permissions}.
     */
    public void setPermissions(Set<PermissionType> permissions) {
        this.permissions = permissions;
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
        final Role other = (Role) obj;
        return Objects.equals(this.getId(), other.getId())
                && Objects.equals(this.client, other.client)
                && Objects.equals(this.name, other.name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(getId(), client, name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link Role}
     */
    public static class Builder extends AbstractIdentityObjectBuilder<Builder> {
        /**
         * {@code Group} that defined the {@code Role}
         */
        private Group client;

        /**
         * Name for the {@code Role}
         */
        private String name;

        /**
         * Collection containing {@code PermissionType}s
         */
        private Set<PermissionType> permissions = EnumSet.noneOf(PermissionType.class);

        public Builder withClient(Group client) {
            this.client = client;
            return self();
        }

        public Builder withName(String name) {
            this.name = name;
            return self();
        }

        public Builder withPermissions(Set<PermissionType> permissions) {
            this.permissions = permissions;
            return self();
        }

        public Role build() {
            return new Role(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
