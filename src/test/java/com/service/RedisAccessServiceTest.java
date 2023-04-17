package com.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.dto.RedisRequest;
import com.dto.RedisResponse;
import com.redisaccess.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {RedisAccessService.class})
@ExtendWith(SpringExtension.class)
class RedisAccessServiceTest {
    @Autowired
    private RedisAccessService redisAccessService;

    @MockBean
    private RedisService redisService;

    @Test
    void testStoreData() {
        when(this.redisService.isDataExist((String) any())).thenReturn(true);

        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setExpire(1);
        redisRequest.setDataKey("dataKey");
        redisRequest.setData("\"{\\\"what\\\":\\\"code\\\"}\"");
        ResponseEntity<RedisResponse> actualStoreDataResult = this.redisAccessService.storeData(redisRequest);
        assertNull(actualStoreDataResult.getBody());
        assertEquals("<409,[]>", actualStoreDataResult.toString());
        assertEquals(409, actualStoreDataResult.getStatusCodeValue());
        assertTrue(actualStoreDataResult.getHeaders().isEmpty());
        verify(this.redisService).isDataExist((String) any());
    }

    @Test
    void testStoreData2() {
        when(this.redisService.insertData((String) any(), (String) any())).thenReturn(true);
        when(this.redisService.isDataExist((String) any())).thenReturn(false);

        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setExpire(1);
        redisRequest.setDataKey("dataKey");
        redisRequest.setData("{\"what\":\"code\"}");
        ResponseEntity<RedisResponse> actualStoreDataResult = this.redisAccessService.storeData(redisRequest);
        assertTrue(actualStoreDataResult.getHeaders().isEmpty());
        assertTrue(actualStoreDataResult.hasBody());
        assertEquals(HttpStatus.OK, actualStoreDataResult.getStatusCode());
        assertEquals("Success", actualStoreDataResult.getBody().getResponse());
        verify(this.redisService).insertData((String) any(), (String) any());
        verify(this.redisService).isDataExist((String) any());
    }

    @Test
    void testStoreData3() {
        when(this.redisService.insertData((String) any(), (String) any())).thenReturn(false);
        when(this.redisService.isDataExist((String) any())).thenReturn(false);

        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setExpire(1);
        redisRequest.setDataKey("dataKey");
        redisRequest.setData("{\"what\":\"code\"}");
        ResponseEntity<RedisResponse> actualStoreDataResult = this.redisAccessService.storeData(redisRequest);
        assertTrue(actualStoreDataResult.getHeaders().isEmpty());
        assertTrue(actualStoreDataResult.hasBody());
        assertEquals(HttpStatus.BAD_REQUEST, actualStoreDataResult.getStatusCode());
        assertEquals("Failed", actualStoreDataResult.getBody().getResponse());
        verify(this.redisService).insertData((String) any(), (String) any());
        verify(this.redisService).isDataExist((String) any());
    }

    @Test
    void testStoreData4() {
        when(this.redisService.insertData((String) any(), (String) any(), (Integer) any())).thenReturn(true);
        when(this.redisService.insertData((String) any(), (String) any())).thenReturn(true);
        when(this.redisService.isDataExist((String) any())).thenReturn(false);

        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setExpire(0);
        redisRequest.setDataKey("dataKey");
        redisRequest.setData("{\"what\":\"code\"}");
        ResponseEntity<RedisResponse> actualStoreDataResult = this.redisAccessService.storeData(redisRequest);
        assertTrue(actualStoreDataResult.getHeaders().isEmpty());
        assertTrue(actualStoreDataResult.hasBody());
        assertEquals(HttpStatus.OK, actualStoreDataResult.getStatusCode());
        assertEquals("Success", actualStoreDataResult.getBody().getResponse());
        verify(this.redisService).insertData((String) any(), (String) any(), (Integer) any());
        verify(this.redisService).isDataExist((String) any());
    }

    @Test
    void testGetData() {
        when(this.redisService.getData((String) any())).thenReturn("{\"what\":\"code\"}");
        ResponseEntity<RedisResponse> actualData = this.redisAccessService.getData("dataKey");
        assertTrue(actualData.getHeaders().isEmpty());
        assertTrue(actualData.hasBody());
        assertEquals(HttpStatus.OK, actualData.getStatusCode());
        RedisResponse body = actualData.getBody();
        assertEquals("Success", body.getResponse());
        assertEquals("dataKey", body.getDataKey());
        assertEquals("{\"what\":\"code\"}", body.getData());
        verify(this.redisService).getData((String) any());
    }

    @Test
    void testGetData2() {
        when(this.redisService.getData((String) any())).thenReturn(null);
        ResponseEntity<RedisResponse> actualData = this.redisAccessService.getData("dataKey");
        assertNull(actualData.getBody());
        assertEquals("<204,[]>", actualData.toString());
        assertEquals(204, actualData.getStatusCodeValue());
        assertTrue(actualData.getHeaders().isEmpty());
        verify(this.redisService).getData((String) any());
    }
}

