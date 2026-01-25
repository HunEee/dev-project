package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.ProductResourcesRequest;
import com.ecommerce.product.dto.response.ProductResourcesResponse;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.entity.ProductResources;

@Service
public class ProductResourcesMapper {

    public List<ProductResources> toProductResources(
            List<ProductResourcesRequest> requests,
            Product product
    ) {
        return requests.stream()
                .map(req -> toProductResource(req, product))
                .toList();
    }

    private ProductResources toProductResource(
    		ProductResourcesRequest request,
            Product product
    ) {
        return ProductResources.builder()
                .name(request.name())
                .url(request.url())
                .type(request.type())
                .isPrimary(request.isPrimary())
                .product(product)
                .build();
    }
    
    public List<ProductResources> toEntities(
            List<ProductResourcesRequest> requests,
            Product product
    ) {
        return requests.stream()
                .map(request -> ProductResources.builder()
                        .name(request.name())
                        .url(request.url())
                        .isPrimary(request.isPrimary())
                        .type(request.type())
                        .product(product)  
                        .build()
                )
                .toList();
    }

    public List<ProductResourcesResponse> toResponses(
            List<ProductResources> resources
    ) {
        if (resources == null) return List.of();

        return resources.stream()
                .map(r -> new ProductResourcesResponse(
                        r.getId(),
                        r.getName(),
                        r.getUrl(),
                        r.getIsPrimary(),
                        r.getType()
                ))
                .toList();
    }
}
