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

import java.util.Optional;
import java.util.UUID;

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
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockProject));
        Reward mockReward = new Reward(mockProject, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.save(Mockito.any())).thenReturn(mockReward);


        Reward result = rewardService.create(UUID.fromString("00000000-0000-0000-0000-000000000001"), 2,
                Money.of(CurrencyUnit.USD, 1), "Reward description");

        assertEquals(mockReward, result);
    }

    @Test
    public void create_WithWrongProjectId_ShouldThrowException() {
        Mockito.when(mockProjectRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.save(Mockito.any())).thenReturn(mockReward);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.create(UUID.fromString("00000000-0000-0000-0000-000000000001"), 2,
                    Money.of(CurrencyUnit.USD, 1), "Reward description");
        });

        assertEquals("Invalid project id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void get_WithProperParams_ShouldReturnExistingReward() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        Optional<Reward> result = rewardService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.of(mockReward), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Optional<Reward> result = rewardService.get(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteReward() {
        rewardService.delete(UUID.fromString("00000000-0000-0000-0000-000000000001"));

        Mockito.verify(mockRewardRepository, Mockito.times(1))
                .deleteById(UUID.fromString("00000000-0000-0000-0000-000000000001"));
    }

    @Test
    public void changeLimit_WithProperParams_ShouldChangeLimit() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        assertEquals(Integer.valueOf(2), mockReward.getMaximumAmount());
        rewardService.changeLimit(UUID.fromString("00000000-0000-0000-0000-000000000001"), 5);
        assertEquals(Integer.valueOf(5), mockReward.getMaximumAmount());
    }

    @Test
    public void changeLimit_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeLimit(UUID.fromString("00000000-0000-0000-0000-000000000001"), 5);
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeMinimalContribution_WithProperParams_ShouldChangeMinimalContribution() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        assertEquals(Money.of(CurrencyUnit.USD, 1), mockReward.getMinimalContribution());
        rewardService.changeMinimalContribution(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                Money.of(CurrencyUnit.USD, 5));
        assertEquals(Money.of(CurrencyUnit.USD, 5), mockReward.getMinimalContribution());
    }

    @Test
    public void changeMinimalContribution_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeMinimalContribution(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    Money.of(CurrencyUnit.USD, 5));
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeDescription_WithProperParams_ShouldChangeDescription() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        assertEquals("Reward description", mockReward.getDescription());
        rewardService.changeDescription(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New description");
        assertEquals("New description", mockReward.getDescription());
    }

    @Test
    public void changeDescription_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeDescription(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New description");
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeDeliveryDate_WithProperParams_ShouldChangeDeliveryDate() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        assertNull(mockReward.getDeliveryDate());
        rewardService.changeDeliveryDate(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New delivery date");
        assertEquals("New delivery date", mockReward.getDeliveryDate());
    }

    @Test
    public void changeDeliveryDate_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeDeliveryDate(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New delivery date");
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }

    @Test
    public void changeShippingLocation_WithProperParams_ShouldChangeShippingLocation() {
        Reward mockReward = new Reward(null, 2, Money.of(CurrencyUnit.USD, 1),
                "Reward description");
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.of(mockReward));

        assertNull(mockReward.getShippedTo());
        rewardService.changeShippingLocation(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                "New shipping location");
        assertEquals("New shipping location", mockReward.getShippedTo());
    }

    @Test
    public void changeShippingLocation_WithWrongRewardId_ShouldThrowException() {
        Mockito.when(mockRewardRepository.findById(UUID.fromString("00000000-0000-0000-0000-000000000001")))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            rewardService.changeShippingLocation(UUID.fromString("00000000-0000-0000-0000-000000000001"),
                    "New shipping location");
        });

        assertEquals("Invalid reward id: 00000000-0000-0000-0000-000000000001",
                exception.getMessage());
    }
}