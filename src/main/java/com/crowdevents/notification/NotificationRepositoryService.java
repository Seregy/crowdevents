package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.contribution.ContributionRepository;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.update.Update;
import com.crowdevents.update.UpdateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class NotificationRepositoryService implements NotificationService {
    private NotificationRepository notificationRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private ContributionRepository contributionRepository;
    private UpdateRepository updateRepository;
    private Clock clock;

    @Autowired
    public NotificationRepositoryService(NotificationRepository notificationRepository,
                                         PersonRepository personRepository,
                                         ProjectRepository projectRepository,
                                         ContributionRepository contributionRepository,
                                         UpdateRepository updateRepository,
                                         Clock clock) {
        this.notificationRepository = notificationRepository;
        this.personRepository = personRepository;
        this.projectRepository = projectRepository;
        this.contributionRepository = contributionRepository;
        this.updateRepository = updateRepository;
        this.clock = clock;
    }

    @Override
    public BaseNotification sendBaseNotification(String message, UUID receiverId, UUID projectId) {
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        BaseNotification baseNotification = new BaseNotification(message, receiver, LocalDateTime.now(clock), project);
        return notificationRepository.save(baseNotification);
    }

    @Override
    public ContributionNotification sendContributionNotification(String message, UUID contributionId,
                                                                 UUID receiverId, UUID projectId) {
        Contribution contribution = contributionRepository
                .findById(contributionId)
                .orElseThrow(() -> new RuntimeException("Invalid contribution id: " + contributionId));
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        ContributionNotification contributionNotification = new ContributionNotification(message, contribution,
                receiver, LocalDateTime.now(clock), project);
        return notificationRepository.save(contributionNotification);
    }

    @Override
    public PersonNotification sendPersonNotification(String message, UUID personId, UUID receiverId, UUID projectId) {
        Person person = getPerson(personId);
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        PersonNotification personNotification = new PersonNotification(message, person, receiver,
                LocalDateTime.now(clock), project);
        return notificationRepository.save(personNotification);
    }

    @Override
    public UpdateNotification sendUpdateNotification(String message, UUID updateId, UUID receiverId, UUID projectId) {
        Update update = updateRepository
                .findById(updateId)
                .orElseThrow(() -> new RuntimeException("Invalid update id: " + updateId));
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        UpdateNotification updateNotification = new UpdateNotification(message, update, receiver,
                LocalDateTime.now(clock), project);
        return notificationRepository.save(updateNotification);
    }

    @Override
    public Optional<BaseNotification> get(UUID id) {
        return notificationRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        notificationRepository.deleteById(id);
    }

    @Override
    public void changeMessage(UUID id, String newMessage) {
        BaseNotification notification = notificationRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid notification id: " + id));
        notification.setMessage(newMessage);
        notificationRepository.save(notification);
    }

    private Person getPerson(UUID id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid person id: " + id));
    }

    private Project getProject(UUID id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Invalid project id: " + id));
    }
}
