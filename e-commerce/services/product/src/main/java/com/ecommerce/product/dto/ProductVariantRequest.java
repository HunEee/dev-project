package com.ecommerce.product.dto;

public record ProductVariantRequest(
        String color,
        String size,
        Integer stockQuantity
) {}
