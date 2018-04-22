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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenReturn(mockProject);

        Project result = projectService.create("Name", "description", null,
                1L);

        assertEquals(mockProject, result);
    }

    @Test
    public void create_WithWrongOwnerId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenReturn(mockProject);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.create("Name", "description", null,
                    1L);
        });

        assertEquals("Invalid person id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingProject() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        Optional<Project> result = projectService.get(1L);

        assertEquals(Optional.of(mockProject), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Project> result = projectService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllProjects() {
        Project[] projects = {new Project("Project 1", "Description 1", null),
                new Project("Project 2", "Description 2", null),
                new Project("Project 3", "Description 3", null),
                new Project("Project 4", "Description 4", null)};
        Mockito.when(mockProjectRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(projects)));

        Page<Project> result = projectService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(projects)), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperBeforeId_ShouldReturnAllProjectsBeforeId() {
        Project[] projects = {new Project("Project 1", "Description 1", null),
                new Project("Project 2", "Description 2", null),
                new Project("Project 3", "Description 3", null),
                new Project("Project 4", "Description 4", null)};
        List<Project> beforeList = Arrays.asList(projects[0], projects[1]);
        Mockito.when(mockProjectRepository.findAllByIdBefore(3L, Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(beforeList));

        Page<Project> result = projectService.getAllBeforeAndOrAfter(3L, null,
                PageRequest.of(0, 2));

        assertEquals(new PageImpl<>(beforeList), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperAfterId_ShouldReturnAllProjectsAfterId() {
        Project[] projects = {new Project("Project 1", "Description 1", null),
                new Project("Project 2", "Description 2", null),
                new Project("Project 3", "Description 3", null),
                new Project("Project 4", "Description 4", null)};
        List<Project> afterList = Arrays.asList(projects[2], projects[3]);
        Mockito.when(mockProjectRepository.findAllByIdAfter(2L, Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(afterList));

        Page<Project> result = projectService.getAllBeforeAndOrAfter(null, 2L,
                PageRequest.of(0, 2));

        assertEquals(new PageImpl<>(afterList), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperBeforeAndAfterId_ShouldReturnAllProjectsBetweenIds() {
        Project[] projects = {new Project("Project 1", "Description 1", null),
                new Project("Project 2", "Description 2", null),
                new Project("Project 3", "Description 3", null),
                new Project("Project 4", "Description 4", null)};
        List<Project> betweenList = Arrays.asList(projects[1], projects[2]);
        Mockito.when(mockProjectRepository
                .findAllByIdAfterAndIdBefore(1L, 4L, Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(betweenList));

        Page<Project> result = projectService.getAllBeforeAndOrAfter(4L, 1L,
                PageRequest.of(0, 2));

        assertEquals(new PageImpl<>(betweenList), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithNullIds_ShouldThrowException() {
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.getAllBeforeAndOrAfter(null, null, PageRequest.of(0, 1));
        });

        assertEquals("Either beforeId or afterId must not be null",
                exception.getMessage());
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteProject() {
        projectService.delete(1L);

        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void changeName_WithProperParams_ShouldChangeName() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertEquals("Name", mockProject.getName());
        projectService.changeName(1L, "New name");
        assertEquals("New name", mockProject.getName());
    }

    @Test
    public void changeName_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeName(1L, "New name");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void changeDescription_WithProperParams_ShouldChangeDescription() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertEquals("description", mockProject.getDescription());
        projectService.changeDescription(1L,
                "New description");
        assertEquals("New description", mockProject.getDescription());
    }

    @Test
    public void changeDescription_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeDescription(1L,
                    "New description");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void changeFundingGoal_WithProperParams_ShouldChangeFundingGoal() {
        Money goal = Money.of(CurrencyUnit.USD, 10);
        Project mockProject = new Project("Name", "description", goal);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertEquals(Money.of(CurrencyUnit.USD, 10), mockProject.getFundingGoal());
        projectService.changeFundingGoal(1L,
                Money.of(CurrencyUnit.USD, 15));
        assertEquals(Money.of(CurrencyUnit.USD, 15), mockProject.getFundingGoal());
    }

    @Test
    public void changeFundingGoal_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeFundingGoal(1L,
                    Money.of(CurrencyUnit.USD, 15));
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void changeLocation_WithProperParams_ShouldChangeLocation() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getLocation());
        projectService.changeLocation(1L,
                new Location(50.45, 30.52));
        assertEquals(new Location(50.45, 30.52), mockProject.getLocation());
    }

    @Test
    public void changeLocation_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeLocation(1L,
                    new Location(50.45, 30.52));
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void changeStartDateTime_WithProperParams_ShouldChangeStartDateTime() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getStartDateTime());
        projectService.changeStartDateTime(1L,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getStartDateTime());
    }

    @Test
    public void changeStartDateTime_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeStartDateTime(1L,
                    LocalDateTime.parse("2018-01-01T01:00:00"));
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void changeEndDateTime_WithProperParams_ShouldChangeEndDateTime() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertNull(mockProject.getEndDateTime());
        projectService.changeEndDateTime(1L,
                LocalDateTime.parse("2018-01-01T01:00:00"));
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getEndDateTime());
    }

    @Test
    public void changeEndDateTime_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.changeEndDateTime(1L,
                    LocalDateTime.parse("2018-01-01T01:00:00"));
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void addVideoLink_WithProperParams_ShouldAddNewVideoLink() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertTrue(mockProject.getVideoLinks().isEmpty());
        projectService.addVideoLink(1L,
                "link 1", "link 2");
        assertEquals(Arrays.asList("link 1", "link 2"), mockProject.getVideoLinks());
    }

    @Test
    public void addVideoLink_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addVideoLink(1L,
                    "link 1", "link 2");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void addImageLink_WithProperParams_ShouldAddNewImageLink() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        assertTrue(mockProject.getImageLinks().isEmpty());
        projectService.addImageLink(1L,
               "image link 1", "image link 2");
        assertEquals(Arrays.asList("image link 1", "image link 2"), mockProject.getImageLinks());
    }

    @Test
    public void addImageLink_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addImageLink(1L,
                    "image link 1", "image link 2");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void addOwner_WithProperParams_ShouldAddNewOwner() {
        Person mockPerson = new Person("email", "password", "name");
        Set<Person> initialOwners = new HashSet<>();
        initialOwners.add(mockPerson);
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        Person newOwner = new Person("Another@mail.com", "owner", "owner");
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.of(newOwner));

        assertEquals(initialOwners, mockProject.getOwners());
        projectService.addOwner(1L,
                2L);
        Set<Person> resultOwners = new HashSet<>();
        resultOwners.add(mockPerson);
        resultOwners.add(newOwner);
        assertEquals(resultOwners, mockProject.getOwners());
    }

    @Test
    public void addOwner_WithWrongOwnerId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addOwner(1L,
                    2L);
        });

        assertEquals("Invalid person id: 2",
                exception.getMessage());
    }

    @Test
    public void addOwner_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Person newOwner = new Person("Another@mail.com", "owner", "owner");
        Mockito.when(mockPersonRepository.findById(2L))
                .thenReturn(Optional.of(newOwner));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.addOwner(1L,
                    2L);
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void update_WithProperProject_ShouldUpdateProject() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "description", null);
        newProject.setStartDateTime(LocalDateTime.parse("2018-01-01T01:00:00"));

        assertNull(mockProject.getStartDateTime());
        projectService.update(1L, newProject);
        assertEquals("Name", mockProject.getName());
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getStartDateTime());
    }

    @Test
    public void update_WithNullProject_ShouldThrowException() {
        Project mockProject = new Project("Name", "description", null);
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.update(1L, null);
        });

        assertEquals("NewProject must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Project newProject = new Project("Name", "description", null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.update(1L, newProject);
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }
}