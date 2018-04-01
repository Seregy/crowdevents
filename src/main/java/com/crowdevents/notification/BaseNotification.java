package com.crowdevents.notification;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity(name = "notification")
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
    private Person receiver;

    /**
     * Constructs new generic notification that has message and refers to some project.
     *
     * @param message notification message
     * @param receiver person who will receive the notification
     * @param dateTime creation date and time
     * @param project project, referenced by the notification
     */
    public BaseNotification(String message, Person receiver, LocalDateTime dateTime,
                            Project project) {
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

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
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
