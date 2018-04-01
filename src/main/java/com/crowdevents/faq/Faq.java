package com.crowdevents.faq;

import com.crowdevents.project.Project;

import java.util.Objects;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

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

    /**
     * Constructs new frequently asked question with answer.
     *
     * @param project project to which faq belongs
     * @param question frequently asked question
     * @param answer answer to the question
     */
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
