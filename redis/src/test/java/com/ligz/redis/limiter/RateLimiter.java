package com.ligz.redis.limiter;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

public class RateLimiter extends BaseTest {
    @Autowired
    private RedissonClient redissonClient;

    @BeforeEach
    public void init() {
        redissonClient.getKeys().flushall();
    }

    @Test
    public void rate() {
        RRateLimiter rRateLimiter = redissonClient.getRateLimiter("rate");
        rRateLimiter.trySetRate(RateType.OVERALL, 2, 1, RateIntervalUnit.SECONDS);
        for (int i = 0; i < 10; i++) {
            rRateLimiter.acquire(1);
            System.out.println("get a permits: " + new Date(System.currentTimeMillis()));
        }
    }
}
