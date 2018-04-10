package com.crowdevents.project;

import com.crowdevents.location.Location;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProjectResource {
    private UUID id;
    private String name;
    private String description;
    private Location location;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private Money fundingGoal;

    private List<String> videoLinks = new ArrayList<>();
    private List<String> imageLinks = new ArrayList<>();

    private List<UUID> owners = new ArrayList<>();
    private List<UUID> subscribers = new ArrayList<>();
    private List<UUID> contributions = new ArrayList<>();
    private List<UUID> comments = new ArrayList<>();
    private List<UUID> faqs = new ArrayList<>();
    private List<UUID> categories = new ArrayList<>();
    private List<UUID> updates = new ArrayList<>();
    private List<UUID> rewards = new ArrayList<>();

    public ProjectResource() {

    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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

    public List<UUID> getOwners() {
        return owners;
    }

    public void setOwners(List<UUID> owners) {
        this.owners = owners;
    }

    public List<UUID> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<UUID> subscribers) {
        this.subscribers = subscribers;
    }

    public List<UUID> getContributions() {
        return contributions;
    }

    public void setContributions(List<UUID> contributions) {
        this.contributions = contributions;
    }

    public List<UUID> getComments() {
        return comments;
    }

    public void setComments(List<UUID> comments) {
        this.comments = comments;
    }

    public List<UUID> getFaqs() {
        return faqs;
    }

    public void setFaqs(List<UUID> faqs) {
        this.faqs = faqs;
    }

    public List<UUID> getCategories() {
        return categories;
    }

    public void setCategories(List<UUID> categories) {
        this.categories = categories;
    }

    public List<UUID> getUpdates() {
        return updates;
    }

    public void setUpdates(List<UUID> updates) {
        this.updates = updates;
    }

    public List<UUID> getRewards() {
        return rewards;
    }

    public void setRewards(List<UUID> rewards) {
        this.rewards = rewards;
    }
}
