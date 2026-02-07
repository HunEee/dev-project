package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.ProductRequest;
import com.ecommerce.product.dto.request.ProductResourcesRequest;
import com.ecommerce.product.dto.request.ProductVariantRequest;
import com.ecommerce.product.dto.response.ProductResourcesResponse;
import com.ecommerce.product.dto.response.ProductResponse;
import com.ecommerce.product.dto.response.ProductVariantResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductResources;
import com.ecommerce.product.entity.ProductVariant;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductMapper {

    // ========================================    
    // =====  ProductRequest -> Entity  =====
    // ========================================
    public Product toEntity(ProductRequest request, Category category, CategoryType categoryType) {
        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .brand(request.brand())
                .rating(request.rating())
                .isNewArrival(request.isNewArrival())
                .slug(request.slug())
                .category(category)
                .categoryType(categoryType)
                .build();
        
        // Variants 매핑
        if (request.variants() != null) {
            product.updateVariants(
                    toVariants(request.variants(), product)
            );
        }

        // Resources 매핑
        if (request.resources() != null) {
            product.updateResources(
                    toResources(request.resources(), product)
            );
        }
        
        return product;
    }
     
    private List<ProductResources> toResources(List<ProductResourcesRequest> resourceRequest,Product product) {
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
    
    private List<ProductVariant> toVariants(List<ProductVariantRequest> variantRequest,Product product) {
        return variantRequest.stream()
                .map(dto -> ProductVariant.builder()
                        .color(dto.color())
                        .size(dto.size())
                        .stockQuantity(dto.stockQuantity())
                        .product(product)
                        .build()
                ).toList();
    }
    
    public void updateEntity(Product product,ProductRequest request) {
        // 기본 필드
        product.updateBasicInfo(
                request.name(),
                request.description(),
                request.price(),
                request.brand(),
                request.slug(),
                request.rating(),
                request.isNewArrival()
        );

        // Variants 매핑
        if (request.variants() != null) {
            product.updateVariants(
                    toVariants(request.variants(), product)
            );
        }
        // Resources 매핑
        if (request.resources() != null) {
            product.updateResources(
                    toResources(request.resources(), product)
            );
        }
    }
    
    
    
    // ========================================    
    // ===== Product -> ProductResponse  =====
    // ========================================
    // 리스트/상세 공용
    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getBrand(),
                product.getRating(),
                product.isNewArrival(),
                product.getSlug(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategoryType().getId(),
                product.getCategoryType().getName(),
                getThumbnail(product.getResources()),
                toVariantResponses(product.getProductVariants()),
                toResourceResponses(product.getResources())
        );
    }
    
    private String getThumbnail(List<ProductResources> resources) {
        if (resources == null) return null;

        return resources.stream()
                .filter(resource -> resource.getIsPrimary())
                .findFirst()
                .map(ProductResources::getUrl)
                .orElse(null);
    }
    
    private List<ProductVariantResponse> toVariantResponses(List<ProductVariant> variants) {
        if (variants == null) return List.of();

        return variants.stream()
                .map(variant -> new ProductVariantResponse(
                        variant.getId(),
                        variant.getColor(),
                        variant.getSize(),
                        variant.getStockQuantity()
                ))
                .toList();
    }
    
    private List<ProductResourcesResponse> toResourceResponses(List<ProductResources> resources) {
        if (resources == null) return List.of();

        return resources.stream()
                .map(resource -> new ProductResourcesResponse(
                        resource.getId(),
                        resource.getName(),
                        resource.getType(),
                        resource.getUrl(),
                        resource.getIsPrimary()
                ))
                .toList();
    }
    
    // 리스트
    public List<ProductResponse> toProductResponses(List<Product> products) {
        return products.stream()
                .map(this::toProductResponse)
                .toList();
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
