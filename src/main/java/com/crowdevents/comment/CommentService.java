package com.crowdevents.comment;

import java.util.Optional;

public interface CommentService {
    Comment post(Long projectId, Long personId, String message);

    Optional<Comment> get(Long id);

    boolean delete(Long id);

    void changeMessage(Long id, String newMessage);

    void update(Long id, Comment updatedComment);
}
