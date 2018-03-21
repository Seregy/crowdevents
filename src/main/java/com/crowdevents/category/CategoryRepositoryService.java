package com.crowdevents.category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class CategoryRepositoryService implements CategoryService {
    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryRepositoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(String name, String description) {
        Category category = new Category(name, description);
        return categoryRepository.save(category);
    }

    @Override
    public Category create(String name, String description, UUID parentCategoryId) {
        Category parent = categoryRepository
                .findById(parentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent category id: " + parentCategoryId));
        Category category = new Category(name, description, parent);
        parent.getChildren().add(category);
        Category created = categoryRepository.save(category);
        categoryRepository.save(parent);
        return created;
    }

    @Override
    public Optional<Category> get(UUID id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(UUID id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void changeName(UUID id, String newName) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        category.setName(newName);
        categoryRepository.save(category);
    }

    @Override
    public void changeDescription(UUID id, String newDescription) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        category.setDescription(newDescription);
        categoryRepository.save(category);
    }

    @Override
    public void changeParentCategory(UUID id, UUID newParentCategoryId) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        Category parent = categoryRepository
                .findById(newParentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid parent category id: " + newParentCategoryId));
        category.setParent(parent);
        categoryRepository.save(category);
        categoryRepository.save(parent);
    }
}
