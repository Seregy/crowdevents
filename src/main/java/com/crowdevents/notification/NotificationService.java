package com.crowdevents.notification;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface NotificationService {
    BaseNotification sendBaseNotification(String message, Long receiverId, Long projectId);

    ContributionNotification sendContributionNotification(String message, Long contributionId,
                                                          Long receiverId, Long projectId);

    PersonNotification sendPersonNotification(String message, Long personId, Long receiverId,
                                              Long projectId);

    UpdateNotification sendUpdateNotification(String message, Long updateId, Long receiverId,
                                              Long projectId);

    Optional<BaseNotification> get(Long id);

    Page<BaseNotification> getAll(Pageable pageable);

    Page<BaseNotification> getAllByPerson(Long personId, Pageable pageable);

    boolean delete(Long id);

    void changeMessage(Long id, String newMessage);

    void update(Long id, BaseNotification updatedNotification);
}
