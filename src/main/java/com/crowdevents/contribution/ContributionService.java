package com.crowdevents.contribution;

import java.util.Optional;

import org.joda.money.Money;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ContributionService {
    Contribution contribute(Long personId, Long projectId, Money money, Long rewardId,
                            String paymentId);

    Optional<Contribution> get(Long id);

    Page<Contribution> getAll(Pageable pageable);

    Page<Contribution> getAllByProject(Long projectId, Pageable pageable);

    Page<Contribution> getAllByPerson(Long personId, Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Contribution updatedContribution);
}
