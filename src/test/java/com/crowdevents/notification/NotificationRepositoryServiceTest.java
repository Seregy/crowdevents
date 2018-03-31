package com.crowdevents.notification;

import com.crowdevents.contribution.Contribution;
import com.crowdevents.contribution.ContributionRepository;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.update.Update;
import com.crowdevents.update.UpdateRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationRepositoryServiceTest {
    @Mock
    private NotificationRepository mockNotificationRepository;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private ProjectRepository mockProjectRepository;
    @Mock
    private ContributionRepository mockContributionRepository;
    @Mock
    private UpdateRepository mockUpdateRepository;

    private NotificationRepositoryService notificationService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Clock clock = Clock.fixed(Instant.parse("2018-01-01T01:00:00Z"), ZoneId.systemDefault());
        notificationService = new NotificationRepositoryService(mockNotificationRepository, mockPersonRepository,
                mockProjectRepository, mockContributionRepository, mockUpdateRepository, clock);
    }

    @Test
    public void sendBaseNotification_WithProperParams_ShouldCreateNewBaseNotification() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        BaseNotification mockBaseNotification = new BaseNotification("Base notification", mockPerson,
                LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockBaseNotification);

        BaseNotification result = notificationService.sendBaseNotification("Base notification",
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"));

        assertEquals(mockBaseNotification, result);
    }

    @Test
    public void sendBaseNotification_WithWrongPersonId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        BaseNotification mockBaseNotification = new BaseNotification("Base notification", mockPerson,
                LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockBaseNotification);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendBaseNotification("Base notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void sendBaseNotification_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        BaseNotification mockBaseNotification = new BaseNotification("Base notification", mockPerson,
                LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockBaseNotification);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendBaseNotification("Base notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void sendContributionNotification_WithProperParams_ShouldCreateNewContributionNotification() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Contribution mockContribution = new Contribution(mockPerson, mockProject, null,
                null, null);
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockContribution));
        ContributionNotification mockContributionNotification = new ContributionNotification("Notification",
                mockContribution, mockPerson, LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockContributionNotification);

        ContributionNotification result = notificationService.sendContributionNotification("Notification",
                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"));

        assertEquals(mockContributionNotification, result);
    }

    @Test
    public void sendContributionNotification_WithWrongContributionId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.empty());
        ContributionNotification mockContributionNotification = new ContributionNotification("Notification",
                null, mockPerson, LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockContributionNotification);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendContributionNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid contribution id: 00000000-0000-0000-0000-000000000003",
                exception.getMessage());
    }

    @Test
    public void sendContributionNotification_WithWrongPersonId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Contribution mockContribution = new Contribution(mockPerson, mockProject, null,
                null, null);
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockContribution));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendContributionNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void sendContributionNotification_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        Contribution mockContribution = new Contribution(mockPerson, null, null,
                null, null);
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockContribution));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendContributionNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void sendPersonNotification_WithProperParams_ShouldCreateNewPersonNotification() {
        Person receiver = new Person("receiver@mail.com", "password", "Receiver person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(receiver));
        Person targetPerson = new Person("target@mail.com", "password", "Target person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(targetPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                targetPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockProject));
        PersonNotification mockPersonNotification = new PersonNotification("Notification",
                targetPerson, receiver, LocalDateTime.parse("2018-01-01T01:00:00"), mockProject);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockPersonNotification);

        PersonNotification result = notificationService.sendPersonNotification("Notification",
                UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000003"));

        assertEquals(mockPersonNotification, result);
    }

    @Test
    public void sendPersonNotification_WithWrongTargetPersonId_ShouldThrowException() {
        Person receiver = new Person("receiver@mail.com", "password", "Receiver person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(receiver));
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Mock project", "Description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockProject));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendPersonNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void sendPersonNotification_WithWrongReceiverPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Person targetPerson = new Person("target@mail.com", "password", "Target person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(targetPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                targetPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockProject));
        PersonNotification mockPersonNotification = new PersonNotification("Notification",
                targetPerson, null, LocalDateTime.parse("2018-01-01T01:00:00"), mockProject);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockPersonNotification);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendPersonNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void sendPersonNotification_WithWrongProjectId_ShouldThrowException() {
        Person receiver = new Person("receiver@mail.com", "password", "Receiver person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(receiver));
        Person targetPerson = new Person("target@mail.com", "password", "Target person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(targetPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.empty());
        PersonNotification mockPersonNotification = new PersonNotification("Notification",
                targetPerson, receiver, LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockPersonNotification);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendPersonNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000003",
                exception.getMessage());
    }

    @Test
    public void sendUpdateNotification_WithProperParams_ShouldCreateNewUpdateNotification() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Mock project", "Description", null, mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Update mockUpdate = new Update(mockProject, LocalDateTime.parse("2018-01-01T01:00:00"), "Update");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockUpdate));
        UpdateNotification mockUpdateNotification = new UpdateNotification("Notification", mockUpdate,
                mockPerson, LocalDateTime.parse("2018-01-01T01:00:00"), mockProject);
        Mockito.when(mockNotificationRepository.save(Mockito.any())).thenReturn(mockUpdateNotification);

        UpdateNotification result = notificationService.sendUpdateNotification("Notification",
                UUID.fromString("00000000-0000-0000-0000-000000000003"),
                UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"));

        assertEquals(mockUpdateNotification, result);
    }

    @Test
    public void sendUpdateNotification_WithWrongUpdateId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Mock project", "Description", null,
                mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendUpdateNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid update id: 00000000-0000-0000-0000-000000000003",
                exception.getMessage());
    }

    @Test
    public void sendUpdateNotification_WithWrongPersonId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Mock project", "Description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Update mockUpdate = new Update(mockProject, LocalDateTime.parse("2018-01-01T01:00:00"), "Update");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockUpdate));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendUpdateNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void sendUpdateNotification_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("mock@mail.com", "password", "Mock person");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        Update mockUpdate = new Update(null, LocalDateTime.parse("2018-01-01T01:00:00"), "Update");
        Mockito.when(mockUpdateRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockUpdate));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.sendUpdateNotification("Notification",
                    UUID.fromString("00000000-0000-0000-0000-000000000003"),
                    UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingNotification() {
        BaseNotification mockNotification = new BaseNotification("Message", null,
                LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockNotification));

        Optional<BaseNotification> result = notificationService.get(
                UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockNotification), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockNotificationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<BaseNotification> result = notificationService.get(
                UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteNotification() {
        notificationService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockNotificationRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeMessage_WithProperParams_ShouldChangeMessage() {
        BaseNotification mockNotification = new BaseNotification("Message", null,
                LocalDateTime.parse("2018-01-01T01:00:00"), null);
        Mockito.when(mockNotificationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockNotification));

        assertEquals("Message", mockNotification.getMessage());
        notificationService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New message");
        assertEquals("New message", mockNotification.getMessage());
    }

    @Test
    public void changeMessage_WithWrongMessageId_ShouldThrowException() {
        Mockito.when(mockNotificationRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            notificationService.changeMessage(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New message");
        });

        assertEquals("Invalid notification id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}