package com.redisaccess;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.concurrent.TimeUnit;

/**
 * @author mobolajiake
 */
@Slf4j
@Repository
public class RedisService {

    private final RedisTemplate<String, String> redisTemplate;
    private HashOperations hashOperations;
    @Value("${app.redis.data.expire}")
    private String sessionExpire;
    private String prefixSession = "SAMPLE_DATA";

    public RedisService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
    @PostConstruct
    private void init(){
        hashOperations = redisTemplate.opsForHash();
    }

    /**
     * to insert data into redis
     * @param dataKey dataKey
     * @param jsonData string to be returned
     * @return true/false
     * @see Boolean
     */
    public Boolean insertData(String dataKey,String jsonData){

        try{

            hashOperations.put(prefixSession + ":" + dataKey, dataKey, jsonData);
            redisTemplate.expire(prefixSession + ":" + dataKey, Integer.valueOf(sessionExpire), TimeUnit.MINUTES);
            return true;

        }catch (Exception e){
            log.info(" Redis Service Exception ");
        }

        return false;

    }

    /**
     * to insert data into redis
     * @param dataKey dataKey
     * @param jsonData string to be returned
     * @return true/false
     * @see Boolean
     */
    public Boolean insertData(String dataKey,String jsonData,Integer dataExpiry){

        try {

            hashOperations.put(prefixSession + ":" + dataKey, dataKey, jsonData);
            redisTemplate.expire(prefixSession + ":" + dataKey, dataExpiry, TimeUnit.MINUTES);
            return true;

        }catch (Exception e){

            log.info(" Redis Service Exception ");

        }
        return false;
    }
    /**
     * Redis Check if  Data Exist
     * @param dataKey dataKey
     * @return true/false
     * @see Boolean
     */
    public Boolean isDataExist(String dataKey){

        try{
            Object tokenObj = hashOperations.get(prefixSession + ":" + dataKey, dataKey);
            if (tokenObj != null) {
                return true;
            }

        }catch(Exception e){

            log.info(" Redis Service Exception ");

        }

        return false;
    }

    /**
     * Get Data from redis
     * @param dataKey dataKey
     * @return String
     * @see String
     */

    public String getData (String dataKey){

        try{

            log.info("Redis Prefix is --> {}",prefixSession+ ":" + dataKey);
            Object tokenObj = hashOperations.get(prefixSession + ":" + dataKey, dataKey);

            if (tokenObj == null) {
                return null;
            }

            return (String) tokenObj;

        }catch (Exception e){

            log.info(" Redis Service Exception ");
            return null;

        }

    }


    public void deleteData(String dataKey) {
        try {
            hashOperations.delete(prefixSession + ":" + dataKey, dataKey);
        }catch (Exception e){
            log.error(" Exception Deleting Redis {}",dataKey);
        }
    }


}
