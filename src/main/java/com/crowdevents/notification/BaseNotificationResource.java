package com.crowdevents.notification;

import com.crowdevents.core.web.Views;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonResource;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY,
        property = "type", visible = true, defaultImpl = BaseNotificationResource.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = ContributionNotificationResource.class, name = "contribution"),
        @JsonSubTypes.Type(value = PersonNotificationResource.class, name = "person"),
        @JsonSubTypes.Type(value = UpdateNotificationResource.class, name = "update")})
@JsonView(Views.Detailed.class)
public class BaseNotificationResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonProperty("sent")
    @JsonView(Views.Minimal.class)
    private LocalDateTime dateTime;

    @JsonView(Views.Minimal.class)
    private String message;

    @JsonIgnoreProperties({"gallery_videos", "gallery_images", "owners", "subscribers",
            "contributions", "comments", "faqs", "categories", "updates", "rewards"})
    @JsonView(Views.Minimal.class)
    private ProjectResource project;

    @JsonIgnoreProperties({"password", "email", "country", "city"})
    @JsonView(Views.Minimal.class)
    private PersonResource receiver;

    @JsonProperty("type")
    @JsonView(Views.Minimal.class)
    protected NotificationType notificationType;

    /**
     * Constructs new BaseNotificationResource.
     *
     * <p>If notificationType isn't set sets it to {@code NotificationType.Base}</p>
     *
     */
    public BaseNotificationResource() {
        if (notificationType == null) {
            notificationType = NotificationType.BASE;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ProjectResource getProject() {
        return project;
    }

    public void setProject(ProjectResource project) {
        this.project = project;
    }

    public PersonResource getReceiver() {
        return receiver;
    }

    public void setReceiver(PersonResource receiver) {
        this.receiver = receiver;
    }

    public NotificationType getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(NotificationType notificationType) {
        this.notificationType = notificationType;
    }
}
