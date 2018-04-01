package com.crowdevents.notification;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface NotificationRepository extends PagingAndSortingRepository<BaseNotification, UUID> {
}
