package com.ecommerce.auth.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.dto.ProfileRequest;
import com.ecommerce.auth.entity.User;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserDetailController {

    private final UserDetailsService userDetailsService;

    @GetMapping("/profile")
    public ResponseEntity<ProfileRequest> getUserProfile(Principal principal) {

        if (principal == null) {
            return ResponseEntity.status(401).build();
        }

        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        List<String> authorities = user.getAuthorities()
                .stream()
                .map(auth -> auth.getAuthority())
                .toList();
        
        ProfileRequest response = new ProfileRequest(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.getEmail(),
                authorities
        );

        return ResponseEntity.ok(response);
    }
}
