package com.ecommerce.product.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.dto.request.CategoryCreateRequest;
import com.ecommerce.product.dto.request.CategoryUpdateRequest;
import com.ecommerce.product.dto.response.CategoryResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.mapper.CategoryMapper;
import com.ecommerce.product.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    
    
    public CategoryResponse getCategory(UUID id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        return categoryMapper.toResponse(category);
    }
    
    public List<CategoryResponse> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    public Category createCategory(CategoryCreateRequest request) {
        Category category = categoryMapper.toEntity(request);
        return categoryRepository.save(category);
    }
    
    
    public Category updateCategory(CategoryUpdateRequest request, UUID categoryId) {
	    Category category = categoryRepository.findById(categoryId)
	            .orElseThrow(() ->
	                    new ResourceNotFoundException("Category not found with id " + categoryId)
	            );
	    categoryMapper.updateCategory(category, request);
	    // @Transactional 이라 save 없어도 되지만 명시적으로
	    return categoryRepository.save(category);

    }
    
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
    

	
}
