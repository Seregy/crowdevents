package com.crowdevents.update;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface UpdateService {
    Update post(UUID projectId, String message);

    Optional<Update> get(UUID id);

    void delete(UUID id);

    void changeMessage(UUID id, String newMessage);

    void changeShortMessage(UUID id, String newShortMessage);
}
