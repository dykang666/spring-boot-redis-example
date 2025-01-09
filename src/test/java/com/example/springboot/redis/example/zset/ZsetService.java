package com.example.springboot.redis.example.zset;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/27 16:56
 */
@Service
@Slf4j
public class ZsetService {
    @Autowired
    private RedisTemplate redisTemplate;
    public void initRank(String name)
    {
        Set<ZSetOperations.TypedTuple<String>> tuples = new HashSet<>();
        for (int i = 0; i < 1000; i++) {
            DefaultTypedTuple<String> tuple = new DefaultTypedTuple<>("张三" + i, 1D + i);
            tuples.add(tuple);
        }
        // 批量存放
        Long add = redisTemplate.opsForZSet().add(name, tuples);
    }
    public void getRankNameList(String name)
    {
        Set<String> set = redisTemplate.opsForZSet().reverseRange(name, 0, 100);
        System.out.println(set.toString());
    }
    public void getRankNameAndScoreList(String name)
    {
        Set<ZSetOperations.TypedTuple<String>> set = redisTemplate.opsForZSet().reverseRangeWithScores(name, 0, 100);
        System.out.println(set.toString());
    }


}
