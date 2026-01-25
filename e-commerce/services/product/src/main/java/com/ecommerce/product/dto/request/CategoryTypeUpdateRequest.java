package com.ecommerce.product.dto.request;

import java.util.UUID;

public record CategoryTypeUpdateRequest(
        UUID id,
        String name,
        String code,
        String description
) {}
