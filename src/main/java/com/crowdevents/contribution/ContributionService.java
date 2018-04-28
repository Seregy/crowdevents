package com.crowdevents.contribution;

import java.util.Optional;

import org.joda.money.Money;

public interface ContributionService {
    Contribution contribute(Long personId, Long projectId, Money money, Long rewardId);

    Optional<Contribution> get(Long id);

    boolean delete(Long id);

    void changeReward(Long id, Long newRewardId);

    void update(Long id, Contribution updatedContribution);
}
