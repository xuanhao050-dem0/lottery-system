package com.bit.lotterysystem;

import com.bit.lotterysystem.common.utils.RegexUtil;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RegexUtilTest {
    @Test
    void RegexUtilTest1(){

        UserRegisterParam userRegisterParam=new UserRegisterParam();
        userRegisterParam.setEmail("123@qq.com");

        boolean b = RegexUtil.checkMail(userRegisterParam.getEmail());
        System.out.println(b);


    }
}
