package com.crowdevents.notification;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class PersonNotification extends BaseNotification {
    @ManyToOne
    private Person person;

    public PersonNotification(String message, Person person, Person receiver,
                              LocalDateTime dateTime, Project project) {
        super(message, receiver, dateTime, project);
        this.person = person;
    }

    protected PersonNotification() {
        super();
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
