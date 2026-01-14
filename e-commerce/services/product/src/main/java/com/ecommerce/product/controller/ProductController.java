package com.ecommerce.product.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.ProductPurchaseRequest;
import com.ecommerce.product.dto.ProductPurchaseResponse;
import com.ecommerce.product.dto.ProductRequest;
import com.ecommerce.product.dto.ProductResponse;
import com.ecommerce.product.service.ProductService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

	private final ProductService service;
	
	/*
    @GetMapping("/{product-id}")
    public ResponseEntity<ProductResponse> findById(@PathVariable("product-id") UUID productId) {
        return ResponseEntity.ok(service.findById(productId));
    }
    */

    //파라미터 없으면 → 전체, 파라미터 있으면 → 필터링
    @GetMapping
    public ResponseEntity<List<ProductResponse>> findAll(    
    		@RequestParam(required = false) UUID categoryId,
    	    @RequestParam(required = false) UUID typeId
	) {
        return ResponseEntity.ok(service.findAll(categoryId, typeId));
    }
    
    // record에 Validation 붙였으면 파라미터에 @Valid 필수
    @PostMapping
    public ResponseEntity<UUID> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(service.createProduct(request));
    }
    
  /*  
    @PostMapping("/purchase")
    public ResponseEntity<List<ProductPurchaseResponse>> purchaseProducts(@RequestBody List<ProductPurchaseRequest> request) {
        return ResponseEntity.ok(service.purchaseProducts(request));
    }
*/


	
	
}
