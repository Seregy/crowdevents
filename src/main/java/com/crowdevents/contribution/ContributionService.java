package com.crowdevents.contribution;

import java.util.Optional;
import java.util.UUID;

import org.joda.money.Money;

public interface ContributionService {
    Contribution contribute(UUID personId, UUID projectId, Money money, UUID rewardId);

    Optional<Contribution> get(UUID id);

    void delete(UUID id);

    void changeReward(UUID id, UUID newRewardId);
}
