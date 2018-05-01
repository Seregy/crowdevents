package com.crowdevents.faq;

import com.crowdevents.core.web.Views;
import com.crowdevents.project.ProjectResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Detailed.class)
public class FaqResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonIgnoreProperties({"gallery_videos", "gallery_images", "owners", "subscribers",
            "contributions", "comments", "faqs", "categories", "updates", "rewards"})
    private ProjectResource project;

    @JsonView(Views.Minimal.class)
    private String question;

    @JsonView(Views.Minimal.class)
    private String answer;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ProjectResource getProject() {
        return project;
    }

    public void setProject(ProjectResource project) {
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
