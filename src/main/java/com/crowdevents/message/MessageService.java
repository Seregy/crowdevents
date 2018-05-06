package com.crowdevents.message;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MessageService {
    Message send(Long senderId, Long receiverId, String message);

    Optional<Message> get(Long id);

    Page<Message> getAll(Pageable pageable);

    Page<Message> getAllBySender(Long senderId, Pageable pageable);

    Page<Message> getAllByReceiver(Long receiverId, Pageable pageable);

    Page<Message> getAllByPerson(Long personId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Message updatedMessage);
}
