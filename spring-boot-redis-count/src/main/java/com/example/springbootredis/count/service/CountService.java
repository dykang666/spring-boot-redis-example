package com.example.springbootredis.count.service;

public interface CountService {
    long addOne(String key);
    String getCount(String key);

    String sendVerifyCode(String mobile);
}
