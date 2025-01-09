package com.example.springboot.redis.example.string.bit;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/26 21:12
 */

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

/**
 * 签到功能测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class SignInTest {


    @Autowired
    private SignInService signInService;

    /**
     * 测试用户签到
     */
    @Test
    public void t1() {
        boolean b1 = signInService.signIn("U0003", "2023-11-01");
        boolean b2 = signInService.signIn("U0001", "2023-11-03");
        boolean b3 = signInService.signIn("U0001", "2023-11-05");
        boolean b4 = signInService.signIn("U0001", "2023-11-01");
        boolean b5 = signInService.signIn("U0002", "2023-11-01");
        System.out.println("b1=" + b1 + " b2=" + b2 + " b3=" + b3 + " b4=" + b4 +"b5=" + b5);
    }

    /**
     * 测试查看用户指定日期是否签到（查看当天是否有签到同理）
     */
    @Test
    public void t2() {
        boolean b1 = signInService.isSignIn("U0001", "2023-11-01");
        System.out.println(b1 ? "b1已签到" : "b1未签到");
        boolean b2 = signInService.isSignIn("U0001", "2023-11-06");
        System.out.println(b2 ? "b2已签到" : "b2未签到");
    }

    /**
     * 测试统计用户指定年月签到次数
     */
    @Test
    public void t3() {
        Long count = signInService.getSignInCount("U0002", "2023-11");
        System.out.println("签到次数count=" + count);
    }

    /**
     * 测试获取用户指定年月签到列表，也可以通过这种方式获取用户月签到次数
     */
    @Test
    public void t4() {
        List<Map> list = signInService.getSignInList("U0001", "2023-11");
        if (list != null && !list.isEmpty()) {
            list.forEach(item -> System.out.println(item));
        }
    }
    @Test
    public   void t5 () {
        String dateStr = "2022-07-15";
        String substring = dateStr.substring(0, 7);
        System.out.println(substring);
    }

}
