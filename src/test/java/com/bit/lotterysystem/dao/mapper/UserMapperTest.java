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
        List<Long> list=List.of(40l,41l);
        List<Long> list1=userMapper.selectExistId(list);
        System.out.println(list1);
    }
}