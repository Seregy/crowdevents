package com.crowdevents.message;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class MessageRepositoryService implements MessageService {
    private MessageRepository messageRepository;
    private PersonRepository personRepository;
    private Clock clock;

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
                .orElseThrow(() -> new RuntimeException("Invalid person id: " + senderId));
        Person receiver = personRepository
                .findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Invalid person id: " + receiverId));
        Message newMessage = new Message(message, sender, receiver, LocalDateTime.now(clock));

        sender.getCreatedMessages().add(newMessage);
        receiver.getReceivedMessages().add(newMessage);
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
                .orElseThrow(() -> new RuntimeException("Invalid message id: " + id));
        message.setMessage(newMessage);
        messageRepository.save(message);
    }
}
