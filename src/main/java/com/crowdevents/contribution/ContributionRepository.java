package com.crowdevents.contribution;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface ContributionRepository extends PagingAndSortingRepository<Contribution, UUID> {
}
