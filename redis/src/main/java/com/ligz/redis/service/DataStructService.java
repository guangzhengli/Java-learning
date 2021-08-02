package com.ligz.redis.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBucket;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class DataStructService {
    private final RedisTemplate<String, String> redisTemplate;
    private final RedissonClient redissonClient;

    public void storeString(String value) {
        redisTemplate.opsForValue().setIfAbsent("redisTemplate", value);
        RBucket<String> rBucket = redissonClient.getBucket("redission");
        rBucket.trySet(value);
    }

    public void storeList(List<String> values) {
        redisTemplate.opsForList().leftPushAll("redisTemplateList", values);
        RList<String> rList = redissonClient.getList("redissionList");
        rList.addAll(values);
    }

    public String getString(String key) {
        String redisTemplateValue = redisTemplate.opsForValue().get(key);
        RBucket<String> rBucket = redissonClient.getBucket(key);
        String redissonValue = rBucket.get();
        log.info("redisTemplate: {}, redisson:{}", redisTemplateValue, redissonValue);
        return redisTemplateValue;
    }
}
