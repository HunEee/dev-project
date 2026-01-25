package com.ecommerce.product.dto.request;

public record ProductResourcesRequest(
        String name,
        String url,
        Boolean isPrimary,
        String type
) {}