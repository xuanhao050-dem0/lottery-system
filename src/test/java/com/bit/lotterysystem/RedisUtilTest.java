package com.bit.lotterysystem;

import com.bit.lotterysystem.common.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    void test(){
        redisUtil.setValue("123@qq.com","2345",60L);
    }

}
