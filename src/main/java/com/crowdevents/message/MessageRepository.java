package com.crowdevents.message;

import java.util.UUID;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, UUID> {
}
