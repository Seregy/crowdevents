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

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Optional;
import java.util.UUID;

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
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Reward mockReward = new Reward(mockProject, 1, 1, "description");
        Contribution mockContribution = new Contribution(mockPerson, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward);
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockReward));

        Contribution result = contributionService.contribute(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                UUID.fromString("00000000-0000-0000-0000-000000000002"), Money.of(CurrencyUnit.USD, 1),
                UUID.fromString("00000000-0000-0000-0000-000000000003"));

        assertEquals(mockContribution, result);
    }

    @Test
    public void contribute_WithWrongPersonId_ShouldThrowException() {
        Project mockProject = new Project("Name", "description", null);
        Reward mockReward = new Reward(mockProject, 1, 1, "description");
        Contribution mockContribution = new Contribution(null, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward);
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"), Money.of(CurrencyUnit.USD, 1),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid person id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void contribute_WithWrongProjectId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Reward mockReward = new Reward(null, 1, 1, "description");
        Contribution mockContribution = new Contribution(mockPerson, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward);
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(mockReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"), Money.of(CurrencyUnit.USD, 1),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void contribute_WithWrongRewardId_ShouldThrowException() {
        Person mockPerson = new Person("email", "password", "name");
        Project mockProject = new Project("Name", "description", null, mockPerson);
        Contribution mockContribution = new Contribution(mockPerson, mockProject,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null);
        Mockito.when(mockContributionRepository.save(Mockito.any()))
                .thenReturn(mockContribution);
        Mockito.when(mockPersonRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockPerson));
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockProject));
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.contribute(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    UUID.fromString("00000000-0000-0000-0000-000000000002"), Money.of(CurrencyUnit.USD, 1),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000003",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingContribution() {
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), null);
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockContribution));

        Optional<Contribution> result = contributionService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockContribution), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Contribution> result = contributionService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteComment() {
        contributionService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockContributionRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeReward_WithProperParams_ShouldChangeReward() {
        Reward mockReward = new Reward(null, 1, 1, "description");
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward);
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockContribution));
        Reward anotherReward = new Reward(null, 5, 5, "Another description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(anotherReward));

        assertEquals(mockReward, mockContribution.getReward());
        contributionService.changeReward(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                UUID.fromString("00000000-0000-0000-0000-000000000003"));
        assertEquals(anotherReward, mockContribution.getReward());
    }

    @Test
    public void changeReward_WithWrongContributionId_ShouldThrowException() {
        Reward mockReward = new Reward(null, 1, 1, "description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.empty());
        Reward anotherReward = new Reward(null, 5, 5, "Another description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.of(anotherReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.changeReward(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid contribution id: 00000000-0000-0000-0000-000000000002",
                exception.getMessage());
    }

    @Test
    public void changeReward_WithWrongRewardId_ShouldThrowException() {
        Reward mockReward = new Reward(null, 1, 1, "description");
        Contribution mockContribution = new Contribution(null, null,
                LocalDateTime.parse("2018-01-01T01:00:00"), Money.of(CurrencyUnit.USD, 1), mockReward);
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));
        Mockito.when(mockContributionRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000002")))
                .thenReturn(Optional.of(mockContribution));
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000003")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            contributionService.changeReward(UUID.fromString("00000000-0000-0000-0000-000000000002"),
                    UUID.fromString("00000000-0000-0000-0000-000000000003"));
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000003",
                exception.getMessage());
    }
}