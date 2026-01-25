package com.ecommerce.product.dto.request;

public record ProductVariantRequest(
        String color,
        String size,
        Integer stockQuantity
) {}
