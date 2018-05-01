package com.crowdevents.notification;

import com.crowdevents.core.web.Views;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

@JsonView(Views.Detailed.class)
public class PersonNotificationResource extends BaseNotificationResource {
    @JsonIgnoreProperties({"password", "email", "country", "city"})
    @JsonView(Views.Minimal.class)
    private PersonResource person;

    public PersonResource getPerson() {
        return person;
    }

    public void setPerson(PersonResource person) {
        this.person = person;
    }

    @Override
    public NotificationType getNotificationType() {
        return NotificationType.PERSON;
    }
}
