package com.ligz.redis.hyperlog;

import com.ligz.redis.BaseTest;
import com.ligz.redis.InitRedisData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.redisson.api.RBitSet;
import org.redisson.api.RHyperLogLog;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDailyActiveTest extends BaseTest {
    @Autowired
    private InitRedisData initRedisData;

    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void initData() {
        redissonClient.getKeys().flushall();
        initRedisData.initData("classpath:lua/init_hyperlog.lua");
    }

    /**
     * when key = 1
     * count = 4960560
     * sizeInMemory: 14384
     */
    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void count(String key) {
        RHyperLogLog rHyperLogLog = redissonClient.getHyperLogLog(key);
        long count = rHyperLogLog.count();
        System.out.println("count: " + count);
        System.out.println("sizeInMemory: " + rHyperLogLog.sizeInMemory());
    }

    @Test
    public void daysActive() {
        RHyperLogLog hyperLogLog = redissonClient.getHyperLogLog("result");

        hyperLogLog.mergeWith("1", "2", "3", "4", "5");
        long count = hyperLogLog.count();
        System.out.println("days active users count: " + count);
        redissonClient.getKeys().delete("result");
    }

}
