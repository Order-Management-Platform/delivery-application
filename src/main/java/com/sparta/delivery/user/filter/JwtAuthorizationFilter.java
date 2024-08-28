package com.sparta.delivery.user.filter;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.delivery.common.dto.ResponseDto;
import com.sparta.delivery.user.entity.UserRole;
import com.sparta.delivery.user.jwt.JwtUtil;
import com.sparta.delivery.user.jwt.UserDetailsServiceImpl;
import com.sparta.delivery.user.service.AuthService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;
    private final AuthService authService;


    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {

        String tokenValue = jwtUtil.getJwtFromHeader(req);

        if (StringUtils.hasText(tokenValue)) {

            String errorMessage = jwtUtil.validateToken(tokenValue);
            if (StringUtils.hasText(errorMessage)) {
                jwtUtil.errorMessageResponse(res, errorMessage);

                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            String role = info.get("role", String.class);
            String email = info.get("email", String.class);


            if (!role.equals(UserRole.CUSTOMER.name()) && !authService.isRoleMatch(email, role)) {
                jwtUtil.errorMessageResponse(res, "토큰 권한과 실제 권한이 맞지 않습니다.");
                return;
            }

            try {
                setAuthentication(email);
            } catch (UsernameNotFoundException e) {
                jwtUtil.errorMessageResponse(res, e.getMessage());
                return;
            }
        }

        filterChain.doFilter(req, res);
    }


    // 인증 처리
    public void setAuthentication(String email) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        Authentication authentication = createAuthentication(email);
        context.setAuthentication(authentication);

        SecurityContextHolder.setContext(context);
    }

    // 인증 객체 생성
    private Authentication createAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}