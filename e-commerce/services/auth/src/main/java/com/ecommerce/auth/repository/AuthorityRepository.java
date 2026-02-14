package com.ecommerce.auth.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.auth.entity.Authority;

public interface AuthorityRepository extends JpaRepository<Authority, UUID> {

    Optional <Authority> findByRoleCode(String roleCode);
    boolean existsByRoleCode(String roleCode);

}
