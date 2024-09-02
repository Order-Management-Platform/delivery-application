package com.sparta.delivery.user.filter;


import com.sparta.delivery.common.ResponseCode;
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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final AuthService authService;


    public JwtAuthorizationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain) throws ServletException, IOException {
        String requestURI = req.getRequestURI();
        String tokenValue = jwtUtil.getJwtFromHeader(req);
        if (requestURI.equals("/user/signUp") || requestURI.equals("/user/signIn")) {
            filterChain.doFilter(req, res);
            return;
        }


        if (StringUtils.hasText(tokenValue)) {

            ResponseCode responseCode = jwtUtil.validateToken(tokenValue);
            if (responseCode != null) {
                jwtUtil.errorMessageResponse(res, responseCode);
                return;
            }

            Claims info = jwtUtil.getUserInfoFromToken(tokenValue);
            String role = info.get("role", String.class);
            String email = info.get("email", String.class);


            if (!role.equals(UserRole.CUSTOMER.name()) && !isRoleMatch(email, role)) {
                jwtUtil.errorMessageResponse(res, ResponseCode.NOT_MATCH_ROLE);
                return;
            }

            try {
                setAuthentication(email);
            } catch (UsernameNotFoundException e) {
                jwtUtil.errorMessageResponse(res, ResponseCode.NOT_FOUND_USER);
                return;
            }
        }
        filterChain.doFilter(req, res);
    }

    private boolean isRoleMatch(String email, String role) {
        UserRole userRole = authService.getRole(email);
        return role.equals(userRole.name());
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