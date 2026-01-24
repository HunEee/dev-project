package com.ecommerce.product.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.product.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
	
}
