package com.ecommerce.product.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record ProductPurchaseRequest(
        @NotNull(message = "상품은 필수입니다.")
        Integer productId,
        @Positive(message = "수량은 양수입니다.")
        double quantity
) {
}
