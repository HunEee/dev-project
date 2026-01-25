package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.ProductRequest;
import com.ecommerce.product.dto.response.ProductPurchaseResponse;
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
	
    private final ProductVariantMapper productVariantMapper;
    private final ProductResourcesMapper productResourcesMapper;
	
    public Product toEntity(
            ProductRequest request,
            Category category,
            CategoryType categoryType
    ) {
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
        if (request.variants() != null && !request.variants().isEmpty()) {
            List<ProductVariant> variants =
                    productVariantMapper.toEntities(request.variants(), product);
            product.setProductVariants(variants);
        }

        // Resources 매핑
        if (request.resources() != null && !request.resources().isEmpty()) {
            List<ProductResources> resources =
                    productResourcesMapper.toEntities(request.resources(), product);
            product.setResources(resources);
        }

        return product;
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
    public ProductResponse toProductResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getAvailableQuantity(),
                product.getPrice(),
                product.getCategory().getId(),
                product.getCategory().getName(),
                product.getCategory().getDescription()
        );
    }
*/    
    
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
