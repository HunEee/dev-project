package com.ecommerce.product.mapper;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.CategoryRequest;
import com.ecommerce.product.dto.CategoryResponse;
import com.ecommerce.product.dto.CategoryTypeResponse;
import com.ecommerce.product.entity.Category;
import com.ecommerce.product.entity.CategoryType;

@Service
public class CategoryMapper {
	
	public Category toEntity(CategoryRequest request) {
        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .code(request.code())
                .build();

        if (request.categoryTypes() != null) {
            List<CategoryType> types = request.categoryTypes().stream()
                    .map(type -> CategoryType.builder()
                            .name(type.name())
                            .code(type.code())
                            .description(type.description())
                            .category(category)
                            .build())
                    .toList();

            // 연관관계 주입
            category.setProducts(null); // 명시적으로 product는 건드리지 않음
        }

        return category;
    }

    /* =========================
       Entity → Response
     ========================= */
	/*
	 * public CategoryResponse toResponse(Category category,List<CategoryType>
	 * categoryTypes) { return new CategoryResponse( category.getId(),
	 * category.getName(), category.getDescription(), category.getCode(),
	 * mapToTypeResponses(categoryTypes) ); }
	 */
    
    
    
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
    
    
}
