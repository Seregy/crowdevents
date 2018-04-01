package com.crowdevents.notification;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface NotificationService {
    BaseNotification sendBaseNotification(String message, UUID receiverId, UUID projectId);

    ContributionNotification sendContributionNotification(String message, UUID contributionId,
                                                          UUID receiverId, UUID projectId);

    PersonNotification sendPersonNotification(String message, UUID personId, UUID receiverId,
                                              UUID projectId);

    UpdateNotification sendUpdateNotification(String message, UUID updateId, UUID receiverId,
                                              UUID projectId);

    Optional<BaseNotification> get(UUID id);

    void delete(UUID id);

    void changeMessage(UUID id, String newMessage);
}
