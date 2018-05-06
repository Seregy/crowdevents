package com.crowdevents.message;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Message send(Long senderId, Long receiverId, String message) {
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
    public Optional<Message> get(Long id) {
        return messageRepository.findById(id);
    }

    @Override
    public Page<Message> getAll(Pageable pageable) {
        return messageRepository.findAll(pageable);
    }

    @Override
    public Page<Message> getAllBySender(Long senderId, Pageable pageable) {
        return messageRepository.findAllBySenderId(senderId, pageable);
    }

    @Override
    public Page<Message> getAllByReceiver(Long receiverId, Pageable pageable) {
        return messageRepository.findAllByReceiverId(receiverId, pageable);
    }

    @Override
    public Page<Message> getAllByPerson(Long personId, Pageable pageable) {
        return messageRepository.findDistinctBySenderIdOrReceiverId(personId, personId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (messageRepository.existsById(id)) {
            messageRepository.deleteById(id);
        }

        return !messageRepository.existsById(id);
    }

    @Override
    public void update(Long id, Message updatedMessage) {
        if (updatedMessage == null) {
            throw new IllegalArgumentException("Updated message must not be null");
        }

        Message message = getMessage(id);
        message.setMessage(updatedMessage.getMessage());
        messageRepository.save(message);
    }

    private Message getMessage(Long id) {
        return messageRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid message id: " + id));
    }
}
