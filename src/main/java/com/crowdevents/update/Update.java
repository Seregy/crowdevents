package com.crowdevents.update;

import com.crowdevents.project.Project;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

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
