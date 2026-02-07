package com.ecommerce.product.dto.response;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String brand,
        Float rating,
        boolean newArrival,
        String slug,

        UUID categoryId,
        String categoryName,

        UUID categoryTypeId,
        String categoryTypeName,
        
        String thumbnail,
        List<ProductVariantResponse> variants,
        List<ProductResourcesResponse> resources

) {}
