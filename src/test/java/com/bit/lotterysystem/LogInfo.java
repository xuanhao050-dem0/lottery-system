package com.bit.lotterysystem;

import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;


//使用springboot会 启动整个springboot容器，没有数据库配置会报错！！
@SpringBootTest
public class LogInfo {

    public final static Logger logger= LoggerFactory.getLogger(LogInfo.class);

    @Test
    void logTest(){



        System.out.println("hello world");

        logger.info("hello world");

    }



}
