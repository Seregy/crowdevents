package com.crowdevents.person;

import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.message.Message;
import com.crowdevents.notification.BaseNotification;
import com.crowdevents.project.Project;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Entity
public class Person {
    @Id
    @Column(unique = true)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    private String country;
    private String city;

    @ManyToMany
    private Set<Project> createdProjects;

    @ManyToMany
    private Set<Project> subscribedProjects;

    @OneToMany(mappedBy = "contributor")
    private Set<Contribution> contributions;

    @OneToMany(mappedBy = "receiver")
    private Set<BaseNotification> notifications;

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments;

    @ManyToMany
    private Set<Person> followers;
    @ManyToMany(mappedBy = "followers")
    private Set<Person> followed;

    @OneToMany(mappedBy = "sender")
    private Set<Message> createdMessages;

    @OneToMany(mappedBy = "receiver")
    private Set<Message> receivedMessages;

    public Person(String email, String password, String name, String surname) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.id = UUID.randomUUID();
    }

    protected Person() {

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

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Set<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void setCreatedProjects(Set<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public Set<Project> getSubscribedProjects() {
        return subscribedProjects;
    }

    public void setSubscribedProjects(Set<Project> subscribedProjects) {
        this.subscribedProjects = subscribedProjects;
    }

    public Set<Contribution> getContributions() {
        return contributions;
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<BaseNotification> getNotifications() {
        return notifications;
    }

    public void setNotifications(Set<BaseNotification> notifications) {
        this.notifications = notifications;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Person> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<Person> followers) {
        this.followers = followers;
    }

    public Set<Person> getFollowed() {
        return followed;
    }

    public void setFollowed(Set<Person> followed) {
        this.followed = followed;
    }

    public Set<Message> getCreatedMessages() {
        return createdMessages;
    }

    public void setCreatedMessages(Set<Message> createdMessages) {
        this.createdMessages = createdMessages;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Person)) {
            return false;
        }

        Person person = (Person) o;
        return Objects.equals(id, person.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
