package com.example.springbootredis.session.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2025/1/9 21:23
 */
@EnableRedisHttpSession
@Configuration
public class RedisSessionConfig {
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        LettuceConnectionFactory factory = new LettuceConnectionFactory("81.70.101.216", 6379);
        factory.setTimeout(10000);  // 设置连接超时
        factory.setPassword("123456");
        factory.setDatabase(5);
        return factory;
    }
    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }
}
