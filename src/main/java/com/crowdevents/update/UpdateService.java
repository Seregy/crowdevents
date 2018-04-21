package com.crowdevents.update;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UpdateService {
    Update post(Long projectId, String message);

    Optional<Update> get(Long id);

    void delete(Long id);

    void changeMessage(Long id, String newMessage);

    void changeShortMessage(Long id, String newShortMessage);
}
