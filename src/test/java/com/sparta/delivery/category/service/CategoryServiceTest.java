package com.sparta.delivery.category.service;

import com.sparta.delivery.category.dto.CategoryResponse;
import com.sparta.delivery.category.dto.UpdateCategoryRequest;
import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("카테고리를 정상적으로 저장하는지 검증")
    void createCategory_ShouldSaveCategory() {
        String categoryName = "New Category";

        categoryService.createCategory(categoryName);

        verify(categoryRepository, times(1)).save(any(Category.class));
    }

    @Test
    @DisplayName("페이지된 카테고리 리스트를 반환하는지 검증")
    void getCategories_ShouldReturnPagedCategories() {
        Pageable pageable = PageRequest.of(0, 10);
        Category category = new Category("Sample Category");
        Page<Category> categories = new PageImpl<>(Collections.singletonList(category));
        when(categoryRepository.findAll(pageable)).thenReturn(categories);

        Page<CategoryResponse> result = categoryService.getCategories(pageable);

        assertThat(result.getTotalElements()).isEqualTo(1);
        assertThat(result.getContent().get(0).getName()).isEqualTo("Sample Category");
    }

    @Test
    @DisplayName("카테고리 이름을 정상적으로 업데이트하는지 검증")
    void updateCategory_ShouldUpdateCategoryName() {
        UUID categoryId = UUID.randomUUID();
        String newName = "Updated Category";
        UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest(newName);
        Category category = new Category("Old Category");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.updateCategory(categoryId, updateCategoryRequest);

        assertThat(category.getName()).isEqualTo(newName);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("존재하지 않는 카테고리를 업데이트하려 할 때 예외가 발생하는지 검증")
    void updateCategory_ShouldThrowException_WhenCategoryNotFound() {
        UUID categoryId = UUID.randomUUID();
        UpdateCategoryRequest updateCategoryRequest = new UpdateCategoryRequest("Updated Category");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                categoryService.updateCategory(categoryId, updateCategoryRequest));

        assertThat(exception.getResponseCode()).isEqualTo(ResponseCode.NOT_FOUND_CATEGORY);
        verify(categoryRepository, times(1)).findById(categoryId);
    }

    @Test
    @DisplayName("카테고리를 정상적으로 삭제하는지 검증")
    void deleteCategory_ShouldMarkCategoryAsDeleted() {
        UUID categoryId = UUID.randomUUID();
        Category category = new Category("Category to Delete");
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(category));

        categoryService.deleteCategory(categoryId);


        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());
        Optional<Category> foundCategory = categoryRepository.findById(categoryId);

        assertThat(foundCategory).isEmpty();
    }

    @Test
    @DisplayName("존재하지 않는 카테고리를 삭제하려 할 때 예외가 발생하는지 검증")
    void deleteCategory_ShouldThrowException_WhenCategoryNotFound() {
        UUID categoryId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () ->
                categoryService.deleteCategory(categoryId));

        assertThat(exception.getResponseCode()).isEqualTo(ResponseCode.NOT_FOUND_CATEGORY);
        verify(categoryRepository, times(1)).findById(categoryId);
    }
}