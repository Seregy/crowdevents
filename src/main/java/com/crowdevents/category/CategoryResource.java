package com.crowdevents.category;

import com.crowdevents.core.web.Views;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

@JsonView(Views.Detailed.class)
public class CategoryResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    private List<ProjectResource> projects;

    @JsonView(Views.Minimal.class)
    @JsonIgnoreProperties({"parent", "children"})
    private CategoryResource parent;

    @JsonIgnoreProperties({"parent", "children"})
    private List<CategoryResource> children;

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

    public CategoryResource getParent() {
        return parent;
    }

    public void setParent(CategoryResource parent) {
        this.parent = parent;
    }

    public List<CategoryResource> getChildren() {
        return children;
    }

    public void setChildren(List<CategoryResource> children) {
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
