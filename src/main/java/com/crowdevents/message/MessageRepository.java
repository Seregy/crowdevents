package com.crowdevents.message;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MessageRepository extends PagingAndSortingRepository<Message, Long> {
    Page<Message> findAllBySenderId(Long senderId, Pageable pageable);

    Page<Message> findAllByReceiverId(Long receiverId, Pageable pageable);

    Page<Message> findDistinctBySenderIdOrReceiverId(Long senderId,
                                                     Long receiverId, Pageable pageable);
}
