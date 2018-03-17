package com.crowdevents.comment;

import java.time.LocalDateTime;
import java.util.UUID;

public interface CommentService {
    Comment post(UUID projectId, UUID personId, String message);
    Comment get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
}
