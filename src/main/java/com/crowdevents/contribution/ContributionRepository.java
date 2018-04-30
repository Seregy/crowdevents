package com.crowdevents.contribution;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ContributionRepository extends PagingAndSortingRepository<Contribution, Long> {
    Page<Contribution> findAllByProjectId(Long projectId, Pageable pageable);

    Page<Contribution> findAllByContributorId(Long contributorId, Pageable pageable);
}
