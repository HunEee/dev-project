package com.ecommerce.product.dto;

import java.util.List;

public record CategoryRequest(
        String name,
        String description,
        String code,
        List<CategoryTypeRequest> categoryTypes
) {}
