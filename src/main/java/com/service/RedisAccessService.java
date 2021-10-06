package com.service;

import com.dto.RedisRequest;
import com.dto.RedisResponse;
import com.redisaccess.RedisService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;


/**
 * @Author Mobolaji AKe
 */
@Service
public class RedisAccessService {

    private RedisService redisService;

    public RedisAccessService(RedisService redisService){
        this.redisService=redisService;
    }

    /**
     * This function helps to process savinng of data into redis
     * @param redisRequest this is the request object
     * @return ResponseEntity<RedisResponse>
     */
    public ResponseEntity<RedisResponse> storeData(RedisRequest redisRequest){

        Boolean isInsert=false;
        RedisResponse redisResponse = new RedisResponse();
        /**
         * Check if Data Exist in Redis
         */
        if (redisService.isDataExist(redisRequest.getDataKey())){
            return ResponseEntity.status(409).body(null);
        }

        /**
         * Connfirm if expire has been set else use defualt
         */
        if(redisRequest.getExpire() > 0){
            isInsert=redisService.insertData(redisRequest.getDataKey(),redisRequest.getData());
        }else{
            isInsert=redisService.insertData(redisRequest.getDataKey(), redisRequest.getData(), redisRequest.getExpire());
        }

        /**
         * if insert is successful return positive feedback
         */
        if(isInsert){
            redisResponse.setResponse("Success");
            return ResponseEntity.ok(redisResponse);
        }

        redisResponse.setResponse("Failed");
        return ResponseEntity.badRequest().body(redisResponse);

    }

    /**
     * This function is used to fetch data from redis
     * @param dataKey
     * @return ResponseEntity<RedisResponse>
     */
    public ResponseEntity<RedisResponse> getData(String dataKey) {
        String data = redisService.getData(dataKey);
        RedisResponse redisResponse = new RedisResponse();
        redisResponse.setDataKey(dataKey);
        /**
         * if no data is found return 204 no content
         */
        if (data == null) {
            redisResponse.setResponse("Not Found");
            return ResponseEntity.status(204).body(null);
        }

        redisResponse.setResponse("Success");
        redisResponse.setData(data);
        return ResponseEntity.ok(redisResponse);
    }
}
