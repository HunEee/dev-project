package com.ecommerce.auth.controller;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.dto.LoginRequest;
import com.ecommerce.auth.dto.RegistrationRequest;
import com.ecommerce.auth.dto.RegistrationResponse;
import com.ecommerce.auth.dto.UserToken;
import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.service.RegistrationService;
import com.ecommerce.config.JWTTokenHelper;

import lombok.RequiredArgsConstructor;

@RestController
@CrossOrigin
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthenticationManager authenticationManager;
	private final RegistrationService registrationService;
	private final JWTTokenHelper jwtTokenHelper;
	
	 /**
     * 로그인 API 
     */
    @PostMapping("/login")
    public ResponseEntity <UserToken> login(@RequestBody LoginRequest loginRequest) {

        try {
            Authentication authentication = 
            		new UsernamePasswordAuthenticationToken(loginRequest.userName(), loginRequest.password());

            Authentication authenticationResponse = authenticationManager.authenticate(authentication);

            UserDetails userDetails = (UserDetails) authenticationResponse.getPrincipal();

            // enabled 체크 (이메일 인증 여부)
            if (userDetails instanceof User user && !user.isEnabled()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = jwtTokenHelper.generateToken(userDetails.getUsername());

            return ResponseEntity.ok(new UserToken(token));

        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    /**
     * 회원가입 API
     */
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@RequestBody RegistrationRequest request) {

        RegistrationResponse response = registrationService.createUser(request);

        return ResponseEntity
                .status(response.code() == 200 ? HttpStatus.OK : HttpStatus.BAD_REQUEST)
                .body(response);
    }

    /**
     * 이메일 인증 코드 검증
     */
    @PostMapping("/verify")
    public ResponseEntity<Void> verifyCode(@RequestBody Map<String, String> map) {

        String userName = map.get("userName");
        String code = map.get("code");

        User user = registrationService.findByEmail(userName);

        if (user != null && user.getVerificationCode() != null && user.getVerificationCode().equals(code)) {
            registrationService.verifyUser(userName);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.badRequest().build();
    }
	

	
	

	
	
}
