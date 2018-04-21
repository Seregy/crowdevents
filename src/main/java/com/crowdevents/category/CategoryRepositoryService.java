package com.crowdevents.category;

import java.util.Optional;


import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


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
    public Category create(String name, String description, Long parentCategoryId) {
        Category parent = categoryRepository
                .findById(parentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid parent category id: " + parentCategoryId));
        Category category = new Category(name, description, parent);
        parent.addChild(category);
        return categoryRepository.save(category);
    }

    @Override
    public Optional<Category> get(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public void changeName(Long id, String newName) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        category.setName(newName);
        categoryRepository.save(category);
    }

    @Override
    public void changeDescription(Long id, String newDescription) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        category.setDescription(newDescription);
        categoryRepository.save(category);
    }

    @Override
    public void changeParentCategory(Long id, Long newParentCategoryId) {
        Category category = categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
        Category parent = categoryRepository
                .findById(newParentCategoryId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Invalid parent category id: " + newParentCategoryId));
        category.setParent(parent);
        categoryRepository.save(category);
    }
}
