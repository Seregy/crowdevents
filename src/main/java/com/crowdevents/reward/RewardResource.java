package com.crowdevents.reward;

import com.crowdevents.contribution.ContributionResource;
import com.crowdevents.core.web.Views;
import com.crowdevents.project.ProjectResource;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

import org.joda.money.Money;

@JsonView(Views.Detailed.class)
public class RewardResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonIgnoreProperties({"gallery_videos", "gallery_images", "owners", "subscribers",
            "contributions", "comments", "faqs", "categories", "updates", "rewards"})
    private ProjectResource project;

    @JsonIgnoreProperties({"project", "reward"})
    private List<ContributionResource> contributions;

    @JsonProperty("minimal_contribution")
    @JsonView(Views.Minimal.class)
    private Money minimalContribution;

    @JsonProperty("maximum_amount")
    @JsonView(Views.Minimal.class)
    private Integer maximumAmount;

    @JsonView(Views.Minimal.class)
    private String description;

    @JsonProperty("delivery_date")
    @JsonView(Views.Minimal.class)
    private String deliveryDate;

    @JsonProperty("shipped_to")
    @JsonView(Views.Minimal.class)
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
