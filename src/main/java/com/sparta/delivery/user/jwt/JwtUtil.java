package com.sparta.delivery.user.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery.common.ResponseCode;
import com.sparta.delivery.common.dto.ErrorResponseDto;
import com.sparta.delivery.user.entity.UserRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
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

    public ResponseCode validateToken(String token) {
        try {
            getUserInfoFromToken(token);
            return null;
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            return ResponseCode.INVALID_JWT_SIGNATURE;
        } catch (ExpiredJwtException e) {
            return ResponseCode.EXPIRED_JWT;
        } catch (UnsupportedJwtException e) {
            return ResponseCode.UNSUPPORTED_JWT;
        } catch (IllegalArgumentException e) {
            return ResponseCode.EMPTY_JWT_CLAIMS;
        }
    }

    // 토큰에서 사용자 정보 가져오기
    public Claims getUserInfoFromToken(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload();
    }


    public void errorMessageResponse(HttpServletResponse res, ResponseCode responseCode) throws IOException {
        res.setStatus(responseCode.getStatus());
        res.setContentType(MediaType.APPLICATION_JSON_VALUE);
        res.setCharacterEncoding(StandardCharsets.UTF_8.name());

        ErrorResponseDto responseDto = ErrorResponseDto.of(responseCode);
        ObjectMapper mapper = new ObjectMapper();
        String jsonResponse = mapper.writeValueAsString(responseDto);
        res.getWriter().write(jsonResponse);
    }
}
