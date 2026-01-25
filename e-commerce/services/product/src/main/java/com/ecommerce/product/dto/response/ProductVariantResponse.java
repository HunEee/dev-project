package com.ecommerce.product.dto.response;

import java.util.UUID;

public record ProductVariantResponse(
        UUID id,
        String color,
        String size,
        Integer stockQuantity
) {}
