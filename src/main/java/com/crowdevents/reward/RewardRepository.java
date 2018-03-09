package com.crowdevents.reward;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface RewardRepository extends PagingAndSortingRepository<Reward, UUID> {
}
