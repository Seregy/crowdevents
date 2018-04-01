package com.crowdevents.update;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface UpdateRepository extends PagingAndSortingRepository<Update, UUID> {
}
