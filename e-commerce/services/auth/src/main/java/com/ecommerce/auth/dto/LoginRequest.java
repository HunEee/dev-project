package com.ecommerce.auth.dto;

public record LoginRequest(
		String userName,
		String password	
) {}
