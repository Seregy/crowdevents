package com.crowdevents.faq;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface FaqRepository extends PagingAndSortingRepository<Faq, UUID> {
}
