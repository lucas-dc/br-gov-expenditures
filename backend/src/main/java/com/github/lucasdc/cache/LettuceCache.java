package com.github.lucasdc.cache;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.api.sync.RedisCommands;
import org.springframework.cache.Cache;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

public class LettuceCache implements Cache {

    private final String name;
    private final RedisCommands<String, String> syncCommands;
    private final long ttlInSeconds;
    private final ObjectMapper objectMapper;

    public LettuceCache(String name, RedisCommands<String, String> syncCommands, long ttlInSeconds) {
        this.name = name;
        this.syncCommands = syncCommands;
        this.ttlInSeconds = ttlInSeconds;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public Object getNativeCache() {
        return this.syncCommands;
    }

    @Override
    public ValueWrapper get(Object key) {
        throw new RuntimeException("Unsupported method. Use <T> T get(Object key, Class<T> collectionType, Class<?> elementType) instead");
    }

    @Override
    public <T> T get(Object key, Callable<T> valueLoader) {
        throw new RuntimeException("Unsupported method. Use <T> T get(Object key, Class<T> collectionType, Class<?> elementType) instead ");
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        throw new RuntimeException("Unsupported method. Use <T> T get(Object key, Class<T> collectionType, Class<?> elementType) instead");
    }

    public <T> T get(Object key, Class<T> collectionType, Class<?> elementType) {
        String value = syncCommands.get(name + ":" + key);

        if (value == null) {
            return null;
        }

        try {
            JavaType javaType = objectMapper.getTypeFactory().constructCollectionType((Class<? extends Collection>) collectionType, elementType);
            return objectMapper.readValue(value, javaType);
        } catch (IOException e) {
            throw new RuntimeException("Failed to deserialize cached value", e);
        }
    }

    @Override
    public void put(Object key, Object value) {
        try {
            if (value instanceof List<?>) {
                String jsonValue = objectMapper.writeValueAsString(value);
                syncCommands.setex(name + ":" + key, ttlInSeconds, jsonValue);
            } else {
                throw new IllegalArgumentException("Unsupported cache value type");
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to serialize value for caching", e);
        }
    }

    @Override
    public void evict(Object key) {
        syncCommands.del(name + ":" + key);
    }

    @Override
    public void clear() {
        syncCommands.keys(name + ":*").forEach(syncCommands::del);
    }
}