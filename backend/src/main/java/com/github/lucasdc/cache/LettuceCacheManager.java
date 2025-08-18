package com.github.lucasdc.cache;

import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class LettuceCacheManager implements CacheManager {
    private final RedisCommands<String, String> syncCommands;
    private final Map<String, LettuceCache> cacheMap;

    public LettuceCacheManager(StatefulRedisConnection<String, String> redisConnection) {
        this.syncCommands = redisConnection.sync();
        this.cacheMap = new HashMap<>();
    }

    @Override
    public Cache getCache(String name) {
        long ttl = 3600 * 24 * 5; // 5 days
        return cacheMap.computeIfAbsent(name, cacheName -> new LettuceCache(cacheName, syncCommands, ttl));
    }

    @Override
    public Collection<String> getCacheNames() {
        return cacheMap.keySet();
    }
}
