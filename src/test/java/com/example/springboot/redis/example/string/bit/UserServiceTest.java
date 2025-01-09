package com.example.springboot.redis.example.string.bit;


import com.example.springboot.redis.example.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/27 11:17
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserServiceTest {
    @Autowired
    private UserServiceImpl userService;

    @Test
    public void T1() {
        userService.sign("kkk", "2023-11-01");
        userService.sign("kkk", "2023-11-03");
        userService.sign("kkk", "2023-11-05");
        userService.sign("kkk1", "2023-11-01");
    }
    @Test
     public   void  t2(){
         long u0001 = userService.signCount("kkk","2023-11-01");
         System.out.println( u0001);
//        long u0001 = userService.signCount("kkk","2023-11-01");
     }
     @Test
     public  void T3(){



     }
}
