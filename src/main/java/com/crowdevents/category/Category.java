package com.crowdevents.category;

import com.crowdevents.project.Project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Category {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    private Set<Project> projects;

    @ManyToOne
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private Set<Category> children = new HashSet<>();

    @Column(nullable = false)
    private String name;

    private String description;

    public Category(String name, String description) {
        this(name, description, null);
    }

    /**
     * Constructs new category for projects.
     *
     * @param name name of the category
     * @param description short description
     * @param parent parent category
     */
    public Category(String name, String description, Category parent) {
        this.name = name;
        this.description = description;
        this.parent = parent;
    }

    protected Category() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void addChild(Category category) {
        children.add(category);
        category.setParent(this);
    }

    public void removeChild(Category category) {
        children.remove(category);
        category.setParent(null);
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
}
