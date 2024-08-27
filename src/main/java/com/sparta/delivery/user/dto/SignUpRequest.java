package com.sparta.delivery.user.dto;

import com.sparta.delivery.user.entity.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String nickname;

    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d!@#$%&*()]{8,15}&",
            message = "알파벳 대소문자, 숫자, 특수문자를 사용 가능하며, 최소 8자이상 15자 이하 여야합니다.")
    private String password;
    private UserRole role;

    @NotBlank
    @Email(message = "잘못된 이메일 주소 입니다.")
    private String email;
    private String tel;
    private String address;
    private String zipcode;


    public void encodingPassword(String password) {
        this.password = password;
    }
}
