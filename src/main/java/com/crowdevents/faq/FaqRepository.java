package com.crowdevents.faq;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface FaqRepository extends PagingAndSortingRepository<Faq, UUID> {
}
