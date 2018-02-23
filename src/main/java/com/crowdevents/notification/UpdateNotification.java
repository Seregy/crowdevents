package com.crowdevents.notification;

import com.crowdevents.project.Project;
import com.crowdevents.update.Update;
import com.crowdevents.person.Person;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
public class UpdateNotification extends BaseNotification {
    @ManyToOne
    private Update update;

    public UpdateNotification(String message, Update update, Person receiver, LocalDateTime dateTime, Project project) {
        super(message, receiver, dateTime, project);
        this.update = update;
    }

    protected UpdateNotification() {
        super();
    }

    public Update getUpdate() {
        return update;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }
}
