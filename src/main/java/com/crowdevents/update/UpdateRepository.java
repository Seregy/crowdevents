package com.crowdevents.update;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UpdateRepository extends PagingAndSortingRepository<Update, Long> {
    Page<Update> findAllByProjectId(Long projectId, Pageable pageable);
}
