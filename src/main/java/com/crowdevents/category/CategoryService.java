package com.crowdevents.category;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Category create(String name, String description);

    Category create(String name, String description, Long parentCategoryId);

    Optional<Category> get(Long id);

    Page<Category> getAll(Pageable pageable);

    boolean delete(Long id);

    void update(Long id, Category updatedCategory);
}
