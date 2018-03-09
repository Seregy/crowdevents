package com.crowdevents.update;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface UpdateRepository extends PagingAndSortingRepository<Update, UUID> {
}
