package com.ecommerce.product.dto.request;

import java.util.List;

public record CategoryUpdateRequest(
        String name,
        String description,
        String code,
        List<CategoryTypeUpdateRequest> categoryTypes
) {}
