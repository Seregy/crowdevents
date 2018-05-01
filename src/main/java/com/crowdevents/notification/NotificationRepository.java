package com.crowdevents.notification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends PagingAndSortingRepository<BaseNotification, Long> {
    Page<BaseNotification> findAllByReceiverId(Long receiverId, Pageable pageable);
}
