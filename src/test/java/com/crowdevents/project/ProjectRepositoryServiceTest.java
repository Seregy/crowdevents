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
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), mockPerson);
        Mockito.when(mockProjectRepository.save(Mockito.any()))
                .thenReturn(mockProject);

        Project result = projectService.create("Name", "description",
                Money.of(CurrencyUnit.USD, 1),
                1L);

        assertEquals(mockProject, result);
    }

    @Test
    public void create_WithWrongOwnerId_ShouldThrowException() {
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
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
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
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
        Project[] projects = {new Project("Project 1", "Description 1",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", "")),
                new Project("Project 2", "Description 2", Money.of(CurrencyUnit.USD, 2),
                        new Person("", "", "")),
                new Project("Project 3", "Description 3", Money.of(CurrencyUnit.USD, 3),
                        new Person("", "", "")),
                new Project("Project 4", "Description 4", Money.of(CurrencyUnit.USD, 4),
                        new Person("", "", ""))};
        Mockito.when(mockProjectRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(projects)));

        Page<Project> result = projectService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(projects)), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperBeforeId_ShouldReturnAllProjectsBeforeId() {
        Project[] projects = {new Project("Project 1", "Description 1",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", "")),
                new Project("Project 2", "Description 2", Money.of(CurrencyUnit.USD, 2),
                        new Person("", "", "")),
                new Project("Project 3", "Description 3", Money.of(CurrencyUnit.USD, 3),
                        new Person("", "", "")),
                new Project("Project 4", "Description 4", Money.of(CurrencyUnit.USD, 4),
                        new Person("", "", ""))};
        List<Project> beforeList = Arrays.asList(projects[0], projects[1]);
        Mockito.when(mockProjectRepository.findAllByIdBefore(Mockito.eq(3L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(beforeList));

        Page<Project> result = projectService.getAllBeforeAndOrAfter(3L, null,
                PageRequest.of(0, 2));

        assertEquals(new PageImpl<>(beforeList), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperAfterId_ShouldReturnAllProjectsAfterId() {
        Project[] projects = {new Project("Project 1", "Description 1",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", "")),
                new Project("Project 2", "Description 2", Money.of(CurrencyUnit.USD, 2),
                        new Person("", "", "")),
                new Project("Project 3", "Description 3", Money.of(CurrencyUnit.USD, 3),
                        new Person("", "", "")),
                new Project("Project 4", "Description 4", Money.of(CurrencyUnit.USD, 4),
                        new Person("", "", ""))};
        List<Project> afterList = Arrays.asList(projects[2], projects[3]);
        Mockito.when(mockProjectRepository.findAllByIdAfter(Mockito.eq(2L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(afterList));

        Page<Project> result = projectService.getAllBeforeAndOrAfter(null, 2L,
                PageRequest.of(0, 2));

        assertEquals(new PageImpl<>(afterList), result);
    }

    @Test
    public void getAllBeforeAndOrAfter_WithProperBeforeAndAfterId_ShouldReturnAllProjectsBetweenIds() {
        Project[] projects = {new Project("Project 1", "Description 1",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", "")),
                new Project("Project 2", "Description 2", Money.of(CurrencyUnit.USD, 2),
                        new Person("", "", "")),
                new Project("Project 3", "Description 3", Money.of(CurrencyUnit.USD, 3),
                        new Person("", "", "")),
                new Project("Project 4", "Description 4", Money.of(CurrencyUnit.USD, 4),
                        new Person("", "", ""))};
        List<Project> betweenList = Arrays.asList(projects[1], projects[2]);
        Mockito.when(mockProjectRepository
                .findAllByIdAfterAndIdBefore(Mockito.eq(1L), Mockito.eq(4L), Mockito.any(Pageable.class)))
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
        Mockito.when(mockProjectRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(projectService.delete(1L));

        Mockito.verify(mockProjectRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithProperProject_ShouldUpdateProject() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setStartDateTime(LocalDateTime.parse("2018-01-01T01:00:00"));

        assertNull(mockProject.getStartDateTime());
        projectService.update(1L, newProject);
        assertEquals("Name", mockProject.getName());
        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getStartDateTime());
    }

    @Test
    public void update_WithNewName_ShouldChangeName() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("New name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));

        assertEquals("Name", mockProject.getName());
        projectService.update(1L, newProject);
        assertEquals("New name", mockProject.getName());
    }

    @Test
    public void update_WithNewDescription_ShouldChangeShortDescription() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));

        assertEquals("description", mockProject.getShortDescription());
        projectService.update(1L, newProject);
        assertEquals("New description", mockProject.getShortDescription());
    }

    @Test
    public void update_WithNewDescription_ShouldChangeDescription() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        mockProject.setDescription("Full description");
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setDescription("New full description");

        assertEquals("Full description", mockProject.getDescription());
        projectService.update(1L, newProject);
        assertEquals("New full description", mockProject.getDescription());
    }

    @Test
    public void update_WithNewFundingGoal_ShouldChangeFundingGoal() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 7), new Person("", "", ""));

        assertEquals(Money.of(CurrencyUnit.USD, 1), mockProject.getFundingGoal());
        projectService.update(1L, newProject);
        assertEquals(Money.of(CurrencyUnit.USD, 7), mockProject.getFundingGoal());
    }

    @Test
    public void update_WithNewLocation_ShouldChangeLocation() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setLocation(new Location(20.13, 37.21));

        assertNull(mockProject.getLocation());
        projectService.update(1L, newProject);
        assertEquals(new Location(20.13, 37.21), mockProject.getLocation());
    }

    @Test
    public void update_WithNewStartDate_ShouldChangeStartDate() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        mockProject.setStartDateTime(LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setStartDateTime(LocalDateTime.parse("2018-02-02T12:00:00"));

        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getStartDateTime());
        projectService.update(1L, newProject);
        assertEquals(LocalDateTime.parse("2018-02-02T12:00:00"), mockProject.getStartDateTime());
    }

    @Test
    public void update_WithNewEndDate_ShouldChangeEndDate() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        mockProject.setEndDateTime(LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setEndDateTime(LocalDateTime.parse("2018-02-02T12:00:00"));

        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getEndDateTime());
        projectService.update(1L, newProject);
        assertEquals(LocalDateTime.parse("2018-02-02T12:00:00"), mockProject.getEndDateTime());
    }

    @Test
    public void update_WithNewEventDate_ShouldChangeEventDate() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        mockProject.setEventDateTime(LocalDateTime.parse("2018-01-01T01:00:00"));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setEventDateTime(LocalDateTime.parse("2018-02-02T12:00:00"));

        assertEquals(LocalDateTime.parse("2018-01-01T01:00:00"), mockProject.getEventDateTime());
        projectService.update(1L, newProject);
        assertEquals(LocalDateTime.parse("2018-02-02T12:00:00"), mockProject.getEventDateTime());
    }

    @Test
    public void update_WithNewVideoLinks_ShouldChangeVideoLinks() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setVideoLinks(Arrays.asList("link1", "link2", "link3"));

        assertTrue(mockProject.getVideoLinks().isEmpty());
        projectService.update(1L, newProject);
        assertEquals(Arrays.asList("link1", "link2", "link3"), mockProject.getVideoLinks());
    }

    @Test
    public void update_WithNewImageLinks_ShouldChangeImageLinks() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        newProject.setImageLinks(Arrays.asList("link1", "link2", "link3"));

        assertTrue(mockProject.getImageLinks().isEmpty());
        projectService.update(1L, newProject);
        assertEquals(Arrays.asList("link1", "link2", "link3"), mockProject.getImageLinks());
    }

    @Test
    public void update_WithNewTeamMembers_ShouldChangeTeamMembers() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Project newProject = new Project("Name", "New description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Set<Person> persons = Set.of(new Person("email", "pass", "Person 1"),
                new Person("email", "pass", "Person 2"),
                new Person("email", "pass", "Person 3"));
        newProject.setTeamMembers(persons);

        assertTrue(mockProject.getTeamMembers().isEmpty());
        projectService.update(1L, newProject);
        assertEquals(persons, mockProject.getTeamMembers());
    }

    @Test
    public void update_WithNullUpdatedProject_ShouldThrowException() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.update(1L, null);
        });

        assertEquals("Updated project must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Project newProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            projectService.update(1L, newProject);
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }
}