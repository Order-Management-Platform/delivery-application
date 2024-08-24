package com.sparta.delivery.category.service;

import com.sparta.delivery.category.dto.CategoryResponse;
import com.sparta.delivery.category.dto.UpdateCategoryRequest;
import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
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
                .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾지 못했습니다."));
        category.updateName(updateCategoryRequest.getName());
    }

    public void deleteCategory(UUID categoryId) {
        boolean isExists = categoryRepository.existsById(categoryId);
        if (!isExists) {
            throw new EntityNotFoundException("존재하지 않는 카테고리 ID 입니다.");
        }
        categoryRepository.deleteById(categoryId);
    }
}
