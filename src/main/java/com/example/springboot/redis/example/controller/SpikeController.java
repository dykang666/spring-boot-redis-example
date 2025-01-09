package com.example.springboot.redis.example.controller;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/27 11:47
 */

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用于测试redis秒杀
 */
@RestController
@RequestMapping("/api/spike")
@Slf4j
public class SpikeController {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private StringRedisTemplate redisTemplate;
    //记录实际卖出的商品数量
    private AtomicInteger successNum = new AtomicInteger(0);

    @RequestMapping(value = "/initSku", method = RequestMethod.GET)
    public String initSku() {
        //初始化库存数量
        redisTemplate.opsForValue().set("product_sku", "5");
        //初始化实际卖出的商品数量0
        successNum.set(0);
        return "初始化库存成功";
    }

    /**
     * 会出现超卖情况的减少库存方式
     * @return
     */
    @RequestMapping(value = "/reduceSku", method = RequestMethod.GET)
    public String reduceSku() {
        RLock rLock = redissonClient.getLock("product");
        try {
            rLock.lock();
            Integer sku = Integer.parseInt(redisTemplate.opsForValue().get("product_sku"));
            sku = sku - 1;
            if (sku < 0) {
                return "库存不足";
            }
            redisTemplate.opsForValue().set("product_sku", sku.toString());
            //记录实际卖出的商品数量
            return "减少库存成功,共减少" + successNum.incrementAndGet();
        } catch (NumberFormatException e) {
            throw new RuntimeException(e);
        } finally {
            if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
                rLock.unlock();
            }
        }


    }

    /**
     * 会出现超卖情况的减少库存方式
     * @return
     */
    @RequestMapping(value = "/reduceSkuNew", method = RequestMethod.GET)
    public String reduceSkureduceSkuNew() {
        RLock rLock = redissonClient.getLock("product");
        try {
            boolean b = rLock.tryLock(5000, TimeUnit.MILLISECONDS);
            Integer sku = Integer.parseInt(redisTemplate.opsForValue().get("product_sku"));
            sku = sku - 1;
            if (sku < 0) {
                return "库存不足";
            }
            redisTemplate.opsForValue().set("product_sku", sku.toString());
            //记录实际卖出的商品数量
            return "减少库存成功,共减少" + successNum.incrementAndGet();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            rLock.unlock();
        }
    }

    @RequestMapping(value = "/successNum", method = RequestMethod.GET)
    public String successNum() {
        log.info( "顾客成功抢到的商品数量：" + successNum.get());
        return "顾客成功抢到的商品数量：" + successNum.get();
    }
}

















