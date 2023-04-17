package com.redisaccess;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.when;


class RedisServiceTest {

    @MockBean
    private HashOperations hashOperations;

    @Test
    void testInsertData() {

        assertFalse((new RedisService(new RedisTemplate<>())).insertData("insKey", "{\"what\":\"code\"}"));
        assertFalse((new RedisService(new RedisTemplate<>())).insertData("insKey", "{\"what\":\"code\"}", 1));

    }

    @Test
    void testIsDataExist() {
        assertFalse((new RedisService(new RedisTemplate<>())).isDataExist("{\"superwhat\":\"code\"}"));
    }

    @Test
    void testGetData() {
        assertNull((new RedisService(new RedisTemplate<>())).getData("getKey"));
    }
}

