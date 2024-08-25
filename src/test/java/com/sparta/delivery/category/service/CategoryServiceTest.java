package com.sparta.delivery.category.service;

import com.sparta.delivery.category.entity.Category;
import com.sparta.delivery.category.repository.CategoryRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class CategoryServiceTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional
    @Rollback(value = false)
    @Test
    void softDeleteTest() {
        Category category = new Category("한식");
        Category save = categoryRepository.save(category);
        save.delete(UUID.randomUUID());

    }

    @Test
    void deletedTest() {
        boolean present = categoryRepository.findById(UUID.fromString("ee076ece-c65f-4a40-aee7-7d8fd5e9dae6")).isPresent();
        Assertions.assertFalse(present);
    }

}