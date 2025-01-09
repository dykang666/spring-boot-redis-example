package com.example.springboot.redis.example.zset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/27 16:59
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ZsetTest {

    @Autowired
    private ZsetService zsetService;

    /**
     * @description: 测试有序集合
     **/
    @Test
    public void testZsetRank()
    {
        String rankName = "rank";
        zsetService.initRank(rankName);
        zsetService.getRankNameList(rankName);
        //zsetService.getRankNameAndScoreList(rankName);
    }

}
