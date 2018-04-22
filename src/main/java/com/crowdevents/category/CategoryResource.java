package com.crowdevents.category;

import com.crowdevents.core.web.Views;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class CategoryResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    private List<ProjectResource> projects;

    @JsonView(Views.Minimal.class)
    private Category parent;

    private List<Category> children;

    @JsonView(Views.Minimal.class)
    private String name;

    @JsonView(Views.Minimal.class)
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getParent() {
        return parent;
    }

    public void setParent(Category parent) {
        this.parent = parent;
    }

    public List<Category> getChildren() {
        return children;
    }

    public void setChildren(List<Category> children) {
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
