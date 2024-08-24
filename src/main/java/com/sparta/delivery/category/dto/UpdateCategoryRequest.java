package com.sparta.delivery.category.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class UpdateCategoryRequest {

    private UUID id;
    private String name;

}
