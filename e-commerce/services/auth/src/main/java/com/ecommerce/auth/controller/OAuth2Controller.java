package com.ecommerce.auth.controller;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.auth.entity.User;
import com.ecommerce.auth.service.OAuth2Service;
import com.ecommerce.config.JWTTokenHelper;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/oauth2")
@RequiredArgsConstructor
public class OAuth2Controller {

	private final OAuth2Service oAuth2Service;
    private final JWTTokenHelper jwtTokenHelper;

    @GetMapping("/success")
    public void callbackOAuth2(@AuthenticationPrincipal OAuth2User oAuth2User, HttpServletResponse response) throws IOException {

        String email = oAuth2User.getAttribute("email");
        Optional <User> optionalUser = oAuth2Service.getUser(email);

        User user = optionalUser.orElseGet(() ->
                oAuth2Service.createUser(oAuth2User, "google")
        );

        String token = jwtTokenHelper.generateToken(user.getUsername());

        response.sendRedirect("http://localhost:5173/oauth2/callback?token="+token);

    }
	
}
