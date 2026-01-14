package com.ecommerce.product.dto;

import java.util.UUID;

public record ProductResourcesResponse(
        UUID id,
        String name,
        String url,
        Boolean isPrimary,
        String type
) {}
