package com.tenpo.api.service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserLogedCache {

    private final LoadingCache<String, String> cache;

    public UserLogedCache() {
        CacheLoader<String, String> loader;
        loader = new CacheLoader<String, String>() {
            @Override
            public String load(String key) {
                return "";
            }
        };

        cache = CacheBuilder.newBuilder()
                .maximumSize(100000)
                .expireAfterWrite(15, TimeUnit.MINUTES)
                .build(loader);
    }

    public void removeUser(String token) {
        cache.invalidate(token);
    }

    public void addUser(String token, String username) {
        cache.put(token, username);
    }

    public boolean isUserOnCache(String token) {
        return (cache.getIfPresent(token) != null);
    }

    public LoadingCache<String, String> getCache() {
        return cache;
    }

}
