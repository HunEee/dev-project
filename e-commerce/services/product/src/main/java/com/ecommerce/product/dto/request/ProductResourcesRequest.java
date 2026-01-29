package com.ecommerce.product.dto.request;

import java.util.UUID;

public record ProductResourcesRequest(
	    UUID id,
	    String name,
	    String url,
	    String type,
	    Boolean isPrimary
) {}