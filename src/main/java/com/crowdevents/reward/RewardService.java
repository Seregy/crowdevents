package com.crowdevents.reward;

import java.util.Optional;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RewardService {
    Reward create(Long projectId, Integer limit, Money minimalContribution, String description);

    Optional<Reward> get(Long id);

    Page<Reward> getAll(Pageable pageable);

    Page<Reward> getAllByProject(Long projectId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Reward updatedReward);
}
