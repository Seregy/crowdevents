package com.crowdevents.category;

import com.crowdevents.core.web.PageResource;
import com.crowdevents.core.web.Views;
import com.fasterxml.jackson.annotation.JsonView;

import java.net.URI;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;



@RestController
@RequestMapping("v0/categories")
@CrossOrigin
public class CategoryController {
    private CategoryService categoryService;
    private ModelMapper modelMapper;

    @Autowired
    public CategoryController(CategoryService categoryService, ModelMapper modelMapper) {
        this.categoryService = categoryService;
        this.modelMapper = modelMapper;
    }

    /**
     * Returns page with categories inside it.
     *
     * @param pageNumber number of page
     * @param limit amount of items on page
     * @return page with categories
     */
    @JsonView(Views.Minimal.class)
    @GetMapping
    public PageResource<CategoryResource> getAllCategories(
            @RequestParam(name = "page", defaultValue = "0") int pageNumber,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        PageRequest pageRequest = PageRequest.of(pageNumber, limit);
        Page<Category> resultPage = categoryService.getAll(pageRequest);

        return new PageResource<>(
                resultPage.map((category) -> modelMapper.map(category, CategoryResource.class)));
    }

    /**
     * Returns specific category.
     *
     * @param id id of the category to be returned
     * @return response with http status 204 with category inside the body or 404 if it wasn't found
     */
    @JsonView(Views.Detailed.class)
    @GetMapping(value = "/{id}")
    public ResponseEntity<CategoryResource> getCategory(@PathVariable("id") Long id) {
        return categoryService.get(id)
                .map(category -> ResponseEntity.ok(
                        modelMapper.map(category, CategoryResource.class)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Creates new category.
     *
     * @param newCategory category to be created
     * @param servletRequest information about request
     * @return response with http status 201 and link to the category in the header
     */
    @JsonView(Views.Detailed.class)
    @PostMapping
    public ResponseEntity createCategory(@RequestBody CategoryResource newCategory,
                                        HttpServletRequest servletRequest) {
        Category createdCategory;
        if (newCategory.getParent() != null) {
            createdCategory = categoryService.create(newCategory.getName(),
                    newCategory.getDescription(),
                    newCategory.getParent().getId());
        } else {
            createdCategory = categoryService.create(newCategory.getName(),
                    newCategory.getDescription());
        }
        URI uri = ServletUriComponentsBuilder.fromServletMapping(servletRequest)
                .path("/v0/categories/{id}")
                .buildAndExpand(createdCategory.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    /**
     * Updates existing category.
     *
     * @param id id of the category to update
     * @param patchValues values to update
     * @return response with http status 204 or 404 if the category wasn't found
     */
    @JsonView(Views.Detailed.class)
    @PostMapping(value = "/{id}")
    public ResponseEntity updateCategory(@PathVariable("id") Long id,
                                        @RequestBody Map<String, Object> patchValues) {
        Optional<Category> category = categoryService.get(id);
        if (category.isPresent()) {
            CategoryResource categoryResource = modelMapper.map(
                    category.get(), CategoryResource.class);
            modelMapper.map(patchValues, categoryResource);
            categoryService.update(id, modelMapper.map(categoryResource, Category.class));
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Deletes existing category.
     *
     * @param id id of the category to delete
     * @return response with http status 204 or 404 if the category wasn't found
     */
    @DeleteMapping(value = "/{id}")
    public ResponseEntity deleteCategory(@PathVariable("id") Long id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

}
