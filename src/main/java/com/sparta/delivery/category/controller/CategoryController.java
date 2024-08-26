package com.sparta.delivery.category.controller;

import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import com.sparta.delivery.category.dto.CategoryResponse;
import com.sparta.delivery.category.dto.UpdateCategoryRequest;
import com.sparta.delivery.category.service.CategoryService;
import com.sparta.delivery.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/category")
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasRole('MANAGER')")
    @PostMapping
    public ResponseEntity<?> createCategory(@RequestParam("name") String categoryName) {
        categoryService.createCategory(categoryName);
        return ResponseEntity.ok(new ResponseDto(200, "카테고리 생성 성공"));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<Page<CategoryResponse>> getCategories(@PageableDefault(page = 0,
            size = 10,
            sort = "createAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(categories);
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDto> updateCategory(@PathVariable("categoryId")UUID categoryId,
                                                      @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return ResponseEntity.ok(new ResponseDto(200, "카테고리 수정 성공"));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable("categoryId") UUID categoryId,
                                                      @AuthenticationPrincipal UserDetails userDetails)
    {
        categoryService.deleteCategory(categoryId, ((UserDetailsImpl) userDetails).getUserId());
        return ResponseEntity.ok(new ResponseDto(200, "카테고리 삭제 성공"));
    }
}
