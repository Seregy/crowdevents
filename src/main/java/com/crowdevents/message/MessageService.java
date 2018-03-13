package com.crowdevents.message;

import java.util.UUID;

public interface MessageService {
    Message send(UUID senderId, UUID receiverId, String message);
    Message get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
}
