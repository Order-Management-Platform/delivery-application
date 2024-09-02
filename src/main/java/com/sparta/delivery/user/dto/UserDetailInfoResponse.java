package com.sparta.delivery.user.dto;

import com.sparta.delivery.user.entity.User;
import com.sparta.delivery.user.entity.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDetailInfoResponse {

    private UUID userId;
    private String email;
    private String username;
    private String nickName;
    private String password;
    private String phone;
    private UserRole role;
    private String address;
    private String zipcode;
    private LocalDateTime createdAt;


    public static UserDetailInfoResponse of(User user) {
        return UserDetailInfoResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .nickName(user.getNickName())
                .password("")
                .phone(user.getPhone())
                .role(user.getRole())
                .address(user.getAddress())
                .zipcode(user.getZipcode())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
