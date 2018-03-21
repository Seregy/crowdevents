package com.crowdevents.category;

import java.util.Optional;
import java.util.UUID;

public interface CategoryService {
    Category create(String name, String description);
    Category create(String name, String description, UUID parentCategoryId);
    Optional<Category> get(UUID id);
    void delete(UUID id);
    void changeName(UUID id, String newName);
    void changeDescription(UUID id, String newDescription);
    void changeParentCategory(UUID id, UUID newParentCategoryId);
}
