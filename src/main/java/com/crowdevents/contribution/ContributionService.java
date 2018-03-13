package com.crowdevents.contribution;

import com.crowdevents.reward.Reward;
import org.joda.money.Money;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ContributionService {
    Contribution contribute(UUID personId, UUID projectId, Money money, UUID rewardId, LocalDateTime dateTime);
    Contribution get(UUID id);
    void delete(UUID id);
    void changeReward(UUID id, UUID newRewardId);
}
