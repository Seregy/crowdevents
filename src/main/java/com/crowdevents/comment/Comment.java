package com.crowdevents.comment;

import com.crowdevents.project.Project;
import com.crowdevents.user.User;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Comment {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Project project;

    @ManyToOne
    private User author;

    @Column(nullable = false)
    private String message;

    public Comment(Project project, User author, String message) {
        this.project = project;
        this.author = author;
        this.message = message;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
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
