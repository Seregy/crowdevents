package com.crowdevents.reward;

import com.crowdevents.contribution.ContributionResource;
import com.crowdevents.project.ProjectResource;

import java.util.List;

import org.joda.money.Money;

public class RewardResource {
    private Long id;
    private ProjectResource project;
    private List<ContributionResource> contributions;
    private Money minimalContribution;
    private Integer maximumAmount;
    private String description;
    private String deliveryDate;
    private String shippedTo;

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

    public List<ContributionResource> getContributions() {
        return contributions;
    }

    public void setContributions(List<ContributionResource> contributions) {
        this.contributions = contributions;
    }

    public Money getMinimalContribution() {
        return minimalContribution;
    }

    public void setMinimalContribution(Money minimalContribution) {
        this.minimalContribution = minimalContribution;
    }

    public Integer getMaximumAmount() {
        return maximumAmount;
    }

    public void setMaximumAmount(Integer maximumAmount) {
        this.maximumAmount = maximumAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(String deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getShippedTo() {
        return shippedTo;
    }

    public void setShippedTo(String shippedTo) {
        this.shippedTo = shippedTo;
    }
}
