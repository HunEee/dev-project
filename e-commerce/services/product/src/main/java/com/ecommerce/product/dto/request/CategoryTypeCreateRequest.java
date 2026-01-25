package com.ecommerce.product.dto.request;

public record CategoryTypeCreateRequest(
        String name,
        String code,
        String description
) {}
