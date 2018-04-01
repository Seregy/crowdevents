package com.crowdevents.contribution;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContributionRepository extends PagingAndSortingRepository<Contribution, UUID> {
}
