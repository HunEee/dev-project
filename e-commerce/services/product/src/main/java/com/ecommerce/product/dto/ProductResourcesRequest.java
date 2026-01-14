package com.ecommerce.product.dto;

public record ProductResourcesRequest(
        String name,
        String url,
        Boolean isPrimary,
        String type
) {}