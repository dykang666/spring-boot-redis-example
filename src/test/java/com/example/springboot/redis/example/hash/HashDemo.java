package com.example.springboot.redis.example.hash;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/7/30 15:58
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class HashDemo {
    @Autowired
    private RedisTemplate redisTemplate;
    @Test
    public void setHash() {
        Map<String, String> user = new HashMap<>();
        user.put("name", "John Doe");
        user.put("age", "30");
        redisTemplate.opsForHash().putAll("user", user);
    }
}
