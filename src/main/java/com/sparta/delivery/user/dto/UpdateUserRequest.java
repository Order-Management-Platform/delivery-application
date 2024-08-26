package com.sparta.delivery.user.dto;

import com.sparta.delivery.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    private String username;
    private String nickName;
    private String password;
    private String tel;
    private String address;
    private String zipcode;
    private UserRole userRole;

    public void encodingPassword(String encodedPassword) {
        password = encodedPassword;
    }
}
