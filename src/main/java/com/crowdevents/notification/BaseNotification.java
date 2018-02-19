package com.crowdevents.notification;

import com.crowdevents.project.Project;
import com.crowdevents.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class BaseNotification {
    @Id
    @Column(unique = true)
    private UUID id;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String message;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User receiver;

    public BaseNotification(String message, User receiver, LocalDateTime dateTime, Project project) {
        this.message = message;
        this.receiver = receiver;
        this.dateTime = dateTime;
        this.project = project;
        this.id = UUID.randomUUID();
    }

    protected BaseNotification() {

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof BaseNotification)) {
            return false;
        }

        BaseNotification that = (BaseNotification) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
