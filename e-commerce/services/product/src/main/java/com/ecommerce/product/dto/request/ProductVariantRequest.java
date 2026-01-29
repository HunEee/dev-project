package com.ecommerce.product.dto.request;

import java.util.UUID;

public record ProductVariantRequest(
	    UUID id,
	    String color,
	    String size,
	    Integer stockQuantity
) {}
