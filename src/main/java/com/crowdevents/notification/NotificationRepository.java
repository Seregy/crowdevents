package com.crowdevents.notification;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface NotificationRepository extends PagingAndSortingRepository<BaseNotification, UUID> {
}
