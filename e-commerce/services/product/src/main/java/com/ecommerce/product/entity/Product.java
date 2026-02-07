package com.ecommerce.product.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
//@Setter
@Entity
@Table(name = "product")
public class Product {

    /* =========================
     * Primary Key
     * ========================= */
    @Id
    @GeneratedValue
    private UUID id;
    
    /* =========================
     * Basic Info
     * ========================= */
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @Column(nullable = false)
    private BigDecimal price;
    @Column(nullable = false)
    private String brand;
    @Column
    private Float rating;
    @Column(nullable = false)
    private boolean isNewArrival;
    @Column(nullable = true,unique = true)
    private String slug;
    
    /* =========================
     * Timestamp
     * ========================= */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    /* =========================
     * Relationships
     * ========================= */

    // 상품은 카테고리에 속함
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id", nullable = false)
    @JsonIgnore // JSON 직렬화 시 순환 참조 방지
    private Category category;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryType_id", nullable = false)
    @JsonIgnore
    private CategoryType categoryType;
    
    //상품은 여러 옵션과 이미지를 가짐
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductVariant> productVariants;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProductResources> resources;
    
    /* =========================
     * 변경 메서드
     * ========================= */
    public void updateBasicInfo(
            String name,
            String description,
            BigDecimal price,
            String brand,
            String slug,
            Float rating,
            Boolean isNewArrival
    ) {
        if (name != null) this.name = name;
        if (description != null) this.description = description;
        if (price != null) this.price = price;
        if (brand != null) this.brand = brand;
        if (slug != null) this.slug = slug;
        if (rating != null) this.rating = rating;
        if (isNewArrival != null) this.isNewArrival = isNewArrival;
    }
    
    public void changeCategory(Category category, CategoryType categoryType) {
        this.category = category;
        this.categoryType = categoryType;
    }
    
    public void updateVariants(List<ProductVariant> newVariants) {
        this.productVariants.clear();
        newVariants.forEach(v -> v.changeProduct(this));
        this.productVariants.addAll(newVariants);
    }

    public void updateResources(List<ProductResources> newResources) {
        this.resources.clear();
        newResources.forEach(r -> r.changeProduct(this));
        this.resources.addAll(newResources);
    }
    
    /* =========================
     * Lifecycle
     * ========================= */
    // 엔티티 수정 직전 자동 실행
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }

    // 엔티티 수정 직전 자동 실행
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
    
    

}


