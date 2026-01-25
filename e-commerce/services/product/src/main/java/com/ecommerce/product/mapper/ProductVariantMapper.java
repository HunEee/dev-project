package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.ProductVariantRequest;
import com.ecommerce.product.dto.response.ProductVariantResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductVariant;

@Service
public class ProductVariantMapper {

    public List<ProductVariant> toProductVariants(
            List<ProductVariantRequest> requests,
            Product product
    ) {
        return requests.stream()
                .map(req -> toProductVariant(req, product))
                .toList();
    }

    private ProductVariant toProductVariant(
            ProductVariantRequest request,
            Product product
    ) {
        return ProductVariant.builder()
                .color(request.color())
                .size(request.size())
                .stockQuantity(request.stockQuantity())
                .product(product)
                .build();
    }
    
    public List<ProductVariant> toEntities(
            List<ProductVariantRequest> requests,
            Product product
    ) {
        if (requests == null) return List.of();

        return requests.stream()
                .map(request -> ProductVariant.builder()
                        .color(request.color())
                        .size(request.size())
                        .stockQuantity(request.stockQuantity())
                        .product(product) 
                        .build()
                )
                .toList();
    }

    public List<ProductVariantResponse> toResponses(
            List<ProductVariant> variants
    ) {
        if (variants == null) return List.of();

        return variants.stream()
                .map(v -> new ProductVariantResponse(
                        v.getId(),
                        v.getColor(),
                        v.getSize(),
                        v.getStockQuantity()
                ))
                .toList();
    }
    
}