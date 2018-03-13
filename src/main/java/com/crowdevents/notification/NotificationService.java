package com.crowdevents.notification;

import java.time.LocalDateTime;
import java.util.UUID;

public interface NotificationService {
    BaseNotification sendBaseNotification(String message, UUID receiverId,
                                         LocalDateTime dateTime, UUID projectId);
    ContributionNotification sendContributionNotification(String message, UUID contributionId, UUID receiverId,
                                         LocalDateTime dateTime, UUID projectId);
    ContributionNotification sendPersonNotification(String message, UUID personId, UUID receiverId,
                                                         LocalDateTime dateTime, UUID projectId);
    ContributionNotification sendUpdateNotification(String message, UUID updateId, UUID receiverId,
                                                    LocalDateTime dateTime, UUID projectId);
    BaseNotification get(UUID id);
    void delete(UUID id);
    void changeMessage(UUID id, String newMessage);
}
