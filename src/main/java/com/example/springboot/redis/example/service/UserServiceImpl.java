package com.example.springboot.redis.example.service;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/26 18:11
 */
@Service
@Slf4j
public class UserServiceImpl {
    // 用户登录记录的键前缀
    private static final String LOGIN_KEY_PREFIX = "login:";
    @Autowired
    private RedisTemplate redisTemplate;

    private static String yyyy_MM_dd = "yyyy-MM-dd";
    private static String yyyy_MM = "yyyy-MM";
    public  String  sign(String userId,String dateStr){
//        LocalDateTime now = LocalDateTime.now();
//        //3. 拼接key
//        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String keySuffix = dateStr.substring(0, 7);
        String key = LOGIN_KEY_PREFIX + userId + keySuffix;
        //int offset = LocalDate.now().getDayOfYear();
        DateTime dateTime = DateUtil.parse(dateStr, yyyy_MM_dd);
        int offset = dateTime.dayOfMonth();
        log.info("sign,key:{}",key);
        //5. 写入redis setbit key offset 1
        // 设置给BitMap对应位标记 其中offset为0表示第一天所以要day-1
        redisTemplate.opsForValue().setBit(key,  offset-1, true);
        return "ok";
    }
    public   long  signCount(String userId, String dateStr){
        //2. 获取日期
//        LocalDateTime now = LocalDateTime.now();
//        //3. 拼接key
//        String keySuffix = now.format(DateTimeFormatter.ofPattern(":yyyyMM"));
        String keySuffix = dateStr.substring(0, 7);
        String key = LOGIN_KEY_PREFIX + userId + keySuffix;
        log.info("signCount,key:{}",key);
        Long count = (Long)redisTemplate.execute(connection -> connection.bitCount(key.getBytes()), true);
        return  count;

    }



}
