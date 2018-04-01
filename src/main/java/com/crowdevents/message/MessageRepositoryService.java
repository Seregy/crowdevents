package com.crowdevents.message;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
@Transactional
public class MessageRepositoryService implements MessageService {
    private MessageRepository messageRepository;
    private PersonRepository personRepository;
    private Clock clock;

    /**
     * Creates new message service that uses repositories.
     *
     * @param messageRepository message repository
     * @param personRepository person repository
     * @param clock clock for generating date and time
     */
    @Autowired
    public MessageRepositoryService(MessageRepository messageRepository,
                                    PersonRepository personRepository,
                                    Clock clock) {
        this.messageRepository = messageRepository;
        this.personRepository = personRepository;
        this.clock = clock;
    }

    @Override
    public Message send(UUID senderId, UUID receiverId, String message) {
        Person sender = personRepository
                .findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid person id: " + senderId));
        Person receiver = personRepository
                .findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid person id: " + receiverId));
        Message newMessage = new Message(message, sender, receiver, LocalDateTime.now(clock));

        sender.addCreatedMessage(newMessage);
        receiver.addReceivedMessage(newMessage);
        return messageRepository.save(newMessage);
    }

    @Override
    public Optional<Message> get(UUID id) {
        return messageRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        messageRepository.deleteById(id);
    }

    @Override
    public void changeMessage(UUID id, String newMessage) {
        Message message = messageRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid message id: " + id));
        message.setMessage(newMessage);
        messageRepository.save(message);
    }
}
