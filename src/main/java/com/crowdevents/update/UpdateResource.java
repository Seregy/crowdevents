package com.crowdevents.update;

import com.crowdevents.core.web.Views;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

@JsonView(Views.Detailed.class)
public class UpdateResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonIgnoreProperties({"gallery_videos", "gallery_images", "owners", "subscribers",
            "contributions", "comments", "faqs", "categories", "updates", "rewards"})
    private ProjectResource project;

    @JsonProperty("posted")
    @JsonView(Views.Minimal.class)
    private LocalDateTime dateTime;

    @JsonView(Views.Minimal.class)
    private String title;

    private String message;

    @JsonProperty("short_message")
    @JsonView(Views.Minimal.class)
    private String shortMessage;

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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }
}
