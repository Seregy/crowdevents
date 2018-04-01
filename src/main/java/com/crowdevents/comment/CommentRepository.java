package com.crowdevents.comment;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CommentRepository extends PagingAndSortingRepository<Comment, UUID> {
}
