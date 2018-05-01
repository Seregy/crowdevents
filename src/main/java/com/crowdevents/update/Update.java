package com.crowdevents.update;

import com.crowdevents.project.Project;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Update {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    @Lob
    @Column(nullable = false)
    private String message;

    @Column(name = "short_message", length = 140)
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
    }

    protected Update() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
}
