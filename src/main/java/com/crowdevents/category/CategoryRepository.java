package com.crowdevents.category;

import java.util.UUID;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface CategoryRepository extends PagingAndSortingRepository<Category, UUID> {
}
