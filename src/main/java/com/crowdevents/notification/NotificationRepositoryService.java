package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.contribution.ContributionRepository;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.update.Update;
import com.crowdevents.update.UpdateRepository;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class NotificationRepositoryService implements NotificationService {
    private NotificationRepository notificationRepository;
    private PersonRepository personRepository;
    private ProjectRepository projectRepository;
    private ContributionRepository contributionRepository;
    private UpdateRepository updateRepository;
    private Clock clock;

    /**
     * Creates new notification service that uses repositories.
     *
     * @param notificationRepository notification repository
     * @param personRepository person repository
     * @param projectRepository project repository
     * @param contributionRepository contribution repository
     * @param updateRepository update repository
     * @param clock clock for generating date and time
     */
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
    public BaseNotification sendBaseNotification(String message, Long receiverId, Long projectId) {
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        BaseNotification baseNotification = new BaseNotification(message, receiver,
                LocalDateTime.now(clock), project);
        return notificationRepository.save(baseNotification);
    }

    @Override
    public ContributionNotification sendContributionNotification(String message,
                                                                 Long contributionId,
                                                                 Long receiverId,
                                                                 Long projectId) {
        Contribution contribution = contributionRepository
                .findById(contributionId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid contribution id: " + contributionId));
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        ContributionNotification contributionNotification = new ContributionNotification(message,
                contribution, receiver, LocalDateTime.now(clock), project);
        return notificationRepository.save(contributionNotification);
    }

    @Override
    public PersonNotification sendPersonNotification(String message, Long personId,
                                                     Long receiverId, Long projectId) {
        Person person = getPerson(personId);
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        PersonNotification personNotification = new PersonNotification(message, person, receiver,
                LocalDateTime.now(clock), project);
        return notificationRepository.save(personNotification);
    }

    @Override
    public UpdateNotification sendUpdateNotification(String message, Long updateId,
                                                     Long receiverId, Long projectId) {
        Update update = updateRepository
                .findById(updateId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid update id: " + updateId));
        Person receiver = getPerson(receiverId);
        Project project = getProject(projectId);

        UpdateNotification updateNotification = new UpdateNotification(message, update, receiver,
                LocalDateTime.now(clock), project);
        return notificationRepository.save(updateNotification);
    }

    @Override
    public Optional<BaseNotification> get(Long id) {
        return notificationRepository.findById(id);
    }

    @Override
    public Page<BaseNotification> getAll(Pageable pageable) {
        return notificationRepository.findAll(pageable);
    }

    @Override
    public Page<BaseNotification> getAllByPerson(Long personId, Pageable pageable) {
        return notificationRepository.findAllByReceiverId(personId, pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
        }

        return !notificationRepository.existsById(id);
    }

    @Override
    public void update(Long id, BaseNotification updatedNotification) {
        if (updatedNotification == null) {
            throw new IllegalArgumentException("Updated notification must not be null");
        }

        BaseNotification notification = getBaseNotification(id);
        notification.setMessage(updatedNotification.getMessage());
        notificationRepository.save(notification);
    }

    private Person getPerson(Long id) {
        return personRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid person id: " + id));
    }

    private Project getProject(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid project id: " + id));
    }

    private BaseNotification getBaseNotification(Long id) {
        return notificationRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid notification id: " + id));
    }
}
