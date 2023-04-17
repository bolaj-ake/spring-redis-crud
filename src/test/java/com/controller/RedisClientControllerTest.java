package com.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.dto.RedisRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.service.RedisAccessService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {RedisClientController.class})
@ExtendWith(SpringExtension.class)
class RedisClientControllerTest {
    @MockBean
    private RedisAccessService redisAccessService;

    @Autowired
    private RedisClientController redisClientController;

    @Test
    void testGetData() throws Exception {
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/redis/get/{datakey}", "Datakey");
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.redisClientController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(415));
    }

    @Test
    void testStoreData() throws Exception {
        when(this.redisAccessService.storeData((RedisRequest) any())).thenReturn(new ResponseEntity<>(HttpStatus.CONTINUE));

        RedisRequest redisRequest = new RedisRequest();
        redisRequest.setExpire(1);
        redisRequest.setDataKey("micasa");
        redisRequest.setData("\"{\\\"what\\\":\\\"code\\\"}\"");
        String content = (new ObjectMapper()).writeValueAsString(redisRequest);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.post("/redis/store")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content);
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(this.redisClientController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(100));
    }
}

