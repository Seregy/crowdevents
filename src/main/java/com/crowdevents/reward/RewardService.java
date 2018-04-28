package com.crowdevents.reward;

import java.util.Optional;

import org.joda.money.Money;

public interface RewardService {
    Reward create(Long projectId, Integer limit, Money minimalContribution, String description);

    Optional<Reward> get(Long id);

    boolean delete(Long id);

    void changeLimit(Long id, Integer newLimit);

    void changeMinimalContribution(Long id, Money newMinimalContribution);

    void changeDescription(Long id, String newDescription);

    void changeDeliveryDate(Long id, String newDeliveryDate);

    void changeShippingLocation(Long id, String newShippingLocation);

    void update(Long id, Reward updatedReward);
}
