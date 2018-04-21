package com.crowdevents.project;

import com.crowdevents.location.Location;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class ProjectResource {
    private Long id;
    private String name;
    private String description;
    private Location location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Money fundingGoal;

    private List<String> videoLinks = new ArrayList<>();
    private List<String> imageLinks = new ArrayList<>();

    private List<Long> owners = new ArrayList<>();
    private List<Long> subscribers = new ArrayList<>();
    private List<Long> contributions = new ArrayList<>();
    private List<Long> comments = new ArrayList<>();
    private List<Long> faqs = new ArrayList<>();
    private List<Long> categories = new ArrayList<>();
    private List<Long> updates = new ArrayList<>();
    private List<Long> rewards = new ArrayList<>();

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
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

    public Money getFundingGoal() {
        return fundingGoal;
    }

    public void setFundingGoal(Money fundingGoal) {
        this.fundingGoal = fundingGoal;
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

    public List<Long> getOwners() {
        return owners;
    }

    public void setOwners(List<Long> owners) {
        this.owners = owners;
    }

    public List<Long> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Long> subscribers) {
        this.subscribers = subscribers;
    }

    public List<Long> getContributions() {
        return contributions;
    }

    public void setContributions(List<Long> contributions) {
        this.contributions = contributions;
    }

    public List<Long> getComments() {
        return comments;
    }

    public void setComments(List<Long> comments) {
        this.comments = comments;
    }

    public List<Long> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<Long> faqs) {
        this.faqs = faqs;
    }

    public List<Long> getCategories() {
        return categories;
    }

    public void setCategories(List<Long> categories) {
        this.categories = categories;
    }

    public List<Long> getUpdates() {
        return updates;
    }

    public void setUpdates(List<Long> updates) {
        this.updates = updates;
    }

    public List<Long> getRewards() {
        return rewards;
    }

    public void setRewards(List<Long> rewards) {
        this.rewards = rewards;
    }
}
