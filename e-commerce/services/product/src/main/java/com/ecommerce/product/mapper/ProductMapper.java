package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.ProductRequest;
import com.ecommerce.product.dto.request.ProductResourcesRequest;
import com.ecommerce.product.dto.request.ProductVariantRequest;
import com.ecommerce.product.dto.response.ProductResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductResources;
import com.ecommerce.product.entity.ProductVariant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    public Product toEntity(ProductRequest request, Category category, CategoryType categoryType) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .brand(request.brand())
                .rating(request.rating())
                .isNewArrival(request.isNewArrival())
                .category(category)
                .categoryType(categoryType)
                .build();
        
        // Variant 매핑
        if (request.variants() != null) {
            product.setProductVariants(
                    toVariants(request.variants(), product)
            );
        }

        // Resource 매핑
        if (request.resources() != null) {
            product.setResources(
                    toResources(request.resources(), product)
            );
        }
        
        return product;
    }
     
    public List<ProductResources> toResources(List<ProductResourcesRequest> resourceRequest,Product product) {
        return resourceRequest.stream()
                .map(dto -> ProductResources.builder()
                		.name(dto.name())
                		.type(dto.type())
                		.url(dto.url())
                		.isPrimary(dto.isPrimary())
                        .product(product)
                        .build()
                ).toList();
    }
    
    public List<ProductVariant> toVariants(List<ProductVariantRequest> variantRequest,Product product) {
        return variantRequest.stream()
                .map(dto -> ProductVariant.builder()
                        .color(dto.color())
                        .size(dto.size())
                        .stockQuantity(dto.stockQuantity())
                        .product(product)
                        .build()
                ).toList();
    }
    
    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getBrand(),
                product.getRating(),
                product.isNewArrival(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategoryType().getId(),
                product.getCategoryType().getName()
        );
    }
     
    
/*
    public ProductPurchaseResponse toproductPurchaseResponse(Product product, double quantity) {
        return new ProductPurchaseResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                quantity
        );
    }
  */  
    
    
    
}
