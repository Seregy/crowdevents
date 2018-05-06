package com.crowdevents.reward;

import com.crowdevents.person.Person;
import com.crowdevents.project.Project;
import com.crowdevents.project.ProjectRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class RewardRepositoryServiceTest {
    @Mock
    private RewardRepository mockRewardRepository;
    @Mock
    private ProjectRepository mockProjectRepository;
    @InjectMocks
    private RewardRepositoryService rewardService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_WithProperParams_ShouldCreateNewReward() {
        Project mockProject = new Project("Name", "description",
                Money.of(CurrencyUnit.USD, 1), new Person("", "", ""));
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.of(mockProject));
        Reward mockReward = new Reward(mockProject, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.save(Mockito.any())).thenReturn(mockReward);


        Reward result = rewardService.create(1L, 2,
                Money.of(CurrencyUnit.USD, 1), "Reward description");

        assertEquals(mockReward, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(1L))
                .thenReturn(Optional.empty());
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.save(Mockito.any())).thenReturn(mockReward);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.create(1L, 2,
                    Money.of(CurrencyUnit.USD, 1), "Reward description");
        });

        assertEquals("Invalid project id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingReward() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        Optional<Reward> result = rewardService.get(1L);

        assertEquals(Optional.of(mockReward), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Reward> result = rewardService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllReward() {
        Reward[] rewards = {
                new Reward(null, 1, Money.of(CurrencyUnit.USD, 1), "Reward 1"),
                new Reward(null, 2, Money.of(CurrencyUnit.USD, 2), "Reward 2"),
                new Reward(null, 3, Money.of(CurrencyUnit.USD, 3), "Reward 3"),
                new Reward(null, 4, Money.of(CurrencyUnit.USD, 4), "Reward 4")
        };
        Mockito.when(mockRewardRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(rewards)));

        Page<Reward> result = rewardService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(rewards)), result);
    }

    @Test
    public void getAllByProject_WithProperProjectId_ShouldReturnAllRewards() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Reward[] rewards = {
                new Reward(project, 1, Money.of(CurrencyUnit.USD, 1), "Reward 1"),
                new Reward(project, 2, Money.of(CurrencyUnit.USD, 2), "Reward 2"),
                new Reward(project, 3, Money.of(CurrencyUnit.USD, 3), "Reward 3"),
                new Reward(project, 4, Money.of(CurrencyUnit.USD, 4), "Reward 4")
        };
        Mockito.when(mockRewardRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(rewards)));

        Page<Reward> result = rewardService.getAllByProject(1L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(rewards)), result);
    }

    @Test
    public void getAllByProject_WithWrongProjectId_ShouldReturnEmptyPage() {
        Project project = new Project("Project 1", null, Money.of(CurrencyUnit.USD, 1),
                new Person("", "", ""));
        project.setId(1L);
        Reward[] rewards = {
                new Reward(project, 1, Money.of(CurrencyUnit.USD, 1), "Reward 1"),
                new Reward(project, 2, Money.of(CurrencyUnit.USD, 2), "Reward 2"),
                new Reward(project, 3, Money.of(CurrencyUnit.USD, 3), "Reward 3"),
                new Reward(project, 4, Money.of(CurrencyUnit.USD, 4), "Reward 4")
        };
        Mockito.when(mockRewardRepository.findAllByProjectId(Mockito.eq(1L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(rewards)));
        Mockito.when(mockRewardRepository.findAllByProjectId(Mockito.eq(2L), Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Collections.emptyList()));

        Page<Reward> result = rewardService.getAllByProject(2L, PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Collections.emptyList()), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteReward() {
        Mockito.when(mockRewardRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(rewardService.delete(1L));

        Mockito.verify(mockRewardRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void update_WithNewLimit_ShouldChangeLimit() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 5, Money.of(CurrencyUnit.USD, 5),
                "Reward description");

        assertEquals(Integer.valueOf(2), mockReward.getLimit());
        rewardService.update(1L, updatedReward);
        assertEquals(Integer.valueOf(5), mockReward.getLimit());
    }

    @Test
    public void update_WithNewMinimalContribution_ShouldChangeMinimalContribution() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 5),
                "Reward description");

        assertEquals(Money.of(CurrencyUnit.USD, 1), mockReward.getMinimalContribution());
        rewardService.update(1L, updatedReward);
        assertEquals(Money.of(CurrencyUnit.USD, 5), mockReward.getMinimalContribution());
    }

    @Test
    public void update_WithNewDescription_ShouldChangeDescription() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "New description");

        assertEquals("Reward description", mockReward.getDescription());
        rewardService.update(1L, updatedReward);
        assertEquals("New description", mockReward.getDescription());
    }

    @Test
    public void update_WithNewDeliveryDate_ShouldChangeDeliveryDate() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        updatedReward.setDeliveryDate("Some delivery date");

        assertNull(mockReward.getDeliveryDate());
        rewardService.update(1L, updatedReward);
        assertEquals("Some delivery date", mockReward.getDeliveryDate());
    }

    @Test
    public void update_WithNewShippingLocation_ShouldChangeShippingLocation() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        updatedReward.setShippingLocation("Some shipping location");

        assertNull(mockReward.getShippingLocation());
        rewardService.update(1L, updatedReward);
        assertEquals("Some shipping location", mockReward.getShippingLocation());
    }

    @Test
    public void update_WithNullReward_ShouldThrowException() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.update(1L, null);
        });

        assertEquals("Updated reward must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongMessageId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());
        Reward updatedReward = new Reward(null, 5, Money.of(CurrencyUnit.USD, 5),
                "New description");

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.update(1L, updatedReward);
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }
}