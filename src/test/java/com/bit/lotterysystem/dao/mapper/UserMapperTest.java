package com.bit.lotterysystem.dao.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    @Test
    void selectExistId() {
        List<Long> list=[40L,41L];
        List<Long> list1=userMapper.selectExistId(list);
    }
}