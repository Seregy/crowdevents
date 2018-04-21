package com.crowdevents.project;

import com.crowdevents.category.Category;
import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.faq.Faq;
import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import com.crowdevents.reward.Reward;
import com.crowdevents.update.Update;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.money.Money;


@Entity
public class Project {
    @Id
    @Column(unique = true)
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Lob
    @Column(nullable = false)
    private String description;

    private Location location;

    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    @Columns(columns = { @Column(name = "funding_goal_currency"),
            @Column(name = "funding_goal_amount") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentMoneyAmountAndCurrency")
    private Money fundingGoal;

    @ElementCollection
    private List<String> videoLinks = new ArrayList<>();

    @ElementCollection
    private List<String> imageLinks = new ArrayList<>();

    @ManyToMany(mappedBy = "createdProjects", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Person> owners = new HashSet<>();

    @ManyToMany(mappedBy = "subscribedProjects")
    private Set<Person> subscribers = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Contribution> contributions = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Faq> faqs = new HashSet<>();

    @ManyToMany(mappedBy = "projects")
    private Set<Category> categories = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Update> updates = new HashSet<>();

    @OneToMany(mappedBy = "project")
    private Set<Reward> rewards = new HashSet<>();

    /**
     * Constructs new project.
     *
     * @param name name of the project
     * @param description description
     * @param fundingGoal funding goal of the project
     * @param owners owners of the project
     */
    public Project(String name, String description, Money fundingGoal, Person... owners) {
        this.name = name;
        this.description = description;
        this.fundingGoal = fundingGoal;
        for(Person owner : owners) {
            addOwner(owner);
        }
    }

    protected Project() {

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

    public void addVideoLink(String link) {
        videoLinks.add(link);
    }

    public void removeVideoLink(String link) {
        videoLinks.remove(link);
    }

    public void setVideoLinks(List<String> videoLinks) {
        this.videoLinks = videoLinks;
    }

    public List<String> getImageLinks() {
        return imageLinks;
    }

    public void addImageLink(String link) {
        imageLinks.add(link);
    }

    public void removeImageLink(String link) {
        imageLinks.remove(link);
    }

    public void setImageLinks(List<String> imageLinks) {
        this.imageLinks = imageLinks;
    }

    public Set<Person> getOwners() {
        return owners;
    }

    public void addOwner(Person owner) {
        owners.add(owner);
        owner.getCreatedProjects().add(this);
    }

    public void removeOwner(Person owner) {
        owners.remove(owner);
        owner.getCreatedProjects().remove(this);
    }

    public void setOwners(Set<Person> owners) {
        this.owners = owners;
    }

    public Set<Person> getSubscribers() {
        return subscribers;
    }

    public void addSubscriber(Person subscriber) {
        subscribers.add(subscriber);
        subscriber.getSubscribedProjects().add(this);
    }

    public void removeSubscriber(Person subscriber) {
        subscribers.remove(subscriber);
        subscriber.getSubscribedProjects().remove(this);
    }

    public void setSubscribers(Set<Person> subscribers) {
        this.subscribers = subscribers;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
        contribution.setProject(this);
    }

    public void removeContribution(Contribution contribution) {
        contributions.remove(contribution);
        contribution.setProject(null);
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setProject(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setProject(null);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Faq> getFaqs() {
        return faqs;
    }

    public void addFaq(Faq faq) {
        faqs.add(faq);
        faq.setProject(this);
    }

    public void removeFaq(Faq faq) {
        faqs.remove(faq);
        faq.setProject(null);
    }

    public void setFaqs(Set<Faq> faqs) {
        this.faqs = faqs;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addCategory(Category category) {
        categories.add(category);
        category.getProjects().add(this);
    }

    public void removeCategory(Category category) {
        categories.remove(category);
        category.getProjects().remove(this);
    }

    public void setCategories(Set<Category> categories) {
        this.categories = categories;
    }

    public Set<Update> getUpdates() {
        return updates;
    }

    public void addUpdate(Update update) {
        updates.add(update);
        update.setProject(this);
    }

    public void removeUpdate(Update update) {
        updates.remove(update);
        update.setProject(null);
    }

    public void setUpdates(Set<Update> updates) {
        this.updates = updates;
    }

    public Set<Reward> getRewards() {
        return rewards;
    }

    public void addReward(Reward reward) {
        rewards.add(reward);
        reward.setProject(this);
    }

    public void removeReward(Reward reward) {
        rewards.remove(reward);
        reward.setProject(null);
    }

    public void setRewards(Set<Reward> rewards) {
        this.rewards = rewards;
    }
}
