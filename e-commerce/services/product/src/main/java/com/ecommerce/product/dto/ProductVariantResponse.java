package com.ecommerce.product.dto;

import java.util.UUID;

public record ProductVariantResponse(
        UUID id,
        String color,
        String size,
        Integer stockQuantity
) {}
