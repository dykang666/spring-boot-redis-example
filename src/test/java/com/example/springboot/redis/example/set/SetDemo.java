package com.example.springboot.redis.example.set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Set;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:   集合
 * @date 2024/7/30 16:20
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SetDemo {
    @Autowired
    private RedisTemplate redisTemplate;


    @Test
    public void set() {
        redisTemplate.opsForSet().add("set", "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z");
    }

    @Test
    public void getSet() {
        System.out.println(redisTemplate.opsForSet().members("set"));
    }

    @Test
    public void T2() {
        redisTemplate.opsForSet().add("set1", "a","b","c","c");
    }

    @Test
    public void T3() {
        Set members = redisTemplate.opsForSet().members("set1");
        System.out.println(members);
    }
}