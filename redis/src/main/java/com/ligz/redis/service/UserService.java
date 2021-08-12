package com.ligz.redis.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@Slf4j
public class UserService {
    public static final String KEY = "cache:user";
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, User> db = new HashMap<>();
    private final AtomicInteger atomicInteger = new AtomicInteger();

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void insert(User user) {
        db.put(user.getId(), user);
        log.info("save user success, user:{}", user);
    }

    @Retryable(value = BusinessException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000), listeners = {"retryListener"})
    public void updateSuccessAndDeleteCacheFail(User user) {
        db.put(user.getId(), user);
        log.info("save user success, user:{}", user);

        if (atomicInteger.incrementAndGet() % 3 != 0) {
            throw new BusinessException();
        }
        redisTemplate.opsForHash().delete(KEY, user.getId());
        log.info("delete user redis cache success, user:{}", user);
    }

    @Retryable(value = BusinessException.class, maxAttempts = 5, backoff = @Backoff(delay = 10000), listeners = {"retryListener"})
    public void deleteCacheSuccessAndUpdateFail(User user) {
        redisTemplate.opsForHash().delete(KEY, user.getId());
        log.info("delete user redis cache success, user:{}", user);

        if (atomicInteger.incrementAndGet() % 3 != 0) {
            throw new BusinessException();
        }
        db.put(user.getId(), user);
        log.info("update user success, user:{}", user);
    }

    public void getUser(String userId) {
        User user = db.get(userId);
        try {
            redisTemplate.opsForHash().put(KEY, userId, OBJECT_MAPPER.writeValueAsString(user));
            log.info("save user redis success, user:{}", user);
        } catch (JsonProcessingException e) {
            log.error("json error");
        }
    }

}
