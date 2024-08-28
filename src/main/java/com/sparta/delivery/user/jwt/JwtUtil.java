package com.sparta.delivery.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    public String validateToken(String token) {
        try {
            getUserInfoFromToken(token);
            return null;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            return "Invalid JWT signature, 유효하지 않는 JWT 서명 입니다.";
        } catch (ExpiredJwtException e) {
            return "Expired JWT token, 만료된 JWT token 입니다.";
        } catch (UnsupportedJwtException e) {
            return "Unsupported JWT token, 지원되지 않는 JWT 토큰 입니다.";
        } catch (IllegalArgumentException e) {
            return "JWT claims is empty, 잘못된 JWT 토큰 입니다.";
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }


    public void errorMessageResponse(HttpServletResponse res, String errorMessage) throws IOException {
        res.setStatus(HttpStatus.UNAUTHORIZED.value());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ResponseDto responseDto = ResponseDto.of(HttpStatus.UNAUTHORIZED.value(), errorMessage);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseDto);
        res.getWriter().write(jsonResponse);
    }
}
