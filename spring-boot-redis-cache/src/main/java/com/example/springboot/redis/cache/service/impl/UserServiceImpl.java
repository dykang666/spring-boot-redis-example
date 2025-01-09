package com.example.springboot.redis.cache.service.impl;

import com.example.springboot.redis.cache.entity.User;
import com.example.springboot.redis.cache.service.UserService;


import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

/**
 * 主要应用注解的方式应用缓存
 * @Cacheable 可以标记在类上，也可以标记在方法上，如果标记在方法上，每次调用方法之后，都会将调用的返回值存入缓存中，以保证下次利用同样的参数调用方法时，可以直接从缓存中获得结果，而不需要再次执行该方法。
 * @CachePut 该注解标记的方法，不同于@Cacheable，不会向缓存中查找是否存在之前的执行结果，而是每次都会执行该方法，并将执行的结果以键值对的方式存入缓存中。
 * @CacheEvict 是用于标记在需要清除缓存的方法或类上面的
 */

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    /**
     * 模拟数据库
     */
    private static final Map<Long, User> DATABASES = new ConcurrentMap<Long, User>() {
        @Override
        public User putIfAbsent(Long key, User value) {
            return null;
        }

        @Override
        public boolean remove(Object key, Object value) {
            return false;
        }

        @Override
        public boolean replace(Long key, User oldValue, User newValue) {
            return false;
        }

        @Override
        public User replace(Long key, User value) {
            return null;
        }

        @Override
        public int size() {
            return 0;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public boolean containsKey(Object key) {
            return false;
        }

        @Override
        public boolean containsValue(Object value) {
            return false;
        }

        @Override
        public User get(Object key) {
            return null;
        }

        @Override
        public User put(Long key, User value) {
            return null;
        }

        @Override
        public User remove(Object key) {
            return null;
        }

        @Override
        public void putAll(Map<? extends Long, ? extends User> m) {

        }

        @Override
        public void clear() {

        }

        @Override
        public Set<Long> keySet() {
            return null;
        }

        @Override
        public Collection<User> values() {
            return null;
        }

        @Override
        public Set<Entry<Long, User>> entrySet() {
            return null;
        }
    };
    /**
     * 初始化数据
     */
    static {
        DATABASES.put(1L, new User(1L, "user1"));
        DATABASES.put(2L, new User(2L, "user2"));
        DATABASES.put(3L, new User(3L, "user3"));
    }
    /**
     * 保存或修改用户
     *
     * @param user 用户对象
     * @return 操作结果
     */
    @CachePut(value = "user",key = "#user.id")
    @Override
    public User saveOrUpdate(User user) {
        DATABASES.put(user.getId(), user);
        log.info("保存用户【user】= {}", user);
        return user;
    }
    /**
     * 获取用户
     *
     * @param id key值
     * @return 返回结果
     */
    @Cacheable(value = "user",key = "#id")
    @Override
    public User get(Long id) {
        // 我们假设从数据库读取
        log.info("查询用户【id】= {}", id);

        return DATABASES.get(id);
    }
    /**
     * 删除
     *
     * @param id key值
     */
    @CacheEvict(value = "user",key = "#id")
    @Override
    public void delete(Long id) {
        DATABASES.remove(id);
        log.info("删除用户【id】= {}", id);
    }
}
