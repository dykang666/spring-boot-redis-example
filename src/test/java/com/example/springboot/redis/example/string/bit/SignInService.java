package com.example.springboot.redis.example.string.bit;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.util.*;

/**
 * @author kangdongyang
 * @version 1.0
 * @description:
 * @date 2024/6/26 21:13
 */
@Service
public class SignInService {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static String yyyy_MM_dd = "yyyy-MM-dd";
    private static String yyyy_MM = "yyyy-MM";

    /**
     * 用户签到
     * @param userNo 用户编号
     * @param date   日期 格式yyyy-MM-dd
     */
    public boolean signIn(String userNo, String date) {
        // 获取缓存key
        String cacheKey = getCacheKey(userNo, date);
        // 获取日期
        DateTime dateTime = DateUtil.parse(date, yyyy_MM_dd);
        int day = dateTime.dayOfMonth();
        // 设置给BitMap对应位标记 其中offset为0表示第一天所以要day-1
        Boolean result = redisTemplate.opsForValue().setBit(cacheKey, day - 1, true);
        // 如果响应true则代表之前已经签到，在Redis指令操作setbit 设置对应位为1的时候，如果之前是0或者不存在会响应0，如果为1则响应1
        if (result) {
            System.out.println("用户userNo=" + userNo + " date=" + date + "  已签到");
        }
        return result;
    }


    /**
     * 查看用户指定日期是否签到（查看当天是否有签到同理）
     * @param userNo
     * @param date   日期 格式yyyy-MM-dd
     */
    public boolean isSignIn(String userNo, String date) {
        // 获取缓存key
        String cacheKey = getCacheKey(userNo, date);
        // 获取日期
        DateTime dateTime = DateUtil.parse(date, yyyy_MM_dd);
        int day = dateTime.dayOfMonth();
        return redisTemplate.opsForValue().getBit(cacheKey, day - 1);
    }
    /**
     * 统计用户指定年月签到次数
     * @param userNo
     * @param date   格式yyyy-MM
     */
    public Long getSignInCount(String userNo, String date) {
        // 获取缓存key
        String cacheKey = getCacheKey(userNo, date);
        // 不知道是那个版本才有的下面这个方法，我的现在使用的spring-data-redis是2.3.9.RELEASE 是没有这个方法的，改用connection直接调用bitCount
        Long count = redisTemplate.execute(connection -> connection.bitCount(cacheKey.getBytes()), true);
        return count;
    }


    /**
     * 获取用户指定年月签到列表，也可以通过这种方式获取用户月签到次数
     *
     * @param userNo
     * @param date   格式yyyy-MM
     */
    public List<Map> getSignInList(String userNo, String date) {
        // 获取缓存key
        String cacheKey = getCacheKey(userNo, date);
        // 获取传入月份有多少天
        DateTime dateTime = DateUtil.parse(date, yyyy_MM);
        YearMonth yearMonth = YearMonth.of(dateTime.year(), dateTime.monthBaseOne());
        int days = yearMonth.lengthOfMonth();
        BitFieldSubCommands bitFieldSubCommands = BitFieldSubCommands.create()
                .get(BitFieldSubCommands.BitFieldType.unsigned(days)).valueAt(0);
        // 获取位图的无符号十进制整数
        List<Long> list = redisTemplate.opsForValue().bitField(cacheKey, bitFieldSubCommands);
        if (list == null || list.isEmpty()) {
            return null;
        }
        // 获取位图的无符号十进制整数值
        long bitMapNum = list.get(0);
        // 进行位运算判断组装那些日期有签到
        List<Map> result = new ArrayList<>();
        for (int i = days; i > 0; i--) {
            Map<String, Object> map = new HashMap<>();
            map.put("day", i);
            //先 右移，然后在 左移，如果得到的结果仍然与本身相等，则 最低位是0 所以是未签到
            if (bitMapNum >> 1 << 1 == bitMapNum) {
                map.put("active", false);
            } else {
                //与本身不等，则最低位是1 表示已签到
                map.put("active", true);
            }
            result.add(map);
            // 将位图的无符号十进制整数右移一位，准备下一轮判断
            bitMapNum >>= 1;
        }
        Collections.reverse(result);
        return result;
    }


    /**
     * 获取缓存key
     */
    private static String getCacheKey(String userNo, String date) {
        DateTime dateTime = DateUtil.parse(date, yyyy_MM);
        return String.format("USER_SIGN_IN:%s:%s", userNo, dateTime.year() + "" + dateTime.monthBaseOne());
    }


}
