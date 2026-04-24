package com.bit.lotterysystem;

import cn.hutool.crypto.digest.DigestUtil;
import com.bit.lotterysystem.dao.dateobject.Encrypt;
import com.bit.lotterysystem.dao.dateobject.UserDO;
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

    @Test
    void countByPhone(){
        System.out.println("查找结果："
                + userMapper.countByPhoneNumber(new Encrypt("13922222222")));
    }
    //如何测试
    @Test
    void insert(){
        UserDO userDO=new UserDO();
        userDO.setUserName("yixh");
        userDO.setEmail("yixh_8464@qq.com");
        userDO.setPhoneNumber(new Encrypt("13927555975"));
        userDO.setIdentity("ADMIN");
        userDO.setPassword(DigestUtil.sha256Hex("123456"));
        userMapper.insertUserInfo(userDO);
    }

//    @Test
//    void selectByEmail(){
//        System.out.println(userMapper.selectByEmail(""));
//    }

    @Test
    void delete(){
        System.out.println(userMapper.deleteByUsername("yixh"));
    }
}
