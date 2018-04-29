package com.crowdevents.comment;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {
    Page<Comment> findAllByAuthorId(Long authorId, Pageable pageable);

    Page<Comment> findAllByProjectId(Long projectId, Pageable pageable);
}
