package com.crowdevents.category;

import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.UUID;

public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID> {
}
