package com.ecommerce.customer.dto;

import com.ecommerce.customer.domain.vo.Address;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CustomerRequest(
    String id,
    @NotNull(message = "고객 성은 필수 입니다.")
    String firstname,
    @NotNull(message = "고객 이름은 필수 입니다.")
    String lastname,
    @NotNull(message = "이메일은 필수입니다.")
    @Email(message = "이메일이 유효하지 않습니다.")
    String email,
    Address address
){

}
