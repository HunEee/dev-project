package com.ecommerce.product.dto.response;

import java.util.List;
import java.util.UUID;

public record CategoryResponse(
        UUID id,
        String name,
        String description,
        String code,
        List<CategoryTypeResponse> categoryTypes
) {}