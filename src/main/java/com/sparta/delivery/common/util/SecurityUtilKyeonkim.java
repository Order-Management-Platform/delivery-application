package com.sparta.delivery.common.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtilKyeonkim {

    public boolean checkOrderPermission(String orderType, Authentication authentication) {
        String userRole = authentication.getAuthorities().stream()
                .findFirst()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .orElse("NO_ROLE_FOUND");

        System.out.println("orderType = " + orderType);
        System.out.println("userRole = " + userRole);

        if ("delivery".equals(orderType) || "packaging".equals(orderType)) {
            return "ROLE_CUSTOMER".equals(userRole);
        } else if ("internal".equals(orderType)) {
            return "ROLE_OWNER".equals(userRole);
        }

        return false;
    }
}
