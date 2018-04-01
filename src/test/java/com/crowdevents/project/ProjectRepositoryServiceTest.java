package com.crowdevents.project;

import com.crowdevents.location.Location;
import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectRepositoryServiceTest {
    @Mock
    private ProjectRepository mockProjectRepository;
    @Mock
    private PersonRepository mockPersonRepository;
    @InjectMocks
    private ProjectRepositoryService projectService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_WithProperParams_ShouldCreateNewProject() {
        Person mockPerson = new Person("email", "password", "name");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenReturn(mockProject);

        Project result = projectService.create("Name", "description", null,
                UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(mockProject, result);
    }

    @Test
    public void create_WithWrongOwnerId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenReturn(mockProject);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.create("Name", "description", null,
                    UUID.fromString("00000000-0000-0000-0000-000000000001"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingProject() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        Optional<Project> result = projectService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockProject), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Project> result = projectService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllProjects() {
        Project[] projects = {new Project("Project 1", "Description 1", null),
                new Project("Project 2", "Description 2", null),
                new Project("Project 3", "Description 3", null),
                new Project("Project 4", "Description 4", null)};
        Mockito.when(mockProjectRepository.findAll()).thenReturn(Arrays.asList(projects));

        Iterable<Project> result = projectService.getAll();

        assertEquals(Arrays.asList(projects), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteProject() {
        projectService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeName_WithProperParams_ShouldChangeName() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertEquals("Name", mockProject.getName());
        projectService.changeName(UUID.fromString("00000000-0000-0000-0000-000000000001"), "New name");
        assertEquals("New name", mockProject.getName());
    }

    @Test
    public void changeName_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeName(UUID.fromString("00000000-0000-0000-0000-000000000001"), "New name");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeDescription_WithProperParams_ShouldChangeDescription() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertEquals("description", mockProject.getDescription());
        projectService.changeDescription(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New description");
        assertEquals("New description", mockProject.getDescription());
    }

    @Test
    public void changeDescription_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeDescription(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New description");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeFundingGoal_WithProperParams_ShouldChangeFundingGoal() {
        Money goal = Money.of(CurrencyUnit.USD, 10);
        Project mockProject = new Project("Name", "description", goal);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertEquals(Money.of(CurrencyUnit.USD, 10), mockProject.getFundingGoal());
        projectService.changeFundingGoal(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                Money.of(CurrencyUnit.USD, 15));
        assertEquals(Money.of(CurrencyUnit.USD, 15), mockProject.getFundingGoal());
    }

    @Test
    public void changeFundingGoal_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeFundingGoal(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    Money.of(CurrencyUnit.USD, 15));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeLocation_WithProperParams_ShouldChangeLocation() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getLocation());
        projectService.changeLocation(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                new Location(50.45, 30.52));
        assertEquals(new Location(50.45, 30.52), mockProject.getLocation());
    }

    @Test
    public void changeLocation_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeLocation(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    new Location(50.45, 30.52));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeStartDateTime_WithProperParams_ShouldChangeStartDateTime() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getStartDateTime());
        projectService.changeStartDateTime(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                LocalDateTime.parse("2018-01-01T01:00:00"));
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getStartDateTime());
    }

    @Test
    public void changeStartDateTime_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeStartDateTime(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    LocalDateTime.parse("2018-01-01T01:00:00"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeEndDateTime_WithProperParams_ShouldChangeEndDateTime() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getEndDateTime());
        projectService.changeEndDateTime(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                LocalDateTime.parse("2018-01-01T01:00:00"));
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getEndDateTime());
    }

    @Test
    public void changeEndDateTime_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeEndDateTime(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    LocalDateTime.parse("2018-01-01T01:00:00"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void addVideoLink_WithProperParams_ShouldAddNewVideoLink() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertTrue(mockProject.getVideoLinks().isEmpty());
        projectService.addVideoLink(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "link 1", "link 2");
        assertEquals(Arrays.asList("link 1", "link 2"), mockProject.getVideoLinks());
    }

    @Test
    public void addVideoLink_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addVideoLink(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "link 1", "link 2");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void addImageLink_WithProperParams_ShouldAddNewImageLink() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        assertTrue(mockProject.getImageLinks().isEmpty());
        projectService.addImageLink(UUID.fromString("00000000-0000-0000-0000-000000000001"),
               "image link 1", "image link 2");
        assertEquals(Arrays.asList("image link 1", "image link 2"), mockProject.getImageLinks());
    }

    @Test
    public void addImageLink_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addImageLink(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "image link 1", "image link 2");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void addOwner_WithProperParams_ShouldAddNewOwner() {
        Person mockPerson = new Person("email", "password", "name");
        Set<Person> initialOwners = new HashSet<>();
        initialOwners.add(mockPerson);
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));

        Person newOwner = new Person("Another@mail.com", "owner", "owner");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(newOwner));

        assertEquals(initialOwners, mockProject.getOwners());
        projectService.addOwner(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"));
        Set<Person> resultOwners = new HashSet<>();
        resultOwners.add(mockPerson);
        resultOwners.add(newOwner);
        assertEquals(resultOwners, mockProject.getOwners());
    }

    @Test
    public void addOwner_WithWrongOwnerId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addOwner(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void addOwner_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Person newOwner = new Person("Another@mail.com", "owner", "owner");
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(newOwner));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addOwner(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}