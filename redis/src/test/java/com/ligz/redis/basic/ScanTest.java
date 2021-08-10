package com.ligz.redis.basic;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RKeys;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import static com.google.common.collect.Lists.newArrayList;

public class ScanTest extends BaseTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private final AtomicLong atomicLong = new AtomicLong(1000);

    @BeforeEach
    public void init() {
        redissonClient.getKeys().flushall();
    }

    @Test
    public void scan() {
        redisTemplate.opsForValue().multiSet(buildImageId());
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions().match("userId*").count(10).build();
            Cursor c = redisConnection.scan(options);
            while (c.hasNext()) {
                System.out.println(new String((byte[]) c.next()));
            }
        } finally {
            redisConnection.close();
        }
    }

    @Test
    public void redissonScan() {
        redisTemplate.opsForValue().multiSet(buildImageId());
        RKeys rKeys = redissonClient.getKeys();
        rKeys.getKeysByPattern("groupId*", 10).forEach(System.out::println);
    }

    private Map<String, String> buildImageId() {
        Map<String, String> map = new HashMap<>();
        List<String> list = newArrayList("userId", "projectId", "groupId");
        for (String prefix : list) {
            for (int i = 0; i < 100; i++) {
                map.put(prefix + generateString(), generateString());
            }
        }
        return map;
    }

    private String generateString() {
        return String.valueOf(atomicLong.incrementAndGet());
    }
}
