package com.sparta.delivery.user.dto;

import com.sparta.delivery.user.entity.UserRole;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {
    @NotBlank(message = "이름칸이 비어있습니다.")
    private String username;
    @NotBlank(message = "별명을 설정해 주세요")
    private String nickName;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z\\d!@#$%&*()]{8,15}$",
            message = "비밀번호는 알파벳 대소문자, 숫자, 특수문자를 사용 가능하며, 최소 8자이상 15자 이하 여야합니다.")
    private String password;
    private String phone;
    private String address;
    private String zipcode;
    private UserRole role;

    public void encodingPassword(String encodedPassword) {
        password = encodedPassword;
    }
}
