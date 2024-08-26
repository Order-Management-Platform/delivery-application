package com.sparta.delivery.user.dto;

import com.sparta.delivery.user.entity.UserRole;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    private String username;
    private String nickname;
    private String password;
    private UserRole role;
    private String email;
    private String tel;
    private String address;
    private String zipcode;


    public void encodingPassword(String password) {
        this.password = password;
    }
}
