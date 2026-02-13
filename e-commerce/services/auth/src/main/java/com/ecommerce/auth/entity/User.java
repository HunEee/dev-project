package com.ecommerce.auth.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "AUTH_USER_DETAILS")
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User implements UserDetails{

    @Id
    @GeneratedValue
    private UUID id;

    private String firstName;

    private String lastName;

    @JsonIgnore
    private String password;

    private LocalDateTime createdOn;

    private LocalDateTime updatedOn;

    @Column(nullable = false,unique = true)
    private String email;

    private String phoneNumber;

    private String provider;

    private String verificationCode;

    private boolean enabled=false;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name = "AUTH_USER_AUTHORITY",
    		   joinColumns = @JoinColumn(referencedColumnName = "id"),
    		   inverseJoinColumns = @JoinColumn(referencedColumnName = "id")
    )
    private List<Authority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

}
