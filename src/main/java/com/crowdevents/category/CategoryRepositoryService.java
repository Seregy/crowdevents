package com.crowdevents.category;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<Category> getAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public boolean delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        }

        return !categoryRepository.existsById(id);

    }

    @Override
    public void update(Long id, Category updatedCategory) {
        if (updatedCategory == null) {
            throw new IllegalArgumentException("Updated category must not be null");
        }

        Category category = getCategory(id);
        category.setName(updatedCategory.getName());
        category.setDescription(updatedCategory.getDescription());
        if (updatedCategory.getParent() != null) {
            Category parentCategory = getCategory(updatedCategory.getParent().getId());
            category.setParent(parentCategory);
        }
        categoryRepository.save(category);
    }

    private Category getCategory(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid category id: " + id));
    }
}
