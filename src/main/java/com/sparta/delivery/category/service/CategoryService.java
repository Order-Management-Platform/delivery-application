package com.sparta.delivery.category.service;

import com.sparta.delivery.category.dto.CategoryResponse;
import com.sparta.delivery.category.dto.UpdateCategoryRequest;
import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;


    @Transactional
    public void createCategory(String categoryName) {
        Category category = new Category(categoryName);
        categoryRepository.save(category);
    }


    public Page<CategoryResponse> getCategories(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(CategoryResponse::of);
    }

    @Transactional
    public void updateCategory(UpdateCategoryRequest updateCategoryRequest) {
        Category category = categoryRepository.findById(updateCategoryRequest.getId())
                .orElseThrow(() -> new IllegalArgumentException("카테고리를 찾지 못했습니다."));
        category.updateName(updateCategoryRequest.getName());
    }

    @Transactional
    public void deleteCategory(UUID categoryId, UUID userId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 카테고리 입니다."));
        category.delete(userId);
        category.markDeleted(userId);
    }
}
