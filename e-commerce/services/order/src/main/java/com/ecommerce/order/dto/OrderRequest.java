package com.ecommerce.order.dto;

import java.math.BigDecimal;
import java.util.List;

import com.ecommerce.order.PaymentMethod;
import com.ecommerce.product.PurchaseRequest;
import com.fasterxml.jackson.annotation.JsonInclude;
// 수기
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

// 값이 없거나 빈 컬렉션이면 그 부분만 반환X
@JsonInclude(Include.NON_EMPTY)
public record OrderRequest(
    Integer id,
    String reference,
    @Positive(message = "양수여야한다.")
    BigDecimal amount,
    @NotNull(message = "Paymentmethod는 필수")
    PaymentMethod paymentMethod,
    @NotNull(message = "고객이 존재해야 한다.")
    @NotEmpty(message = "고객이 존재해야 한다.")
    @NotBlank(message = "고객이 존재해야 한다.")
    String customerId,
    @NotEmpty(message = "적어도 한개 이상이어야 한다.")
    List<PurchaseRequest> products
) {

}
