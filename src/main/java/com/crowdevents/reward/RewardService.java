package com.crowdevents.reward;

import com.crowdevents.project.Project;
import org.joda.money.Money;

import java.util.Optional;
import java.util.UUID;

public interface RewardService {
    Reward create(UUID projectId, Integer limit, Money minimalContribution, String description);
    Optional<Reward> get(UUID id);
    void delete(UUID id);
    void changeLimit(UUID id, Integer newLimit);
    void changeMinimalContribution(UUID id, Money newMinimalContribution);
    void changeDescription(UUID id, String newDescription);
    void changeDeliveryDate(UUID id, String newDeliveryDate);
    void changeShippingLocation(UUID id, String newShippingLocation);
}
