package com.crowdevents.notification;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends PagingAndSortingRepository<BaseNotification, Long> {
}
