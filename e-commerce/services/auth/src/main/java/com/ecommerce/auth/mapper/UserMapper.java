package com.ecommerce.auth.mapper;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.ecommerce.auth.dto.RegistrationRequest;
import com.ecommerce.auth.entity.User;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;

    public User toEntity(RegistrationRequest request, String verificationCode) {
        return User.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .password(passwordEncoder.encode(request.password()))
                .provider("manual")
                .enabled(false)
                .verificationCode(verificationCode)
                .build();
    }
}
