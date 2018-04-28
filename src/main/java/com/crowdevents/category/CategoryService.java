package com.crowdevents.category;

import java.util.Optional;

public interface CategoryService {
    Category create(String name, String description);

    Category create(String name, String description, Long parentCategoryId);

    Optional<Category> get(Long id);

    boolean delete(Long id);

    void changeName(Long id, String newName);

    void changeDescription(Long id, String newDescription);

    void changeParentCategory(Long id, Long newParentCategoryId);

    void update(Long id, Category updatedCategory);
}
