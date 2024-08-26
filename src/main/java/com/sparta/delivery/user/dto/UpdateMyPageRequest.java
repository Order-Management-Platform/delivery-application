package com.sparta.delivery.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateMyPageRequest {

    private String nickName;
    private String password;
    private String tel;
    private String address;
    private String zipcode;

    public void encodingPassword(String encodedPassword) {

    }
}
