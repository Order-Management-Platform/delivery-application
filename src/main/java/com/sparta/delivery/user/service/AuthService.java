package com.sparta.delivery.user.service;

public interface AuthService {

    boolean isRoleMatch(String email, String role);
}
