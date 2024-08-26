package com.sparta.delivery.user.entity;

import lombok.Getter;

@Getter
public enum UserRole {

    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER),
    MANAGER(Authority.MANAGER),
    MASTER(Authority.MASTER);


    private final String authority;

    UserRole(String authority) {
        this.authority = authority;
    }

    public static class Authority{
        public static final String CUSTOMER = "CUSTOMER";
        public static final String OWNER = "OWNER";
        public static final String MANAGER = "MANAGER";
        public static final String MASTER = "MASTER";

    }

}
