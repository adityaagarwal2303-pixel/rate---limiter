package com.aditya.rate_limiter.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RedisRepository {

    private final StringRedisTemplate redisTemplate;

    public RedisRepository(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    public void setExpiry(String key, long seconds) {
        redisTemplate.expire(key, seconds, TimeUnit.SECONDS);
    }

    public Long getCount(String key) {
        String value = redisTemplate.opsForValue().get(key);
        return value == null ? 0L : Long.parseLong(value);
    }

    public boolean exists(String key) {
        Boolean exists = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(exists);
    }

    public Long getTTL(String key) {
    return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    public void set(String key, String value) {
     redisTemplate.opsForValue().set(key, value);
    }
}