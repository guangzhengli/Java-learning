package com.ligz.redis.string;

import com.ligz.redis.BaseTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class AccessRecord extends BaseTest {
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private static Stream<Arguments> provideParameters() {
        return Stream.of(
                Arguments.of("1", 1),
                Arguments.of("2", 2),
                Arguments.of("3", 3),
                Arguments.of("4", 5),
                Arguments.of("5", 10)
        );
    }

    @ParameterizedTest
    @MethodSource("provideParameters")
    public void record(String userId, int times) {
        String key = "url:";
        for (int i = 0; i < times; i++) {
            if (redisTemplate.opsForValue().get(key +  userId) == null) {
                redisTemplate.opsForValue().setIfAbsent(key + userId, String.valueOf(1), 10, TimeUnit.MINUTES);
            } else {
                Long accessCount = redisTemplate.opsForValue().increment(key + userId);
                if (accessCount >= 5) {
                    System.out.println("access count greater than 5 times in 10 min");
                }
            }
        }

    }
}
