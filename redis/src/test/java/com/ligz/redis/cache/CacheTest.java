package com.ligz.redis.cache;

import com.ligz.redis.BaseTest;
import com.ligz.redis.service.User;
import com.ligz.redis.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import static com.ligz.redis.service.UserService.KEY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CacheTest extends BaseTest {
    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private User user;

    @BeforeEach
    public void init() {
        redissonClient.getKeys().flushall();
        user = new User("1", "lee", "123");
        userService.insert(user);
    }

    @Test
    public void getCacheSuccess() {
        userService.getUser("1");
        String result = (String) redisTemplate.opsForHash().get(KEY, "1");
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void updateSuccessAndDeleteCacheFail() {
        user.setGroupId("456");
        userService.updateSuccessAndDeleteCacheFail(user);
        userService.getUser("1");
        String result = (String) redisTemplate.opsForHash().get(KEY, "1");
        assertNotNull(result);
        System.out.println(result);
    }

    @Test
    public void deleteCacheSuccessAndUpdateFail() {
        user.setGroupId("789");
        userService.deleteCacheSuccessAndUpdateFail(user);
        userService.getUser("1");
        String result = (String) redisTemplate.opsForHash().get(KEY, "1");
        assertNotNull(result);
        System.out.println(result);
    }
}
