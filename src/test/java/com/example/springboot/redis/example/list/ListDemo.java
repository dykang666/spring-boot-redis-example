package com.example.springboot.redis.example.list;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/7/30 14:39
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ListDemo {
    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void  setList(){
        //在变量左边添加元素值。
        redisTemplate.opsForList().leftPush("list", "2");
        redisTemplate.opsForList().leftPush("list", "3");
        redisTemplate.opsForList().rightPush("list", "4");
        redisTemplate.opsForList().leftPush("list", "5");
        redisTemplate.opsForList().rightPush("list", "6");
    }
    @Test
    public void  setList2(){
        List list =new ArrayList<>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.add("d");
        redisTemplate.opsForList().rightPushAll("list2", list);
    }
    @Test
    public void t3() {
        ListOperations listOperations = redisTemplate.opsForList();
        List list2 = listOperations.range("list", 0, -1);//  // 从索引0开始到列表末尾
        System.out.println(list2.toString());
    }
    @Test
    public void t4() {
        ListOperations listOperations = redisTemplate.opsForList();
        List list2 = listOperations.range("list2", 0, -1);//  // 从索引0开始到列表末尾
        System.out.println(list2.toString());
    }

}
