package com.ecommerce.product.dto.request;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank(message = "카테고리 이름은 필수입니다")
        String name,
        @NotBlank(message = "카테고리 설명은 필수입니다")
        String description,
        @NotBlank(message = "카테고리 코드는 필수입니다")
        String code,
        List<CategoryTypeCreateRequest> categoryTypes
) {}
