package com.crowdevents.message;

import com.crowdevents.person.Person;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Type;

@Entity
public class Message {
    @Id
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Person sender;

    @ManyToOne
    private Person receiver;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(nullable = false)
    private String message;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    /**
     * Constructs new personal message.
     *
     * @param message text of the message
     * @param sender person who sent the message
     * @param receiver person who will receive the message
     * @param dateTime creation date and time
     */
    public Message(String message, Person sender, Person receiver, LocalDateTime dateTime) {
        this.message = message;
        this.sender = sender;
        this.receiver = receiver;
        this.dateTime = dateTime;
    }

    protected Message() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Person getSender() {
        return sender;
    }

    public void setSender(Person sender) {
        this.sender = sender;
    }

    public Person getReceiver() {
        return receiver;
    }

    public void setReceiver(Person receiver) {
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
