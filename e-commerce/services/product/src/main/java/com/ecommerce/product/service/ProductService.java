package com.ecommerce.product.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.exception.ProductPurchaseException;
import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.dto.request.ProductPurchaseRequest;
import com.ecommerce.product.dto.request.ProductRequest;
import com.ecommerce.product.dto.response.CategoryResponse;
import com.ecommerce.product.dto.response.CategoryTypeResponse;
import com.ecommerce.product.dto.response.ProductPurchaseResponse;
import com.ecommerce.product.dto.response.ProductResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;
import com.ecommerce.product.entity.Product;
import com.ecommerce.product.mapper.ProductMapper;
import com.ecommerce.product.repository.ProductRepository;
import com.ecommerce.product.specification.ProductSpecification;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
    private final CategoryService categoryService;
	
    @PersistenceContext
	private EntityManager entityManager;
	
	/*
    public ProductResponse findById(UUID id) {
        return productRepository.findById(id)
                .map(mapper::toProductResponse)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 제품ID:: " + id));
    }
    */
    
	public List<ProductResponse> findAll(UUID categoryId, UUID typeId) {
        
		Specification<Product> spec = Specification.allOf();;

        if (categoryId != null) {
            spec = spec.and(ProductSpecification.hasCategoryId(categoryId));
        }
        if (typeId != null) {
            spec = spec.and(ProductSpecification.hasCategoryTypeId(typeId));
        }
    	
        return productRepository.findAll(spec)
                .stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }
    
	
	@Transactional
	public Product createProduct(ProductRequest request) {
		// 1. Category 조회 
        Category category = categoryService.getCategoryId(request.categoryId());

        // 2. CategoryType 검증
        CategoryType categoryType = category.getCategoryTypes().stream()
                .filter(t -> t.getId().equals(request.categoryTypeId()))
                .findFirst()
                .orElseThrow(() ->
                        new ResourceNotFoundException("CategoryType not found")
                );
        
        // 3. Product + Variants + Resources 모두 포함
        Product product = productMapper.toEntity(request, category, categoryType);

	    return productRepository.save(product);
	}

    /*
    @Transactional(rollbackFor = ProductPurchaseException.class)
    public List<ProductPurchaseResponse> purchaseProducts(List<ProductPurchaseRequest> request) {
        var productIds = request
			                .stream()
			                .map(ProductPurchaseRequest::productId)
			                .toList();
        var storedProducts = repository.findAllByIdInOrderById(productIds);
        if (productIds.size() != storedProducts.size()) {
            throw new ProductPurchaseException("존재하지 않는 품목이 한개 이상 존재");
        }
        var sortedRequest = request
				                .stream()
				                .sorted(Comparator.comparing(ProductPurchaseRequest::productId))
				                .toList();
        var purchasedProducts = new ArrayList<ProductPurchaseResponse>();
        for (int i = 0; i < storedProducts.size(); i++) {
            var product = storedProducts.get(i);
            var productRequest = sortedRequest.get(i);
            if (product.getAvailableQuantity() < productRequest.quantity()) {
                throw new ProductPurchaseException("재고가 충분하지 않은 제품ID:: " + productRequest.productId());
            }
            var newAvailableQuantity = product.getAvailableQuantity() - productRequest.quantity();
            product.setAvailableQuantity(newAvailableQuantity);
            repository.save(product);
            purchasedProducts.add(mapper.toproductPurchaseResponse(product, productRequest.quantity()));
        }
        return purchasedProducts;
    }
    */
	
	
}
