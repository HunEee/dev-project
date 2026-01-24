package com.ecommerce.product.dto;

import java.util.UUID;

public record CategoryTypeRequest(
        UUID id,
        String name,
        String code,
        String description
) {}
