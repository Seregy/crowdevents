package com.crowdevents.faq;

import com.crowdevents.project.Project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Faq {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Project project;

    @Column(nullable = false, length = 140)
    private String question;

    @Column(nullable = false, length = 140)
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
    }

    protected Faq() {

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
}
