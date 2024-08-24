package com.sparta.delivery.user.entity;

import com.sparta.delivery.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "p_user")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;

    private String nickName;

    private String email;

    private String password;

    private String phone;

    private String address;

    private String zipcode;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    private boolean isPublic = true;





}
