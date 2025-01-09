package com.example.springboot.redis.example.zset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kangdongyang
 * @version 1.0
 * @description: 有序集合 (Sorted Set，有序集合)
 * @date 2024/7/30 16:38
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ZsetDemo {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Test
    public   void  zset(){
    ZSetOperations<String, String> zsetOps = redisTemplate.opsForZSet();
        //zset内部是按分数来排序的，这里用当前时间做分数
        /**
         * 添加一个元素, zset与set最大的区别就是每个元素都有一个score，因此有个排序的辅助功能;  zadd
         *
         * @param key
         * @param value
         * @param score
         */
        zsetOps.add("zset", "1",1);
        zsetOps.add("zset", "3",2);
        zsetOps.add("zset", "2",3);

}

    @Test
    public  void  getSet(){
      System.out.println(redisTemplate.opsForZSet().range("zset",0,-1));
  }




}
