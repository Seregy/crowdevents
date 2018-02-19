package com.crowdevents.notification;

import com.crowdevents.project.Project;
import com.crowdevents.user.User;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class UserNotification extends BaseNotification {
    @ManyToOne
    private User user;

    public UserNotification(String message, User user, User receiver, LocalDateTime dateTime, Project project) {
        super(message, receiver, dateTime, project);
        this.user = user;
    }

    protected UserNotification() {
        super();
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
