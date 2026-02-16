package com.ecommerce.auth.dto;

import java.util.List;
import java.util.UUID;

public record ProfileRequest(
        UUID id,
        String firstName,
        String lastName,
        String phoneNumber,
        String email,
        List<String> authorityList
) {}
