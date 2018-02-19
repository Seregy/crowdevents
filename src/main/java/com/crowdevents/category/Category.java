package com.crowdevents.category;

import com.crowdevents.project.Project;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Category {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToMany
    private Set<Project> projects;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children;

    @Column(nullable = false)
    private String name;

    private String description;

    public Category(String name) {
        this(name, null, null);
    }

    public Category(String name, String description) {
        this(name, description, null);
    }

    public Category(String name, Category parent) {
        this(name, null, parent);
    }

    public Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
        this.id = UUID.randomUUID();
    }

    protected Category() {

    }

    public UUID getId() {
        return id;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    public void setChildren(Set<Category> children) {
        this.children = children;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Category)) {
            return false;
        }

        Category category = (Category) o;
        return Objects.equals(id, category.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
