package com.ecommerce.payment.dto;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

@Validated
public record Customer(
        String id,
        @NotNull(message = "이름은 필수입니다.")
        String firstname,
        @NotNull(message = "성은 필수입니다.")
        String lastname,
        @NotNull(message = "Email은 필수입니다.")
        @Email(message = "Email 포멧에 맞지 않습니다.")
        String email
) { 
	
}