package com.tenpo.api.services;

import com.google.common.cache.LoadingCache;
import com.tenpo.api.service.UserLogedCache;
import java.util.concurrent.ExecutionException;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserLogedCacheTest {

    private static final String TOKEN = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJuaWNvdGVucG8iLCJyb2xlcyI6WyJVU0VSX1JPTEUiXSwiZXhwIjoxNjM2MzE5ODAzLCJpYXQiOjE2MzYzMTg5MDN9.CgqlNzSBsSdF4lph2cSD-mUUdeYiLeyaNtGK2egUG7s";
    private static final String USERNAME = "JhonUser";
    private UserLogedCache service;
    private LoadingCache<String, String> cache;

    @BeforeEach
    public void setUp() {
        service = new UserLogedCache();
        cache = service.getCache();

    }

    @Test
    public void addUserOnCache() throws ExecutionException {
        service.addUser(TOKEN, USERNAME);

        assertEquals(USERNAME, cache.get(TOKEN));
    }

    @Test
    public void removeUserFromCache() throws ExecutionException {
        cache.put(TOKEN, USERNAME);

        service.removeUser(TOKEN);

        assertEquals("", cache.get(TOKEN));
    }

    @Test
    public void isUserOnCacheTrue() throws ExecutionException {
        cache.put(TOKEN, USERNAME);

        boolean result = service.isUserOnCache(TOKEN);

        assertTrue(result);
    }

    @Test
    public void isUserOnCacheFalse() throws ExecutionException {
        boolean result = service.isUserOnCache(TOKEN);

        assertFalse(result);
    }

}
