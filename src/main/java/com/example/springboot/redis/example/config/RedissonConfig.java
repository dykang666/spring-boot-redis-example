package com.example.springboot.redis.example.config;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * Value 注解不生效  暂未查到原因
 * @date 2024/6/27 12:06
 */
@Configuration
public class RedissonConfig {
//   @Value("${spring.data.redis.host}")
//   private String host;
    private String host="81.70.101.216";
    // @Value("${spring.redis.port}")
    private String port="6379";
    // @Value("${spring.redis.password}")
    private String  password="123456";
    //@Value("${spring.redis.database}")
    private Integer database=0;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        config.useSingleServer()
                .setAddress("redis://" + host + ":" + port)
                .setPassword(password)
                .setDatabase(database);
        return Redisson.create(config);
    }


}
