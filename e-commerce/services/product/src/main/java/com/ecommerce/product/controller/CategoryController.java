package com.ecommerce.product.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.product.dto.request.CategoryCreateRequest;
import com.ecommerce.product.dto.request.CategoryUpdateRequest;
import com.ecommerce.product.dto.response.CategoryResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.service.CategoryService;

//import io.swagger.v3.oas.annotations.parameters.RequestBody;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;
	
    // 한 건 조회
    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable(value = "id",required = true) UUID categoryId) {
        return ResponseEntity.ok(categoryService.getCategory(categoryId));
    }
    
    // 전체 조회
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }
    
    // 생성 
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody @Valid CategoryCreateRequest request, HttpServletRequest httpRequest){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(categoryService.createCategory(request));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryUpdateRequest request, @PathVariable(value = "id",required = true) UUID categoryId){
    	categoryService.updateCategory(request,categoryId);
        return ResponseEntity.ok().build();
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable(value = "id",required = true) UUID categoryId){
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.noContent().build();
    }
	
}
