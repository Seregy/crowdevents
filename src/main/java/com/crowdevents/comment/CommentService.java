package com.crowdevents.comment;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface CommentService {
    Comment post(UUID projectId, UUID personId, String message);
    Optional<Comment> get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
}
