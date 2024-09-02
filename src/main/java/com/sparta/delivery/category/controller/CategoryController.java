package com.sparta.delivery.category.controller;

import com.sparta.delivery.category.dto.CategoryResponse;
import com.sparta.delivery.category.dto.UpdateCategoryRequest;
import com.sparta.delivery.category.service.CategoryService;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.common.dto.ResponsePageDto;
import com.sparta.delivery.user.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_CATEGORY_CREATE));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping
    public ResponseEntity<ResponsePageDto<CategoryResponse>> getCategories(@PageableDefault(page = 0,
            size = 10,
            sort = "createdAt",
            direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CategoryResponse> categories = categoryService.getCategories(pageable);
        return ResponseEntity.ok(ResponsePageDto.of(ResponseCode.SUCC_CATEGORY_LIST_GET,categories));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @PutMapping("/{categoryId}")
    public ResponseEntity<ResponseDto> updateCategory(@PathVariable("categoryId")UUID categoryId,
                                                      @RequestBody UpdateCategoryRequest updateCategoryRequest) {
        categoryService.updateCategory(categoryId, updateCategoryRequest);
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_CATEGORY_MODIFY));
    }


    @PreAuthorize("hasRole('MANAGER')")
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ResponseDto> deleteCategory(@PathVariable("categoryId") UUID categoryId,
                                                      @AuthenticationPrincipal UserDetailsImpl userDetails)
    {
        categoryService.deleteCategory(categoryId, userDetails.getUserId());
        return ResponseEntity.ok(ResponseDto.of(ResponseCode.SUCC_CATEGORY_DELETE));
    }
}
