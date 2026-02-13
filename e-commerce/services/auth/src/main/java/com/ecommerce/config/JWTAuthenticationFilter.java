package com.ecommerce.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

// 요청당 딱 한 번만 실행되는 보안 필터
// 매 HTTP 요청마다 동작함
public class JWTAuthenticationFilter extends OncePerRequestFilter {

	private final UserDetailsService userDetailsService;
    private final JWTTokenHelper jwtTokenHelper;

    public JWTAuthenticationFilter(JWTTokenHelper jwtTokenHelper, UserDetailsService userDetailsService) {
        this.jwtTokenHelper = jwtTokenHelper;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        // Authorization 헤더가 없거나 Bearer 형식이 아니면 그냥 통과
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = authHeader.substring(7); // "Bearer " 제거
        String username = null;

        try {
            username = jwtTokenHelper.getUserNameFromToken(token);
        } catch (Exception e) {
            // 토큰이 유효하지 않으면 그냥 인증 없이 진행
            filterChain.doFilter(request, response);
            return;
        }

        // username이 있고 아직 인증이 안 되어 있는 경우만 처리
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (jwtTokenHelper.validateToken(token, userDetails)) {

                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증 정보 저장
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }

        filterChain.doFilter(request, response);
    }
}
