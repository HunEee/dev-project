package com.ecommerce.auth.service;

import java.util.Optional;

import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.repository.UserDetailRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OAuth2Service {


    private final UserDetailRepository userDetailRepository;
    private final AuthorityService authorityService;
	
    public Optional <User> getUser(String userName) {
        return userDetailRepository.findByEmail(userName);
    }

    @Transactional
    public User createUser(OAuth2User oAuth2User, String provider) {
        
    	String email = oAuth2User.getAttribute("email");

    	// 기존 사용자면 그대로 반환 -> 없으면 생성
        return userDetailRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = User.builder()
                            .firstName(oAuth2User.getAttribute("given_name"))
                            .lastName(oAuth2User.getAttribute("family_name"))
                            .email(email)
                            .provider(provider)
                            .enabled(true)
                            .authorities(authorityService.getUserAuthority())
                            .build();

                    return userDetailRepository.save(newUser);
                });
    }
    
    
}
