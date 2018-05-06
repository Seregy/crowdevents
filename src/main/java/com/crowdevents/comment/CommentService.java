package com.crowdevents.comment;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    Comment post(Long projectId, Long personId, String message);

    Optional<Comment> get(Long id);

    Page<Comment> getAll(Pageable pageable);

    Page<Comment> getAllByProject(Long projectId, Pageable pageable);

    Page<Comment> getAllByPerson(Long personId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Comment updatedComment);
}
