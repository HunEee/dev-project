package com.ecommerce.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;

//JWT 생성, 파싱, 검증, 만료 체크
@Component
public class JWTTokenHelper {

	@Value("${jwt.auth.app}")
    private String appName;

    @Value("${jwt.auth.secret_key}")
    private String secretKey;

    @Value("${jwt.auth.expires_in}")
    private int expiresIn;

    //JWT 생성
    public String generateToken(String userName){
        return Jwts.builder()
                .issuer(appName)
                .subject(userName)
                .issuedAt(new Date())
                .expiration(generateExpirationDate())	// 만료시간 설정
                .signWith(getSigningKey())				// secretKey로 서명
                .compact();
    }

    // HMAC SHA 키
    private javax.crypto.SecretKey getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // 현재시간 + 설정한 초
    private Date generateExpirationDate() {
        return new Date(new Date().getTime() + expiresIn * 1000L);
    }
    
    public String getToken( HttpServletRequest request ) {

        String authHeader = getAuthHeaderFromHeader( request );
        if ( authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }

        return authHeader;
    }

    //토큰 username과 DB username 비교 -> 만료 여부 확인
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUserNameFromToken(token);
        return (
                username != null && username.equals(userDetails.getUsername()) && !isTokenExpired(token)
        );
    }

    private boolean isTokenExpired(String token) {
        Date expireDate=getExpirationDate(token);
        return expireDate.before(new Date());
    }

    private Date getExpirationDate(String token) {
        Date expireDate;
        try {
            final Claims claims = this.getAllClaimsFromToken(token);
            expireDate = claims.getExpiration();
        } catch (Exception e) {
            expireDate = null;
        }
        return expireDate;
    }


    private String getAuthHeaderFromHeader(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    public String getUserNameFromToken(String authToken) {
        String username;
        try {
            final Claims claims = this.getAllClaimsFromToken(authToken);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    private Claims getAllClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                    .verifyWith((javax.crypto.SecretKey) getSigningKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            return null;
        }
    }
	
}
