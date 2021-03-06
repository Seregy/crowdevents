package com.crowdevents.person;

import com.crowdevents.comment.Comment;
import com.crowdevents.contribution.Contribution;
import com.crowdevents.message.Message;
import com.crowdevents.notification.BaseNotification;
import com.crowdevents.project.Project;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Person {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 64)
    private String name;

    @Column(length = 64)
    private String surname;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @Column(length = 64)
    private String country;

    @Column(length = 64)
    private String city;

    @Column(name = "image_link")
    private String personImageLink;

    @OneToMany(mappedBy = "owner")
    private Set<Project> createdProjects = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "person_project_team",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> teamMemberProjects = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "person_project_subscription",
            joinColumns = @JoinColumn(name = "person_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"))
    private Set<Project> subscribedProjects = new HashSet<>();

    @OneToMany(mappedBy = "contributor")
    private Set<Contribution> contributions = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<BaseNotification> notifications = new HashSet<>();

    @OneToMany(mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "person_follower",
            joinColumns = @JoinColumn(name = "follower_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "followed_id", referencedColumnName = "id"))
    private Set<Person> followers = new HashSet<>();

    @ManyToMany(mappedBy = "followers")
    private Set<Person> followed = new HashSet<>();

    @OneToMany(mappedBy = "sender")
    private Set<Message> createdMessages = new HashSet<>();

    @OneToMany(mappedBy = "receiver")
    private Set<Message> receivedMessages = new HashSet<>();

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PersonRole role;

    /**
     * Constructs new person that represents user of the system.
     *
     * @param email email address
     * @param password password
     * @param name username
     */
    public Person(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.role = PersonRole.USER;
    }

    protected Person() {

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

    public String getPersonImageLink() {
        return personImageLink;
    }

    public void setPersonImageLink(String personImageLink) {
        this.personImageLink = personImageLink;
    }

    public Set<Project> getCreatedProjects() {
        return createdProjects;
    }

    public void addCreatedProject(Project project) {
        createdProjects.add(project);
        project.setOwner(this);
    }

    public void removeCreatedProject(Project project) {
        createdProjects.remove(project);
        project.setOwner(null);
    }

    public void setCreatedProjects(Set<Project> createdProjects) {
        this.createdProjects = createdProjects;
    }

    public Set<Project> getTeamMemberProjects() {
        return teamMemberProjects;
    }

    public void setTeamMemberProjects(Set<Project> teamMemberProjects) {
        this.teamMemberProjects = teamMemberProjects;
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

    public void addContribution(Contribution contribution) {
        contributions.add(contribution);
        contribution.setContributor(this);
    }

    public void removeContribution(Contribution contribution) {
        contributions.remove(contribution);
        contribution.setContributor(null);
    }

    public void setContributions(Set<Contribution> contributions) {
        this.contributions = contributions;
    }

    public Set<BaseNotification> getNotifications() {
        return notifications;
    }

    public void addNotification(BaseNotification notification) {
        notifications.add(notification);
        notification.setReceiver(this);
    }

    public void removeNotification(BaseNotification notification) {
        notifications.remove(notification);
        notification.setReceiver(null);
    }

    public void setNotifications(Set<BaseNotification> notifications) {
        this.notifications = notifications;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
        comment.setAuthor(this);
    }

    public void removeComment(Comment comment) {
        comments.remove(comment);
        comment.setAuthor(null);
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Person> getFollowers() {
        return followers;
    }

    public void addFollower(Person follower) {
        followers.add(follower);
        follower.addFollowed(this);
    }

    public void removeFollower(Person follower) {
        followers.remove(follower);
        follower.removeFollowed(this);
    }

    public void setFollowers(Set<Person> followers) {
        this.followers = followers;
    }

    public Set<Person> getFollowed() {
        return followed;
    }

    public void addFollowed(Person follow) {
        followed.add(follow);
        follow.addFollower(this);
    }

    public void removeFollowed(Person follow) {
        followed.add(follow);
        follow.removeFollower(this);
    }

    public void setFollowed(Set<Person> followed) {
        this.followed = followed;
    }

    public Set<Message> getCreatedMessages() {
        return createdMessages;
    }

    public void addCreatedMessage(Message message) {
        createdMessages.add(message);
        message.setSender(this);
    }

    public void removeCreatedMessage(Message message) {
        createdMessages.remove(message);
        message.setSender(null);
    }

    public void setCreatedMessages(Set<Message> createdMessages) {
        this.createdMessages = createdMessages;
    }

    public Set<Message> getReceivedMessages() {
        return receivedMessages;
    }

    public void addReceivedMessage(Message message) {
        receivedMessages.add(message);
        message.setReceiver(this);
    }

    public void removeReceivedMessage(Message message) {
        receivedMessages.remove(message);
        message.setReceiver(null);
    }

    public void setReceivedMessages(Set<Message> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public PersonRole getRole() {
        return role;
    }

    public void setRole(PersonRole role) {
        this.role = role;
    }
}
