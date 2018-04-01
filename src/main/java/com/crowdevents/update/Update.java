package com.crowdevents.update;

import com.crowdevents.project.Project;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Update {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    @Column(nullable = false)
    private String message;

    private String shortMessage;

    /**
     * Constructs new update message about project.
     *
     * @param project project to which update belongs
     * @param dateTime creation date and time
     * @param message update message
     */
    public Update(Project project, LocalDateTime dateTime, String message) {
        this.project = project;
        this.dateTime = dateTime;
        this.message = message;
        this.id = UUID.randomUUID();
    }

    protected Update() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
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

    public String getShortMessage() {
        return shortMessage;
    }

    public void setShortMessage(String shortMessage) {
        this.shortMessage = shortMessage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Update)) {
            return false;
        }

        Update update = (Update) o;
        return Objects.equals(id, update.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
