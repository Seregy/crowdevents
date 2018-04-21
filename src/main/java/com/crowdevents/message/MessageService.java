package com.crowdevents.message;

import java.util.Optional;

public interface MessageService {
    Message send(Long senderId, Long receiverId, String message);

    Optional<Message> get(Long id);

    void delete(Long id);

    void changeMessage(Long id, String newMessage);
}
