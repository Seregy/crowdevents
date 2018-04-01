package com.crowdevents.comment;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Comment {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private Person author;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    /**
     * Constructs new comment to the project.
     *
     * @param project project to which comment belongs
     * @param author person who wrote comment
     * @param message message
     * @param dateTime creation date and time
     */
    public Comment(Project project, Person author, String message, LocalDateTime dateTime) {
        this.project = project;
        this.author = author;
        this.message = message;
        this.dateTime = dateTime;
        this.id = UUID.randomUUID();
    }

    protected Comment() {

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

    public Person getAuthor() {
        return author;
    }

    public void setAuthor(Person author) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Comment)) {
            return false;
        }

        Comment comment = (Comment) o;
        return Objects.equals(id, comment.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
