package com.ligz.redis.bit;

import com.ligz.redis.BaseTest;
import com.ligz.redis.InitRedisData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.redisson.api.RBitSet;
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
        initRedisData.initData("classpath:lua/init_bitmap.lua");
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void count(String key) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        long count = bitSet.cardinality();
        System.out.println("size: " + bitSet.size());
        System.out.println("count: " + count);
        System.out.println("sizeInMemory: " + bitSet.sizeInMemory());
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3", "4", "5"})
    public void dayActive(String key) {
        RBitSet bitSet = redissonClient.getBitSet(key);
        System.out.println("is active? " + bitSet.get(100));
    }

    @ParameterizedTest
    @ValueSource(strings = {"or", "and"})
    public void daysActive(String ops) {
        String lua = "redis.call('bitop', '" + ops + "', 'result', 1, 2, 3, 4 ,5)";

        RScript script = redissonClient.getScript();
        String SHA = script.scriptLoad(lua);
        script.evalSha(RScript.Mode.READ_WRITE, SHA, RScript.ReturnType.VALUE);

        RBitSet bitSet = redissonClient.getBitSet("result");
        long count = bitSet.cardinality();
        System.out.println("days active users count: " + count);
        redissonClient.getKeys().delete("result");
    }

}
