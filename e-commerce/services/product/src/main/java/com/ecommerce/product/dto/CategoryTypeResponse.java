package com.ecommerce.product.dto;

import java.util.UUID;

public record CategoryTypeResponse(
        UUID id,
        String name,
        String description
) {}
