package com.crowdevents.reward;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface RewardRepository extends PagingAndSortingRepository<Reward, UUID> {
}
