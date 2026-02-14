package com.ecommerce.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.auth.dto.RegistrationRequest;
import com.ecommerce.auth.dto.RegistrationResponse;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.mapper.UserMapper;
import com.ecommerce.auth.repository.UserDetailRepository;
import com.ecommerce.helper.VerificationCodeGenerator;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class RegistrationService {
	
	private final UserDetailRepository userRepository;
    private final AuthorityService authorityService;
    private final EmailService emailService;
    private final UserMapper userMapper;
    
    public RegistrationResponse createUser(RegistrationRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            return new RegistrationResponse(400, "이미 존재하는 이메일입니다.");
        }

        String verificationCode = VerificationCodeGenerator.generateCode();
        User user = userMapper.toEntity(request,verificationCode);
        user.setAuthorities(authorityService.getUserAuthority());

        userRepository.save(user);

        emailService.sendMail(user);

        return new RegistrationResponse(200, "회원가입이 완료되었습니다.");
    }

    public void verifyUser(String email) {

        User user = userRepository.findByEmail(email)
        		.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        user.setEnabled(true);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
        		.orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    }    
	
	
	
	
}

