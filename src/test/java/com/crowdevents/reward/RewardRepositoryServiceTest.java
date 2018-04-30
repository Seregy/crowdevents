package com.crowdevents.reward;

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
        Project mockProject = new Project("Name", "description", null);
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
        Project project = new Project("Project 1", null, null);
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
        Project project = new Project("Project 1", null, null);
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
    public void changeLimit_WithProperParams_ShouldChangeLimit() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        assertEquals(Integer.valueOf(2), mockReward.getLimit());
        rewardService.changeLimit(1L, 5);
        assertEquals(Integer.valueOf(5), mockReward.getLimit());
    }

    @Test
    public void changeLimit_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeLimit(1L, 5);
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }

    @Test
    public void changeMinimalContribution_WithProperParams_ShouldChangeMinimalContribution() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        assertEquals(Money.of(CurrencyUnit.USD, 1), mockReward.getMinimalContribution());
        rewardService.changeMinimalContribution(1L,
                Money.of(CurrencyUnit.USD, 5));
        assertEquals(Money.of(CurrencyUnit.USD, 5), mockReward.getMinimalContribution());
    }

    @Test
    public void changeMinimalContribution_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeMinimalContribution(1L,
                    Money.of(CurrencyUnit.USD, 5));
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }

    @Test
    public void changeDescription_WithProperParams_ShouldChangeDescription() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        assertEquals("Reward description", mockReward.getDescription());
        rewardService.changeDescription(1L,
                "New description");
        assertEquals("New description", mockReward.getDescription());
    }

    @Test
    public void changeDescription_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeDescription(1L,
                    "New description");
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }

    @Test
    public void changeDeliveryDate_WithProperParams_ShouldChangeDeliveryDate() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        assertNull(mockReward.getDeliveryDate());
        rewardService.changeDeliveryDate(1L,
                "New delivery date");
        assertEquals("New delivery date", mockReward.getDeliveryDate());
    }

    @Test
    public void changeDeliveryDate_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeDeliveryDate(1L,
                    "New delivery date");
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }

    @Test
    public void changeShippingLocation_WithProperParams_ShouldChangeShippingLocation() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));

        assertNull(mockReward.getShippingLocation());
        rewardService.changeShippingLocation(1L,
                "New shipping location");
        assertEquals("New shipping location", mockReward.getShippingLocation());
    }

    @Test
    public void changeShippingLocation_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeShippingLocation(1L,
                    "New shipping location");
        });

        assertEquals("Invalid reward id: 1",
                exception.getMessage());
    }

    @Test
    public void update_WithProperParams_ShouldUpdateReward() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(1L))
                .thenReturn(Optional.of(mockReward));
        Reward updatedReward = new Reward(null, 5, Money.of(CurrencyUnit.USD, 5),
                "New description");
        updatedReward.setShippingLocation("New shipping location");
        updatedReward.setDeliveryDate("New delivery date");

        assertEquals(Integer.valueOf(2), mockReward.getLimit());
        assertEquals(Money.of(CurrencyUnit.USD, 1), mockReward.getMinimalContribution());
        assertEquals("Reward description", mockReward.getDescription());
        assertNull(mockReward.getShippingLocation());
        assertNull(mockReward.getDeliveryDate());
        rewardService.update(1L, updatedReward);
        assertEquals(Integer.valueOf(5), mockReward.getLimit());
        assertEquals(Money.of(CurrencyUnit.USD, 5), mockReward.getMinimalContribution());
        assertEquals("New description", mockReward.getDescription());
        assertEquals("New shipping location", mockReward.getShippingLocation());
        assertEquals("New delivery date", mockReward.getDeliveryDate());
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