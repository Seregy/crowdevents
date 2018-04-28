package com.crowdevents.category;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;

public class CategoryRepositoryServiceTest {
    @Mock
    private CategoryRepository mockCategoryRepository;

    @InjectMocks
    private CategoryRepositoryService categoryService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void create_WithProperParams_ShouldCreateNewCategory() {
        Category mockedCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.save(Mockito.any()))
                .thenReturn(mockedCategory);

        Category result = categoryService.create("name", "description");

        assertEquals(mockedCategory, result);
    }

    @Test
    public void create_WithProperParentCategoryId_ShouldCreateCategoryWithParent() {
        Category mockedParentCategory = new Category("Parent", "Mocked parent category");
        Category mockedCategory = new Category("Mock", "Mocked category", mockedParentCategory);
        Mockito.when(mockCategoryRepository.save(Mockito.any()))
                .thenReturn(mockedCategory);
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockedParentCategory));

        Category result = categoryService.create("name", "description", 1L);

        assertEquals(mockedCategory, result);
        assertEquals(mockedParentCategory, result.getParent());
        assertFalse(result.getParent().getChildren().isEmpty());
    }

    @Test
    public void create_WithWrongParentCategoryId_ShouldThrowException() {
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.create("name", "description", 1L);
        });

        assertEquals("Invalid parent category id: 1",
                exception.getMessage());
    }

    @Test
    public void get_WithExistingId_ShouldReturnExistingCategory() {
        Category mockedCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockedCategory));

        Optional<Category> result = categoryService.get(1L);

        assertEquals(Optional.of(mockedCategory), result);
    }

    @Test
    public void get_WithWrongId_ShouldReturnEmptyValue() {
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.empty());

        Optional<Category> result = categoryService.get(1L);

        assertEquals(Optional.empty(), result);
    }

    @Test
    public void getAll_ShouldReturnAllCategories() {
        Category[] categories = {new Category("Category 1", "Description 1"),
                new Category("Category 2", "Description 2"),
                new Category("Category 3", "Description 3"),
                new Category("Category 4", "Description 4")};
        Mockito.when(mockCategoryRepository.findAll(Mockito.any(Pageable.class)))
                .thenReturn(new PageImpl<>(Arrays.asList(categories)));

        Page<Category> result = categoryService.getAll(PageRequest.of(0, 4));

        assertEquals(new PageImpl<>(Arrays.asList(categories)), result);
    }

    @Test
    public void delete_WithExistingId_ShouldDeleteCategory() {
        Mockito.when(mockCategoryRepository.existsById(1L))
                .thenReturn(true)
                .thenReturn(false);
        assertTrue(categoryService.delete(1L));

        Mockito.verify(mockCategoryRepository, Mockito.times(1))
                .deleteById(1L);
    }

    @Test
    public void changeName_WithProperParams_ShouldChangeName() {
        Category mockedCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockedCategory));

        assertEquals("Mock", mockedCategory.getName());
        categoryService.changeName(1L, "Changed name");
        assertEquals("Changed name", mockedCategory.getName());
    }

    @Test
    public void changeDescription_WithProperParams_ShouldChangeDescription() {
        Category mockedCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockedCategory));

        assertEquals("Mocked category", mockedCategory.getDescription());
        categoryService.changeDescription(1L, "Changed description");
        assertEquals("Changed description", mockedCategory.getDescription());
    }

    @Test
    public void changeParentCategory_WithProperParams_ShouldChangeParent() {
        Category mockedParentCategory = new Category("Parent", "Mocked parent category");
        Category mockedCategory = new Category("Mock", "Mocked category", mockedParentCategory);
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockedCategory));
        Category anotherParent = new Category("Another parent", "Another mocked parent category");
        Mockito.when(mockCategoryRepository.findById(2L))
                .thenReturn(Optional.of(anotherParent));

        assertEquals(mockedParentCategory, mockedCategory.getParent());
        categoryService.changeParentCategory(1L, 2L);
        assertEquals(anotherParent, mockedCategory.getParent());
    }

    @Test
    public void update_WithProperParams_ShouldUpdateCategory() {
        Category mockCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockCategory));
        Category mockParentCategory = new Category("Parent", "Mocked parent category");
        mockParentCategory.setId(2L);
        Mockito.when(mockCategoryRepository.findById(2L))
                .thenReturn(Optional.of(mockParentCategory));
        Category updatedCategory = new Category("New name", null, mockParentCategory);

        assertNull(mockCategory.getParent());
        categoryService.update(1L, updatedCategory);
        assertNull(mockCategory.getDescription());
        assertEquals("New name", mockCategory.getName());
        assertEquals(mockParentCategory, mockCategory.getParent());
    }

    @Test
    public void update_WithNullUpdatedCategory_ShouldThrowException() {
        Category mockCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockCategory));

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.update(1L, null);
        });

        assertEquals("Updated category must not be null",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongCategoryId_ShouldThrowException() {
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.empty());
        Category updatedCategory = new Category("New name", null, null);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.update(1L, updatedCategory);
        });

        assertEquals("Invalid category id: 1",
                exception.getMessage());
    }

    @Test
    public void update_WithWrongParentCategoryId_ShouldThrowException() {
        Category mockCategory = new Category("Mock", "Mocked category");
        Mockito.when(mockCategoryRepository.findById(1L))
                .thenReturn(Optional.of(mockCategory));
        Category mockParentCategory = new Category("Parent", "Mocked parent category");
        mockParentCategory.setId(2L);
        Mockito.when(mockCategoryRepository.findById(2L))
                .thenReturn(Optional.empty());
        Category newCategory = new Category("New name", null, mockParentCategory);

        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            categoryService.update(1L, newCategory);
        });

        assertEquals("Invalid category id: 2",
                exception.getMessage());
    }
}