package com.controller;


import com.dto.RedisRequest;
import com.dto.RedisResponse;
import com.redisaccess.RedisService;
import com.service.RedisAccessService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = {"/redis"})
public class RedisClientController {

    private RedisAccessService redisAccessService;


    public RedisClientController(RedisAccessService redisAccessService) {
        this.redisAccessService=redisAccessService;
    }


    @PostMapping (value = {"/store"}, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<RedisResponse> storeData(@RequestBody RedisRequest redisRequest){
        log.info("\n\n\n");
        log.info(">>>>NEW REDIS POST REQUEST FROM CLIENT::");
        log.info("Data : {}", redisRequest.toString());
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
        return redisAccessService.storeData(redisRequest);
    }

    @GetMapping(value = {"/get/{datakey}"}, consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<RedisResponse> getData(@PathVariable String datakey){
        log.info("\n\n\n");
        log.info(">>>>NEW REDIS GET REQUEST FROM CLIENT::");
        log.info("Data : {}", datakey);
        log.info("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
       return redisAccessService.getData(datakey);
    }








}
