package com.ecommerce.product.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.dto.CategoryRequest;
import com.ecommerce.product.dto.CategoryResponse;
import com.ecommerce.product.dto.CategoryTypeRequest;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;
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

    public UUID createCategory(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .code(request.code())
                .description(request.description())
                .build();

        return categoryRepository.save(category).getId();
    }
    
    
    public void updateCategory(CategoryRequest request, UUID categoryId) {

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category not found with id " + categoryId)
                );

        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.code() != null) {
            category.setCode(request.code());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }

        // CategoryType 업데이트
        if (request.categoryTypes() != null) {
            List<CategoryType> existingTypes = category.getCategoryTypes();
            List<CategoryType> updatedTypes = new ArrayList<>();

            for (CategoryTypeRequest typeReq : request.categoryTypes()) {

                if (typeReq.id() != null) {
                    CategoryType type = existingTypes.stream()
                            .filter(t -> t.getId().equals(typeReq.id()))
                            .findFirst()
                            .orElseThrow(() ->
                                    new ResourceNotFoundException("CategoryType not found: " + typeReq.id())
                            );

                    type.setName(typeReq.name());
                    type.setCode(typeReq.code());
                    type.setDescription(typeReq.description());
                    updatedTypes.add(type);

                } else {
                    CategoryType newType = CategoryType.builder()
                            .name(typeReq.name())
                            .code(typeReq.code())
                            .description(typeReq.description())
                            .category(category)
                            .build();

                    updatedTypes.add(newType);
                }
            }

            category.setCategoryTypes(updatedTypes);
        }
        // @Transactional 이라 save 없어도 되지만 명시적으로
        categoryRepository.save(category);
    }
    
    public void deleteCategory(UUID categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new ResourceNotFoundException("Category not found with id " + categoryId);
        }
        categoryRepository.deleteById(categoryId);
    }
    

	
}
