package com.ligz.redis.zip;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicLong;

public class ImageStoreTest extends BaseTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private AtomicLong atomicLong = new AtomicLong(1000000000);
    @Test
    public void clear() {
        Set<String> set = redisTemplate.keys("*");
        if (set != null && set.size() != 0) {
            redisTemplate.delete(set);
        }
    }

    /**
     * 1000000 number cost 70m when use the string
     */
    @Test
    public void string() {
        for (int i = 0; i < 10; i++) {
            redisTemplate.opsForValue().multiSet(buildImageId());
        }
        System.out.println("watch redis memory usage");
    }

    /**
     * use zip list to replace string hash value
     *
     * 1000000 number cost 10m when use the zip list
     */
    @Test
    public void zipList() {
        Map<String, String> map = buildImageIds();
        Map<String, Map<String, String>> values = new HashMap<>();
        map.forEach((key, value) -> {
            String imageId = key.substring(0, 7);
            String storeId = key.substring(7, 10);
            Map<String, String> storeIdMap;
            if (values.containsKey(imageId)) {
                storeIdMap = values.get(imageId);
            } else {
                storeIdMap = new HashMap<>();
            }
            storeIdMap.put(storeId, value);
            values.put(imageId, storeIdMap);
        });
        values.forEach((key, value) -> {
            redisTemplate.opsForHash().putAll(key, value);
        });
        System.out.println("watch redis memory usage");
    }

    private Map<String, String> buildImageId() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 100000; i++) {
            map.put(generateString(), generateString());
        }
        return map;
    }

    private Map<String, String> buildImageIds() {
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < 1000000; i++) {
            map.put(generateString(), generateString());
        }
        return map;
    }

    private String generateString() {
        return String.valueOf(atomicLong.incrementAndGet());
    }
}
