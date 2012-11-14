package com.thomsonreuters.grc.fsp.ums.domain;

import com.thomsonreuters.grc.fsp.common.base.type.ums.GroupType;
import com.thomsonreuters.grc.fsp.ums.domain.preference.GroupPreference;
import com.thomsonreuters.grc.platform.util.database.AbstractIdentityObject;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * {@code Group} is part of a tree-like hierarchy and acts as a container for {@code Case}s,
 * {@link GroupPreference}s and {@link User}s.
 */
@Audited
@Entity
public class Group extends AbstractIdentityObject {

    /**
     * Name for the {@code Group}
     */
    @Column(nullable = false)
    private String name;

    /**
     * Parent for the {@code Group}, or {@code null} if the {@code Group} is at the root of a tree
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Group parent;

    /**
     * Token-delimited value representing the {@code Group}'s position in a tree
     */
    @Column(nullable = false, columnDefinition = "TEXT")
    private String path;

    /**
     * Values indicates the depth of the group in the tree.
     * <p/>
     * This is used for querying inherited values like preferences etc
     */
    @Column(nullable = false)
    private Integer depth;

    /**
     * Indicates if the group is a leaf
     */
    @Column(columnDefinition = "BIT", length = 1, nullable = false)
    private boolean leaf = true;

    /**
     * Type for the {@code Group}
     */
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private GroupType type = GroupType.GROUP;

    /**
     * Children for the {@code Group}
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "parent", orphanRemoval = true)
    private List<Group> children = new ArrayList<>();

    /**
     * Collection containing {@code GroupPreference}s for the {@code Group}
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "group", orphanRemoval = true)
    private List<GroupPreference> preferences = new ArrayList<>();

    /**
     * Collection containing {@code Role}s for the {@code Group}
     */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "client", orphanRemoval = true)
    private List<Role> roles = new ArrayList<>();

    /**
     * Collection containing {@code User}s that can operate on the {@code Group}
     */
    @ManyToMany(mappedBy = "groups")
    private Set<User> users = new HashSet<>();

    /**
     * Default Constructor passing builder object as a parameter
     */
    private Group(Builder builder) {
        super(builder);
        this.name = builder.name;
        this.parent = builder.parent;
        this.path = builder.path;
        this.depth = builder.depth;
        this.leaf = builder.leaf;
        this.type = builder.type;
        this.children = builder.children;
        this.preferences = builder.preferences;
        this.roles = builder.roles;
        this.users = builder.users;
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
     * Getter for field {@code parent}.
     *
     * @return The current value of field {@code parent}.
     */
    public Group getParent() {
        return parent;
    }

    /**
     * Getter for field {@code path}.
     *
     * @return The current value of field {@code path}.
     */
    public String getPath() {
        return path;
    }

    /**
     * Getter for {@code leaf}
     *
     * @return true if group is a 'leaf' false otherwise
     */
    public boolean isLeaf() {
        return leaf;
    }

    /**
     * Getter for field {@code depth}.
     *
     * @return The current value of field {@code depth}.
     */
    public Integer getDepth() {
        return depth;
    }

    /**
     * Getter for field {@code type}.
     *
     * @return The current value of field {@code type}.
     */
    public GroupType getType() {
        return type;
    }

    /**
     * Getter for field {@code children}.
     *
     * @return The current value of field {@code children}.
     */
    public List<Group> getChildren() {
        return children;
    }

    /**
     * Getter for field {@code preferences}.
     *
     * @return The current value of field {@code preferences}.
     */
    public List<GroupPreference> getPreferences() {
        return preferences;
    }

    /**
     * Getter for field {@code roles}.
     *
     * @return The current value of field {@code roles}.
     */
    public List<Role> getRoles() {
        return roles;
    }

    /**
     * Getter for field {@code users}.
     *
     * @return The current value of field {@code users}.
     */
    public Set<User> getUsers() {
        return users;
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
     * Setter for field {@code parent}.
     *
     * @param parent The new value to set for field {@code parent}.
     */
    public void setParent(Group parent) {
        this.parent = parent;
    }

    /**
     * Setter for field {@code path}.
     *
     * @param path The new value to set for field {@code path}.
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * Setter for field {@code depth}.
     *
     * @param depth The new value to set for field {@code depth}.
     */
    public void setDepth(Integer depth) {
        this.depth = depth;
    }

    /**
     * Setter for field {@code leaf}
     *
     * @param leaf - true if the group is a leaf false otherwise
     */
    public void setLeaf(boolean leaf) {
        this.leaf = leaf;
    }

    /**
     * Setter for field {@code type}.
     *
     * @param type The new value to set for field {@code type}.
     */
    public void setType(GroupType type) {
        this.type = type;
    }

    /**
     * Setter for field {@code children}.
     *
     * @param children The new value to set for field {@code children}.
     */
    public void setChildren(List<Group> children) {
        this.children = children;
    }

    /**
     * Setter for field {@code preferences}.
     *
     * @param preferences The new value to set for field {@code preferences}.
     */
    public void setPreferences(List<GroupPreference> preferences) {
        this.preferences = preferences;
    }

    /**
     * Setter for field {@code users}.
     *
     * @param users The new value to set for field {@code users}.
     */
    public void setUsers(Set<User> users) {
        this.users = users;
    }

    /**
     * Setter for field {@code roles}.
     *
     * @param roles The new value to set for field {@code roles}.
     */
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    /**
     * Builder for {@link Group}
     */
    public static class Builder extends AbstractIdentityObjectBuilder<Builder> {
        /**
         * Name for the {@code Group}
         */
        private String name;

        /**
         * Parent for the {@code Group}, or {@code null} if the {@code Group} is at the root of a tree
         */
        private Group parent;

        /**
         * Token-delimited value representing the {@code Group}'s position in a tree
         */
        private String path;

        /**
         * Values indicates the depth of the group in the tree.
         * <p/>
         * This is used for querying inherited values like preferences etc
         */
        private Integer depth;

        /**
         * Indicates if the group is a leaf
         */
        private boolean leaf = true;

        /**
         * Type for the {@code Group}
         */
        private GroupType type = GroupType.GROUP;

        /**
         * Children for the {@code Group}
         */
        private List<Group> children = new ArrayList<>();

        /**
         * Collection containing {@code GroupPreference}s for the {@code Group}
         */
        private List<GroupPreference> preferences = new ArrayList<>();

        /**
         * Collection containing {@code Role}s for the {@code Group}
         */
        private List<Role> roles = new ArrayList<>();

        /**
         * Collection containing {@code User}s that can operate on the {@code Group}
         */
        private Set<User> users = new HashSet<>();

        public Builder withName(String name) {
            this.name = name;
            return self();
        }

        public Builder withParent(Group parent) {
            this.parent = parent;
            return self();
        }

        public Builder withPath(String path) {
            this.path = path;
            return self();
        }

        public Builder withDepth(Integer depth) {
            this.depth = depth;
            return self();
        }

        public Builder withLeaf(boolean leaf) {
            this.leaf = leaf;
            return self();
        }

        public Builder withType(GroupType type) {
            this.type = type;
            return self();
        }

        public Builder withChildren(List<Group> children) {
            this.children = children;
            return self();
        }

        public Builder withPreferences(List<GroupPreference> preferences) {
            this.preferences = preferences;
            return self();
        }

        public Builder withRoles(List<Role> roles) {
            this.roles = roles;
            return self();
        }

        public Builder withUsers(Set<User> users) {
            this.users = users;
            return self();
        }

        public Group build() {
            return new Group(this);
        }

        @Override
        protected Builder self() {
            return this;
        }
    }
}
