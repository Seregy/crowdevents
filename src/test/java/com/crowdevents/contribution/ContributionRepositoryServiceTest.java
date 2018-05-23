package com.crowdevents.contribution;

import com.crowdevents.person.Person;
import com.crowdevents.person.PersonRepository;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
import com.crowdevents.reward.Reward;
import com.crowdevents.reward.RewardRepository;
import org.joda.money.CurrencyUnit;
import org.joda.money.Money;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class ContributionRepositoryServiceTest {
    @Mock
    private ContributionRepository mockContributionRepository;
    @Mock
    private PersonRepository mockPersonRepository;
    @Mock
    private ProjectRepository mockProjectRepository;
    @Mock
    private RewardRepository mockRewardRepository;

    private ContributionRepositoryService contributionService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        Clock clock = Clock.fixed(Instant.parse("2018-01-01T01:00:00Z"), ZoneId.systemDefault());
        contributionService = new ContributionRepositoryService(mockContributionRepository, mockPersonRepository,
                mockProjectRepository, mockRewardRepository, clock);
    }

    @Test
    public void contribute_WithProperParams_ShouldCreateNewContribution() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), mockPerson);
        Reward mockReward = new Reward(mockProject, 1, Money.of(CurrencyUnit.USD, 1),
                "description");
        Contribution mockContribution = new Contribution(mockPerson, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward,
                "id");
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(2L))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(3L))
                .thenReturn(Optional.of(mockReward));

        Contribution result = contributionService.contribute(1L,
                2L, Money.of(CurrencyUnit.USD, 1),
                3L, "id");

        assertEquals(mockContribution, result);
    }

    @Test
    public void contribute_WithWrongPersonId_ShouldThrowException() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Reward mockReward = new Reward(mockProject, 1, Money.of(CurrencyUnit.USD, 1),
                "description");
        Contribution mockContribution = new Contribution(null, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward, "id");
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.empty());
        Mockito.when(mockProjectRepository.findById(2L))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(3L))
                .thenReturn(Optional.of(mockReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(1L,
                    2L, Money.of(CurrencyUnit.USD, 1),
                    3L, "id");
        });

        assertEquals("Invalid person id: 1",
                exception.getMessage());
    }

    @Test
    public void contribute_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Reward mockReward = new Reward(null, 1, Money.of(CurrencyUnit.USD, 1),
                "description");
        Contribution mockContribution = new Contribution(mockPerson, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward, "id");
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(2L))
                .thenReturn(Optional.empty());
        Mockito.when(mockRewardRepository.findById(3L))
                .thenReturn(Optional.of(mockReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(1L,
                    2L, Money.of(CurrencyUnit.USD, 1),
                    3L, "id");
        });

        assertEquals("Invalid project id: 2",
                exception.getMessage());
    }

    @Test
    public void contribute_WithWrongRewardId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), mockPerson);
        Contribution mockContribution = new Contribution(mockPerson, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null, "id");
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(1L))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(2L))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(3L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(1L,
                    2L, Money.of(CurrencyUnit.USD, 1),
                    3L, "id");
        });

        assertEquals("Invalid reward id: 3",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingContribution() {
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null, "id");
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.of(mockContribution));

        Optional<Contribution> result = contributionService.get(1L);

        assertEquals(Optional.of(mockContribution), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Contribution> result = contributionService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllContributions() {
        Contribution[] contributions = {
                new Contribution(null, null, LocalDateTime.parse("2018-01-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 1), null, "id 1"),
                new Contribution(null, null, LocalDateTime.parse("2018-02-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 2), null, "id 2"),
                new Contribution(null, null, LocalDateTime.parse("2018-03-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 3), null, "id 3"),
                new Contribution(null, null, LocalDateTime.parse("2018-04-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 4), null, "id 4")};
        Mockito.when(mockContributionRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(contributions)));

        Page<Contribution> result = contributionService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(contributions)), result);
    }

    @Test
    public void getAllByProject_WithProperProjectId_ShouldReturnAllContributions() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Contribution[] contributions = {
                new Contribution(null, project, LocalDateTime.parse("2018-01-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 1), null , "id 1"),
                new Contribution(null, project, LocalDateTime.parse("2018-02-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 2), null, "id 2")};
        Mockito.when(mockContributionRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(contributions)));

        Page<Contribution> result = contributionService.getAllByProject(1L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(contributions)), result);
    }

    @Test
    public void getAllByProject_WithWrongProjectId_ShouldReturnEmptyPage() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Contribution[] contributions = {
                new Contribution(null, project, LocalDateTime.parse("2018-01-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 1), null, "id 1"),
                new Contribution(null, project, LocalDateTime.parse("2018-02-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 2), null, "id 2")};
        Mockito.when(mockContributionRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(contributions)));
        Mockito.when(mockContributionRepository.findAllByProjectId(Mockito.eq(2L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Contribution> result = contributionService.getAllByProject(2L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Collections.emptyList()), result);
    }

    @Test
    public void getAllByPerson_WithProperPersonId_ShouldReturnAllContributions() {
        Person person = new Person("person1@email.com", "pass", "Person 1");
        person.setId(1L);
        Contribution[] contributions = {
                new Contribution(person, null, LocalDateTime.parse("2018-01-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 1), null, "id 1"),
                new Contribution(person, null, LocalDateTime.parse("2018-02-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 2), null, "id 2")};
        Mockito.when(mockContributionRepository.findAllByContributorId(Mockito.eq(1L),
                Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(contributions)));

        Page<Contribution> result = contributionService.getAllByPerson(1L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(contributions)), result);
    }

    @Test
    public void getAllByPerson_WithWrongPersonId_ShouldReturnEmptyPage() {
        Person person = new Person("person1@email.com", "pass", "Person 1");
        person.setId(1L);
        Contribution[] contributions = {
                new Contribution(person, null, LocalDateTime.parse("2018-01-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 1), null, "id 1"),
                new Contribution(person, null, LocalDateTime.parse("2018-02-01T01:00:00"),
                        Money.of(CurrencyUnit.USD, 2), null, "id 2")};
        Mockito.when(mockContributionRepository.findAllByContributorId(Mockito.eq(1L),
                Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(contributions)));
        Mockito.when(mockContributionRepository.findAllByContributorId(Mockito.eq(2L),
                Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Contribution> result = contributionService.getAllByPerson(2L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Collections.emptyList()), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteContribution() {
        Mockito.when(mockContributionRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(contributionService.delete(1L));

        Mockito.verify(mockContributionRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithNewReward_ShouldChangeReward() {
        Reward mockReward = new Reward(null, 1, Money.of(CurrencyUnit.USD, 1),
                "description");
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward, "id");
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.of(mockContribution));
        Reward newReward = new Reward(null, 1, Money.of(CurrencyUnit.CAD, 5),
                "New reward description");
        newReward.setId(2L);
        Mockito.when(mockRewardRepository.findById(2L))
                .thenReturn(Optional.of(newReward));
        Contribution updatedContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-02-02T01:00:00"), Money.of(CurrencyUnit.USD, 1), newReward, "id");

        contributionService.update(1L, updatedContribution);
        assertEquals(newReward, mockContribution.getReward());
    }

    @Test
    public void update_WithNullUpdatedContribution_ShouldThrowException() {
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null, "id");
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.of(mockContribution));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.update(1L, null);
        });

        assertEquals("Updated contribution must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongContributionId_ShouldThrowException() {
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.empty());
        Contribution updatedContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-02-02T01:00:00"), Money.of(CurrencyUnit.USD, 1), null, "id");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.update(1L, updatedContribution);
        });

        assertEquals("Invalid contribution id: 1",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongRewardId_ShouldThrowException() {
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null, "id");
        Mockito.when(mockContributionRepository.findById(1L))
                .thenReturn(Optional.of(mockContribution));
        Reward newReward = new Reward(null, 1, Money.of(CurrencyUnit.CAD, 5),
                "New reward description");
        newReward.setId(2L);
        Mockito.when(mockRewardRepository.findById(2L))
                .thenReturn(Optional.empty());
        Contribution newContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-02-02T01:00:00"), Money.of(CurrencyUnit.USD, 1), newReward, "id");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.update(1L, newContribution);
        });

        assertEquals("Invalid reward id: 2",
                exception.getMessage());
    }
}