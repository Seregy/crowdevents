package com.crowdevents.comment;

import com.crowdevents.core.web.Views;
import com.crowdevents.person.PersonResource;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

@JsonView(Views.Detailed.class)
public class CommentResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonView(Views.Minimal.class)
    @JsonIgnoreProperties({"gallery_videos", "gallery_images", "owners", "subscribers",
            "contributions", "comments", "faqs", "categories", "updates", "rewards"})
    private ProjectResource project;

    @JsonView(Views.Minimal.class)
    @JsonIgnoreProperties({"password", "email", "country", "city"})
    private PersonResource author;

    @JsonView(Views.Minimal.class)
    private String message;

    @JsonProperty("posted")
    @JsonView(Views.Minimal.class)
    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectResource getProject() {
        return project;
    }

    public void setProject(ProjectResource project) {
        this.project = project;
    }

    public PersonResource getAuthor() {
        return author;
    }

    public void setAuthor(PersonResource author) {
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
