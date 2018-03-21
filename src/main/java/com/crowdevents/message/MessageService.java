package com.crowdevents.message;

import java.util.Optional;
import java.util.UUID;

public interface MessageService {
    Message send(UUID senderId, UUID receiverId, String message);
    Optional<Message> get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
}
