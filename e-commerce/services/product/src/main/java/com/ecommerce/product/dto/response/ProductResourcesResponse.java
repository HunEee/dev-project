package com.ecommerce.product.dto.response;

import java.util.UUID;

public record ProductResourcesResponse(
        UUID id,
        String name,
        String url,
        String type,
        Boolean isPrimary
) {}
