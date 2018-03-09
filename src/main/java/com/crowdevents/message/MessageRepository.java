package com.crowdevents.message;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface MessageRepository extends PagingAndSortingRepository<Message, UUID> {
}
