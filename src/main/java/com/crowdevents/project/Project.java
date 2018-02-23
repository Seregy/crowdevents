package com.crowdevents.project;

import com.crowdevents.category.Category;
import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.faq.Faq;
import com.crowdevents.location.Location;
import com.crowdevents.reward.Reward;
import com.crowdevents.update.Update;
import com.crowdevents.person.Person;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
public class Project {
    @Id
    @Column(unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    private Location location;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Columns(columns = { @Column(name = "funding_goal_currency"), @Column(name = "funding_goal_amount") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money fundingGoal;

    @ElementCollection
    private List<String> videoLinks = new ArrayList<>();

    @ElementCollection
    private List<String> imageLinks = new ArrayList<>();

    @ManyToMany(mappedBy = "createdProjects")
    private Set<Person> owners;

    @ManyToMany(mappedBy = "subscribedProjects")
    private Set<Person> subscribers;

    @OneToMany(mappedBy = "project")
    private Set<Contribution> contributions;

    @OneToMany(mappedBy = "project")
    private Set<Comment> comments;

    @OneToMany(mappedBy = "project")
    private Set<Faq> faqs;

    @ManyToMany(mappedBy = "projects")
    private Set<Category> categories;

    @OneToMany(mappedBy = "project")
    private Set<Update> updates;

    @OneToMany(mappedBy = "project")
    private Set<Reward> rewards;

    public Project(String name, String description, Money fundingGoal, Person... owners) {
        this.name = name;
        this.description = description;
        this.fundingGoal = fundingGoal;
        Collections.addAll(this.owners, owners);
        this.id = UUID.randomUUID();
    }

    protected Project() {

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

    public Set<Person> getOwners() {
        return owners;
    }

    public void setOwners(Set<Person> owners) {
        this.owners = owners;
    }

    public Set<Person> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<Person> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Faq> getFaqs() {
        return faqs;
    }

    public void setFaqs(Set<Faq> faqs) {
        this.faqs = faqs;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Update> getUpdates() {
        return updates;
    }

    public void setUpdates(Set<Update> updates) {
        this.updates = updates;
    }

    public Set<Reward> getRewards() {
        return rewards;
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Project)) {
            return false;
        }

        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
