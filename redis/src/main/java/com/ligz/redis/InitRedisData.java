package com.ligz.redis;

import com.google.common.collect.Lists;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RScript;
import org.redisson.api.RedissonClient;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
public class InitRedisData {
    private final RedissonClient redissonClient;
    private final ResourceLoader resourceLoader;

    public void initData(String luaPath) {
        Resource resource = resourceLoader.getResource(luaPath);
        String lua = asString(resource);

        RScript script = redissonClient.getScript();
        String SHA = script.scriptLoad(lua);
        script.evalSha(RScript.Mode.READ_WRITE, SHA, RScript.ReturnType.VALUE, Lists.newArrayList());
    }

    private static String asString(Resource resource) {
        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
