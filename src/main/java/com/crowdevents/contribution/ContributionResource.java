package com.crowdevents.contribution;

import com.crowdevents.core.web.Views;
import com.crowdevents.person.PersonResource;
import com.crowdevents.project.ProjectResource;
import com.crowdevents.reward.RewardResource;
import com.fasterxml.jackson.annotation.JsonView;
import java.time.LocalDateTime;
import org.joda.money.Money;

public class ContributionResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonView(Views.Minimal.class)
    private PersonResource contributor;

    private ProjectResource project;
    private LocalDateTime dateTime;

    @JsonView(Views.Minimal.class)
    private Money money;

    @JsonView(Views.Minimal.class)
    private RewardResource reward;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonResource getContributor() {
        return contributor;
    }

    public void setContributor(PersonResource contributor) {
        this.contributor = contributor;
    }

    public ProjectResource getProject() {
        return project;
    }

    public void setProject(ProjectResource project) {
        this.project = project;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public RewardResource getReward() {
        return reward;
    }

    public void setReward(RewardResource reward) {
        this.reward = reward;
    }
}
