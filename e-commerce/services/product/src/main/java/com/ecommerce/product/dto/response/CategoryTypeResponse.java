package com.ecommerce.product.dto.response;

import java.util.UUID;

public record CategoryTypeResponse(
        UUID id,
        String name,
        String code,
        String description
) {}
