package com.ecommerce.product.dto.request;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record ProductRequest(
		
        @NotBlank(message = "제품명은 필수입니다.")
        @Size(max = 100, message = "제품명은 100자 이하여야 합니다.")
        String name,

        @NotBlank(message = "제품 설명은 필수입니다.")
        @Size(max = 500, message = "제품 설명은 500자 이하여야 합니다.")
        String description,

        @NotNull(message = "가격은 필수입니다.")
        @Positive(message = "가격은 양수여야 합니다.")
        BigDecimal price,

        @NotBlank(message = "브랜드는 필수입니다.")
        String brand,

        boolean isNewArrival,

        @Positive(message = "평점은 양수여야 합니다.")
        Float rating,

        @NotNull(message = "카테고리는 필수입니다.")
        UUID categoryId,

        @NotNull(message = "카테고리 타입은 필수입니다.")
        UUID categoryTypeId,

        List<ProductVariantRequest> variants,
        List<ProductResourcesRequest> resources
) {}
