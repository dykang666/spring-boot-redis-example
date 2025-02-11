package com.example.springboot.redis.cache.service;

import com.example.springboot.redis.cache.SpringBootRedisCacheApplicationTests;
import com.example.springboot.redis.cache.entity.User;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

/**
 * <p>
 * Redis - 缓存测试
 * </p>
 *
 */
@Slf4j
public class UserServiceTest extends SpringBootRedisCacheApplicationTests {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取两次，查看日志验证缓存
     */
    @Test
    public void getTwice() {
        // 模拟查询id为1的用户
        User user1 = userService.get(1L);
        log.debug("【user1】= {}", user1);
        // 再次查询
        User user2 = userService.get(1L);
        log.info("【user2】= {}", user2);
        // 查看日志，只打印一次日志，证明缓存生效
    }

    /**
     * 获取不到数据  暂未找到原因
     */
    @Test
    public  void TestRedis(){
        String ping = redisTemplate.getConnectionFactory().getConnection().ping();
        System.out.println(ping);
        Set<String> keys = redisTemplate.keys("*");
        log.info("打印redis value", redisTemplate.opsForValue().get("ss"));
    }

    /**
     * 先存，再查询，查看日志验证缓存
     */
    @Test
    public void getAfterSave() {
        userService.saveOrUpdate(new User(4L, "user4"));

        User user = userService.get(4L);
        log.debug("【user】= {}", user);
        // 查看日志，只打印保存用户的日志，查询是未触发查询日志，因此缓存生效
    }

    /**
     * 测试删除，查看redis是否存在缓存数据
     */
    @Test
    public void deleteUser() {
        // 查询一次，使redis中存在缓存数据
        userService.get(1L);
        // 删除，查看redis是否存在缓存数据
        userService.delete(1L);
    }

}