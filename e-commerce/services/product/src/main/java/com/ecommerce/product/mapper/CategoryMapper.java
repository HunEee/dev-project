package com.ecommerce.product.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.ecommerce.product.dto.request.CategoryCreateRequest;
import com.ecommerce.product.dto.request.CategoryTypeCreateRequest;
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
    
    
}
