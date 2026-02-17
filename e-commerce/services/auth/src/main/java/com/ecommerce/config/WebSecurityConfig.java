package com.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.ecommerce.exception.RESTAuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

	private final UserDetailsService userDetailsService; // 사용자 정보를 DB에서 조회하는 서비스
	private final JWTTokenHelper jwtTokenHelper; // JWT 생성/검증 도우미 클래스
	private final RESTAuthenticationEntryPoint authenticationEntryPoint; // 인증 실패 시 실행될 EntryPoint (401 반환용)

	public WebSecurityConfig(UserDetailsService userDetailsService, JWTTokenHelper jwtTokenHelper,
			RESTAuthenticationEntryPoint authenticationEntryPoint) {
		this.userDetailsService = userDetailsService;
		this.jwtTokenHelper = jwtTokenHelper;
		this.authenticationEntryPoint = authenticationEntryPoint;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	// 인증 없이 접근 가능한 API 목록
	private static final String[] PUBLIC_APIS = { "/api/v1/auth/**", // 로그인/회원가입
			"/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/oauth2/success" // OAuth2 로그인 성공 후 리다이렉트 URL
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable) // CSRF 보호 비활성화 -> JWT 기반이므로 세션 사용 안 함
				.sessionManagement(session -> // 세션 사용하지 않도록 설정
					session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.exceptionHandling(exception -> // 인증 실패시 실행할 핸들러 지정
					exception.authenticationEntryPoint(authenticationEntryPoint))
				.authorizeHttpRequests(auth -> // 인증 사용 여부
					auth.requestMatchers(PUBLIC_APIS).permitAll()
						.requestMatchers(HttpMethod.GET, "/api/v1/products", "/api/v1/category").permitAll()
						.anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
				)
				.oauth2Login(oauth -> // OAuth2 로그인 설정
				 	oauth.defaultSuccessUrl("/oauth2/success")
				 		 .loginPage("/oauth2/authorization/google") 
				)
				.addFilterBefore( // JWT 필터를 기본 로그인 필터 앞에 추가
						new JWTAuthenticationFilter(jwtTokenHelper, userDetailsService),
						UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	// DB 기반 로그인 처리 Provider 생성 -> 데이터 접근은 UserDetailsService가 담당
	@Bean
	public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService,
			PasswordEncoder passwordEncoder) {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder);
		return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}

}
