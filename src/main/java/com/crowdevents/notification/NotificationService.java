package com.crowdevents.notification;

import java.time.LocalDateTime;
import java.util.Optional;


public interface NotificationService {
    BaseNotification sendBaseNotification(String message, Long receiverId, Long projectId);

    ContributionNotification sendContributionNotification(String message, Long contributionId,
                                                          Long receiverId, Long projectId);

    PersonNotification sendPersonNotification(String message, Long personId, Long receiverId,
                                              Long projectId);

    UpdateNotification sendUpdateNotification(String message, Long updateId, Long receiverId,
                                              Long projectId);

    Optional<BaseNotification> get(Long id);

    void delete(Long id);

    void changeMessage(Long id, String newMessage);
}
