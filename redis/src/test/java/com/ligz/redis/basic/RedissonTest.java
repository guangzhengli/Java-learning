package com.ligz.redis.basic;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.api.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class RedissonTest extends BaseTest {
    @Autowired
    private RedissonClient redissonClient;

    @Test
    public void string() {
        RBucket<String> rBucket = redissonClient.getBucket("rss");
        rBucket.trySet("hello");
    }

    @Test
    public void deleteString() {
        RBucket<String> rBucket = redissonClient.getBucket("rss");
        rBucket.getAndDelete();
    }

    @Test
    public void storeList(List<String> values) {
        RList<String> rList = redissonClient.getList("redissionList");
        rList.addAll(values);
    }

    @Test
    public void getString(String key) {
        RBucket<String> rBucket = redissonClient.getBucket(key);
        String redissonValue = rBucket.get();
    }
}
