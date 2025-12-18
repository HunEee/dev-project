package com.ecommerce.product;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Validated
public record PurchaseRequest(
        @NotNull(message = "품목ID는 필수입니다.")
        Integer productId,
        @Positive(message = "수량은 필수입니다.")
        double quantity
) {
}
