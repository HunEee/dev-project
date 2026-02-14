package com.ecommerce.auth.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.auth.entity.Authority;
import com.ecommerce.auth.repository.AuthorityRepository;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AuthorityService {

    private final AuthorityRepository authorityRepository;

    /**
     * 기본 USER 권한 반환
     */
    public List<Authority> getUserAuthority() {

        Authority authority = authorityRepository
                .findByRoleCode("ROLE_USER")
                .orElseThrow(() -> new IllegalArgumentException("기본 USER 권한이 존재하지 않습니다."));

        return List.of(authority);
    }

    /**
     * 권한 생성
     */
    @Transactional
    public Authority createAuthority(String roleCode, String description) {

        if (authorityRepository.existsByRoleCode(roleCode)) {
            throw new IllegalArgumentException("이미 존재하는 권한입니다.");
        }

        Authority authority = Authority.builder()
                .roleCode(roleCode)
                .roleDescription(description)
                .build();

        return authorityRepository.save(authority);
    }
    
    
}
