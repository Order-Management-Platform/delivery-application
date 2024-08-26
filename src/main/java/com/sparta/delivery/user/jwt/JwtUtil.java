package com.sparta.delivery.user.jwt;

import com.sparta.delivery.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
public class JwtUtil {

    public static final String AUTHORIZATION_HEADER = "Authorization";


    public static final String BEARER_PREFIX = "Bearer ";

    @Value("${spring.application.name}")
    private String issuer;

    private final SecretKey secretKey;
    @Value("${jwt.secret.access-expiration}")
    private long accessExpiration; // 60분

    public JwtUtil(@Value("${jwt.secret.key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Base64.getDecoder().decode(secretKey));
    }

    public String createToken(String email, UserRole role) {
        return BEARER_PREFIX + Jwts.builder()
                .claim("email", email)
                .claim("role", role.name())
                .issuer(issuer)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + accessExpiration))
                .signWith(secretKey)
                .compact();
    }

    public String getJwtFromHeader(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            getUserInfoFromToken(token);
            return true;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
//            log.error("Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.");
        } catch (ExpiredJwtException e) {
//            log.error("Expired JWT token, 만료된 JWT token 입니다.");
        } catch (UnsupportedJwtException e) {
//            log.error("Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.");
        } catch (IllegalArgumentException e) {
//            log.error("JWT claims is empty, 잘못된 JWT 토큰 입니다.");
        }
        return false;
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }
}
