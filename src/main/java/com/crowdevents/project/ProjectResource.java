package com.crowdevents.project;

import com.crowdevents.category.CategoryResource;
import com.crowdevents.comment.CommentResource;
import com.crowdevents.contribution.ContributionResource;
import com.crowdevents.core.web.Views;
import com.crowdevents.faq.FaqResource;
import com.crowdevents.location.LocationResource;
import com.crowdevents.person.PersonResource;
import com.crowdevents.reward.RewardResource;
import com.crowdevents.update.UpdateResource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.joda.money.Money;

@JsonView(Views.Detailed.class)
public class ProjectResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonView(Views.Minimal.class)
    private String name;

    @JsonProperty("short_description")
    @JsonView(Views.Minimal.class)
    private String shortDescription;

    private String description;

    @JsonView(Views.Minimal.class)
    private LocationResource location;

    @JsonProperty("starts")
    @JsonView(Views.Minimal.class)
    private LocalDateTime startDateTime;

    @JsonProperty("ends")
    @JsonView(Views.Minimal.class)
    private LocalDateTime endDateTime;

    @JsonProperty("event_date")
    @JsonView(Views.Minimal.class)
    private LocalDateTime eventDateTime;

    @JsonProperty("funding_goal")
    @JsonView(Views.Minimal.class)
    private Money fundingGoal;

    @JsonProperty("raised")
    @JsonView(Views.Minimal.class)
    private Money raisedMoney;

    @JsonProperty("project_image")
    @JsonView(Views.Minimal.class)
    private String projectImageLink;

    @JsonView(Views.Minimal.class)
    private ProjectType type;

    @JsonView(Views.Minimal.class)
    private ProjectVisibility visibility;

    @JsonProperty("gallery_videos")
    private List<String> videoLinks = new ArrayList<>();
    @JsonProperty("gallery_images")
    private List<String> imageLinks = new ArrayList<>();

    @JsonView(Views.Minimal.class)
    private List<PersonResource> owners = new ArrayList<>();

    private List<PersonResource> subscribers = new ArrayList<>();
    private List<ContributionResource> contributions = new ArrayList<>();
    private List<CommentResource> comments = new ArrayList<>();
    private List<FaqResource> faqs = new ArrayList<>();

    @JsonView(Views.Minimal.class)
    private List<CategoryResource> categories = new ArrayList<>();

    private List<UpdateResource> updates = new ArrayList<>();
    private List<RewardResource> rewards = new ArrayList<>();

    public ProjectResource() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocationResource getLocation() {
        return location;
    }

    public void setLocation(LocationResource location) {
        this.location = location;
    }

    public LocalDateTime getStartDateTime() {
        return startDateTime;
    }

    public void setStartDateTime(LocalDateTime startDateTime) {
        this.startDateTime = startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return endDateTime;
    }

    public void setEndDateTime(LocalDateTime endDateTime) {
        this.endDateTime = endDateTime;
    }

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    public Money getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(Money fundingGoal) {
        this.fundingGoal = fundingGoal;
    }

    public Money getRaisedMoney() {
        return raisedMoney;
    }

    public void setRaisedMoney(Money raisedMoney) {
        this.raisedMoney = raisedMoney;
    }

    public String getProjectImageLink() {
        return projectImageLink;
    }

    public void setProjectImageLink(String projectImageLink) {
        this.projectImageLink = projectImageLink;
    }

    public List<String> getVideoLinks() {
        return videoLinks;
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public List<PersonResource> getOwners() {
        return owners;
    }

    public void setOwners(List<PersonResource> owners) {
        this.owners = owners;
    }

    public List<PersonResource> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<PersonResource> subscribers) {
        this.subscribers = subscribers;
    }

    public List<ContributionResource> getContributions() {
        return contributions;
    }

    public void setContributions(List<ContributionResource> contributions) {
        this.contributions = contributions;
    }

    public List<CommentResource> getComments() {
        return comments;
    }

    public void setComments(List<CommentResource> comments) {
        this.comments = comments;
    }

    public List<FaqResource> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<FaqResource> faqs) {
        this.faqs = faqs;
    }

    public List<CategoryResource> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryResource> categories) {
        this.categories = categories;
    }

    public List<UpdateResource> getUpdates() {
        return updates;
    }

    public void setUpdates(List<UpdateResource> updates) {
        this.updates = updates;
    }

    public List<RewardResource> getRewards() {
        return rewards;
    }

    public void setRewards(List<RewardResource> rewards) {
        this.rewards = rewards;
    }

    public ProjectType getType() {
        return type;
    }

    public void setType(ProjectType type) {
        this.type = type;
    }

    public ProjectVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(ProjectVisibility visibility) {
        this.visibility = visibility;
    }
}
