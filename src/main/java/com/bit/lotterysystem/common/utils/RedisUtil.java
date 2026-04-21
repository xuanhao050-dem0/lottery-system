package com.bit.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;

@Slf4j
@Configuration
public class RedisUtil {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 在缓存存放值
     * @param key
     * @param value
     * @return
     */
    public boolean setValue(String key,String value){
        try{
            stringRedisTemplate.opsForValue().set(key, value);
            return true;
        }catch (Exception e){
            log.error("RedisUtil error ,set({},{})",key,value,e);
            return false;
        }
    }

    /**
     * 设置过期时间
     * @param key
     * @param value
     * @param time second
     * @return
     */
    public boolean setValue(String key,String value,Long time){
        try{
            stringRedisTemplate.opsForValue().set(key, value,time, TimeUnit.SECONDS);
            return true;
        }catch (Exception e){
            log.error("RedisUtil error ,set({},{},{})",key,value,time,e);
            return false;
        }
    }

    /**
     * 获取值
     * @param key
     * @return
     */
    public String getValue(String key){
        try{
            return StringUtils.hasText(key)
                    ? stringRedisTemplate.opsForValue().get(key)
                    :null;
        }catch (Exception e){
            log.error("RedisUtil error ,get({})",key,e);
            return null;
        }
    }

}
