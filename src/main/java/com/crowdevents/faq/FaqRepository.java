package com.crowdevents.faq;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface FaqRepository extends PagingAndSortingRepository<Faq, Long> {
    Page<Faq> findAllByProjectId(Long projectId, Pageable pageable);
}
