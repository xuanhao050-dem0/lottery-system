package com.bit.lotterysystem;

import com.bit.lotterysystem.dao.mapper.UserMapper;
import org.apache.ibatis.annotations.Select;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    void countByMail(){
        System.out.println("查找结果："+userMapper.countByEmail("zhangsan@example.com"));
    }

}
