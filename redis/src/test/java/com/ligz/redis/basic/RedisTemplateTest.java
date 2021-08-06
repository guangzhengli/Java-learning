package com.ligz.redis.basic;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

public class RedisTemplateTest extends BaseTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Test
    public void string() {
        String key = "rts";
        String value = "hello";
        redisTemplate.delete(key);

        redisTemplate.opsForValue().setIfAbsent(key, value);
        assertEquals(value, redisTemplate.opsForValue().get(key));

        redisTemplate.opsForValue().getAndSet(key, "world");
        assertEquals("world", redisTemplate.opsForValue().get(key));

        redisTemplate.delete(key);
        assertNull(redisTemplate.opsForValue().get(key));
    }

    @Test
    public void list() {
        String key = "rtl";
        List<String> values = newArrayList("a", "b", "c");
        redisTemplate.delete(key);

        redisTemplate.opsForList().leftPushAll(key, values);
        assertEquals(3, redisTemplate.opsForList().size(key));
        assertEquals("c", redisTemplate.opsForList().index(key, 0));

        redisTemplate.opsForList().leftPushAll(key, "d", "e");
        assertEquals(5, redisTemplate.opsForList().size(key));
        assertEquals("a", redisTemplate.opsForList().index(key, 4));

        List<String> result = redisTemplate.opsForList().range(key, 0, -1);
        List<String> excepted = newArrayList("e", "d", "c", "b", "a");
        assertEquals(excepted, result);

        redisTemplate.delete(key);
        assertEquals(0, redisTemplate.opsForList().size(key));
    }

    @Test
    public void hash() {
        String key = "rth";
        Map<String, String> map = new HashMap<>();
        map.put("a", "1");
        redisTemplate.delete(key);

        redisTemplate.opsForHash().putAll(key, map);
        redisTemplate.opsForHash().put(key, "b", "2");

        assertEquals("2", redisTemplate.opsForHash().get(key, "b"));
        redisTemplate.opsForHash().delete(key, "b");
        assertFalse(redisTemplate.opsForHash().hasKey(key, "b"));

        redisTemplate.delete(key);
        assertEquals(0, redisTemplate.opsForHash().size(key));
    }
}
