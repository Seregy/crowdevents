package com.crowdevents.faq;

import com.crowdevents.project.Project;

import javax.persistence.*;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Faq {
    @Id
    @Column(unique = true)
    private UUID id;

    @ManyToOne
    private Project project;

    @Column(nullable = false)
    private String question;

    @Column(nullable = false)
    private String answer;

    public Faq(Project project, String question, String answer) {
        this.project = project;
        this.question = question;
        this.answer = answer;
        this.id = UUID.randomUUID();
    }

    protected Faq() {

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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Faq)) {
            return false;
        }

        Faq faq = (Faq) o;
        return Objects.equals(id, faq.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
