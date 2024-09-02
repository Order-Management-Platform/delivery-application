package com.sparta.delivery.user.service;

import com.sparta.delivery.user.entity.UserRole;

public interface AuthService {

    UserRole getRole(String email);
}
