package com.example.springboot.redis.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/26 16:37
 */
@RestController
public class RedisController {
    @Autowired
    private RedisTemplate redisTemplate;
    @GetMapping("/setSessionValue")
    public String setredisResult(HttpServletRequest request){
        request.getSession().setAttribute(request.getSession().getId(), "---测试数据---"+request.getRequestURL());
        System.out.println(request.getSession().getId());
        return "set成功，已经存入session域且redis里面也会有值";
    }
    @GetMapping("/getSessionValue")
    public String redisResult(HttpServletRequest request) {
        System.out.println(request.getSession().getId());
        String value = String.valueOf(request.getSession().getAttribute(request.getSession().getId()));
        return "取值成功         :"+value;
    }

    @GetMapping("/setRedisValue")
    public void setRedisValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @GetMapping("/getRedisValue")
    public String getRedisValue(String key) {
        return (String) redisTemplate.opsForValue().get(key);
    }
    @GetMapping("/deleteRedisValue")
    public void deleteRedisValue(String key) {
        redisTemplate.delete(key);
    }
    @GetMapping("/updateRedisValue")
    public void updateRedisValue(String key, String value) {
        redisTemplate.opsForValue().set(key, value);
    }

    //-------------------------------------------------------------高阶部分
    //---setbit  getbit  bitcount  实现签到接口，将当前用户当天签到信息保存到Redis中

    //测试超卖问题




}
