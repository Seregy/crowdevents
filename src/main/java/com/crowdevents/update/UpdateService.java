package com.crowdevents.update;

import java.time.LocalDateTime;
import java.util.UUID;

public interface UpdateService {
    Update post(UUID projectId, LocalDateTime dateTime, String message);
    Update post(UUID projectId, LocalDateTime dateTime, String message, String shortMessage);
    Update get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
    void changeShortMessage(UUID id, String newShortMessage);
}
