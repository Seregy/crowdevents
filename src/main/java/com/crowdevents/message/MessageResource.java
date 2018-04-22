package com.crowdevents.message;

import com.crowdevents.person.PersonResource;

import java.time.LocalDateTime;

public class MessageResource {
    private Long id;
    private PersonResource sender;
    private PersonResource receiver;
    private String message;
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
