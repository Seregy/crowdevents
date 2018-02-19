package com.crowdevents.project;

import com.crowdevents.category.Category;
import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.faq.Faq;
import com.crowdevents.reward.Reward;
import com.crowdevents.update.Update;
import com.crowdevents.user.User;

import javax.persistence.*;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Project {
    @Id
    @Column(unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String description;

    @ManyToMany(mappedBy = "createdProjects")
    private Set<User> owners;

    @ManyToMany(mappedBy = "subscribedProjects")
    private Set<User> subscribers;

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

    public Project(String name, String description, User... owners) {
        this.name = name;
        this.description = description;
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

    public Set<User> getOwners() {
        return owners;
    }

    public void setOwners(Set<User> owners) {
        this.owners = owners;
    }

    public Set<User> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Set<User> subscribers) {
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
