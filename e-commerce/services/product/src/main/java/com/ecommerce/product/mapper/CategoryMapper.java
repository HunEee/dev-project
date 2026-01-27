package com.ecommerce.product.mapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.exception.ResourceNotFoundException;
import com.ecommerce.product.dto.request.CategoryCreateRequest;
import com.ecommerce.product.dto.request.CategoryTypeCreateRequest;
import com.ecommerce.product.dto.request.CategoryTypeUpdateRequest;
import com.ecommerce.product.dto.request.CategoryUpdateRequest;
import com.ecommerce.product.dto.response.CategoryResponse;
import com.ecommerce.product.dto.response.CategoryTypeResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;

@Service
public class CategoryMapper {
	
	public Category toEntity(CategoryCreateRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .code(request.code())
                .build();

        if (request.categoryTypes() != null) {
            category.setCategoryTypes(
                    toCategoryTypeList(request.categoryTypes(), category)
            );
        }
        return category;
    }
	
	private List<CategoryType> toCategoryTypeList(List<CategoryTypeCreateRequest> categoryTypeList,Category category) {
	        return categoryTypeList.stream()
	                .map(dto -> {
	                    CategoryType categoryType = new CategoryType();
	                    categoryType.setCode(dto.code());
	                    categoryType.setName(dto.name());
	                    categoryType.setDescription(dto.description());
	                    categoryType.setCategory(category); // 연관관계 주입
	                    return categoryType;
	                })
	                .collect(Collectors.toList());
	}
	
	
    /* =========================
       Entity → Response
     ========================= */
    
    public CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName(),
                category.getCode(),
                category.getDescription(),
                category.getCategoryTypes() == null
                        ? List.of()
                        : category.getCategoryTypes()
                                  .stream()
                                  .map(this::toCategoryTypeResponse)
                                  .toList()
        );
    }
    
    

    private CategoryTypeResponse toCategoryTypeResponse(CategoryType type) {
        return new CategoryTypeResponse(
                type.getId(),
                type.getName(),
                type.getCode(),
                type.getDescription()
        );
    }
    
    /* =========================
    	수정 매퍼
  	========================= */

    public void updateCategory(Category category, CategoryUpdateRequest request) {
        if (request.name() != null) {
            category.setName(request.name());
        }
        if (request.code() != null) {
            category.setCode(request.code());
        }
        if (request.description() != null) {
            category.setDescription(request.description());
        }

        if (request.categoryTypes() == null) {
            return;
        }

        List<CategoryType> managedTypes = category.getCategoryTypes();

        // 1. clear 전에 기존 엔티티를 Map으로 보관
        Map<UUID, CategoryType> existingTypeMap = managedTypes.stream()
                .filter(t -> t.getId() != null)
                .collect(Collectors.toMap(CategoryType::getId, t -> t));

        // 2. Hibernate가 관리하는 컬렉션은 유지 + 내용만 제거
        managedTypes.clear();

        // 3. 재구성
        for (CategoryTypeUpdateRequest typeReq : request.categoryTypes()) {
            CategoryType type;
            
            if (typeReq.id() != null) {
                type = existingTypeMap.get(typeReq.id());
                if (type == null) {
                    throw new ResourceNotFoundException(
                            "CategoryType not found with id " + typeReq.id()
                    );
                }
            } else {
                type = CategoryType.builder()
                        .category(category)
                        .build();
            }
            
            type.setName(typeReq.name());
            type.setCode(typeReq.code());
            type.setDescription(typeReq.description());
            managedTypes.add(type);
        }
    }
    
    
    
}
