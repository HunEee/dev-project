package com.ecommerce.product.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductRequest(
        Integer id,
        @NotNull(message = "제품명은 필수입니다.")
        String name,
        @NotNull(message = "제품 설명은 필수입니다.")
        String description,
        @Positive(message = "양수여야 합니다.")
        double availableQuantity,
        @Positive(message = "양수여야 합니다.")
        BigDecimal price,
        @NotNull(message = "카테고리는 필수입니다.")
        Integer categoryId
) {
}