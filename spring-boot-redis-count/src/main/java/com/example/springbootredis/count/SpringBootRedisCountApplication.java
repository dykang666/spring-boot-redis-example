package com.example.springbootredis.count;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


@SpringBootApplication
public class SpringBootRedisCountApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRedisCountApplication.class, args);
    }

}
