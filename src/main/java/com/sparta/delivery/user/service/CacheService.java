package com.sparta.delivery.user.service;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CacheService {


    @CacheEvict(cacheNames = "getRole", key = "#email")
    public void evictRole(String email) {

    }

}
