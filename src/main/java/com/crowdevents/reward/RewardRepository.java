package com.crowdevents.reward;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RewardRepository extends PagingAndSortingRepository<Reward, Long> {
    Page<Reward> findAllByProjectId(Long projectId, Pageable pageable);
}
