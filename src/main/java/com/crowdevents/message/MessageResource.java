package com.crowdevents.message;

import com.crowdevents.core.web.Views;
import com.crowdevents.person.PersonResource;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import java.time.LocalDateTime;

@JsonView(Views.Detailed.class)
public class MessageResource {
    @JsonView(Views.Minimal.class)
    private Long id;

    @JsonIgnoreProperties({"password", "email", "country", "city"})
    @JsonView(Views.Minimal.class)
    private PersonResource sender;

    @JsonIgnoreProperties({"password", "email", "country", "city"})
    @JsonView(Views.Minimal.class)
    private PersonResource receiver;

    private String message;

    @JsonView(Views.Minimal.class)
    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PersonResource getSender() {
        return sender;
    }

    public void setSender(PersonResource sender) {
        this.sender = sender;
    }

    public PersonResource getReceiver() {
        return receiver;
    }

    public void setReceiver(PersonResource receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
