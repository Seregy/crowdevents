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
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;

@Entity
public class Project {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String name;

    @Column(name = "short_description",
            nullable = false, length = 140)
    private String shortDescription;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    private String description;

    private Location location;

    @Column(name = "start_date_time")
    private LocalDateTime startDateTime;

    @Column(name = "end_date_time")
    private LocalDateTime endDateTime;

    @Column(name = "event_date_time")
    private LocalDateTime eventDateTime;

    @Columns(columns = { @Column(name = "funding_goal_currency"),
            @Column(name = "funding_goal_amount") })
    @Type(type = "org.jadira.usertype.moneyandcurrency.joda.PersistentBigMoneyAmountAndCurrency")
    private BigMoney fundingGoal;

    @Column(name = "image_link")
    private String projectImageLink;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectType type;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ProjectVisibility visibility;

    @ElementCollection
    private List<String> videoLinks = new ArrayList<>();

    @ElementCollection
    private List<String> imageLinks = new ArrayList<>();

    @ManyToOne
    private Person owner;

    @ManyToMany(mappedBy = "teamMemberProjects", cascade =
            {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Person> teamMembers = new HashSet<>();

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

    @Column(name = "payment_account_id")
    private String paymentAccountId;

    /**
     * Constructs new project.
     *
     * @param name name of the project
     * @param shortDescription short description
     * @param fundingGoal funding goal of the project
     * @param owner owner of the project
     */
    public Project(String name, String shortDescription, Money fundingGoal, Person owner) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.fundingGoal = fundingGoal.toBigMoney();
        owner.addCreatedProject(this);
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

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
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

    public LocalDateTime getEventDateTime() {
        return eventDateTime;
    }

    public void setEventDateTime(LocalDateTime eventDateTime) {
        this.eventDateTime = eventDateTime;
    }

    /**
     * Returns project's funding goal.
     *
     * @return funding goal if it exists, null otherwise
     */
    public Money getFundingGoal() {
        if (fundingGoal != null) {
            return fundingGoal.toMoney();
        }
        
        return null;
    }

    public void setFundingGoal(Money fundingGoal) {
        this.fundingGoal = fundingGoal.toBigMoney();
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

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public Set<Person> getTeamMembers() {
        return teamMembers;
    }

    public void addTeamMember(Person owner) {
        teamMembers.add(owner);
        owner.getTeamMemberProjects().add(this);
    }

    public void removeTeamMember(Person owner) {
        teamMembers.remove(owner);
        owner.getTeamMemberProjects().remove(this);
    }

    public void setTeamMembers(Set<Person> teamMembers) {
        this.teamMembers = teamMembers;
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

    /**
     * Returns money already raised from contributors.
     *
     * @return raised money
     */
    public Money getRaisedMoney() {
        Money[] contributed = contributions.stream()
                .map(Contribution::getMoney)
                .toArray(Money[]::new);
        if (contributed.length > 0) {
            return Money.total(contributed);
        } else {
            return Money.zero(Optional.ofNullable(fundingGoal)
                    .map(BigMoney::getCurrencyUnit)
                    .orElse(CurrencyUnit.USD));
        }
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

    public String getPaymentAccountId() {
        return paymentAccountId;
    }

    public void setPaymentAccountId(String paymentAccountId) {
        this.paymentAccountId = paymentAccountId;
    }
}
