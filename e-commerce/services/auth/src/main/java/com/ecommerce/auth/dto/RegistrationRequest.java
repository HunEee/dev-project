package com.ecommerce.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record RegistrationRequest(

        @NotBlank(message = "이름은 필수 입력 항목입니다.")
        String firstName,

        @NotBlank(message = "성은 필수 입력 항목입니다.")
        String lastName,

        @Email(message = "올바른 이메일 형식이 아닙니다.")
        @NotBlank(message = "이메일은 필수 입력 항목입니다.")
        String email,

        @Size(min = 6, message = "비밀번호는 최소 6자 이상이어야 합니다.")
        @NotBlank(message = "비밀번호는 필수 입력 항목입니다.")
        String password,

        @NotBlank(message = "휴대폰 번호는 필수 입력 항목입니다.")
        String phoneNumber

) {}