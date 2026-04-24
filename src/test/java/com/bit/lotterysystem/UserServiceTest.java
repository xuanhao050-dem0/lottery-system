package com.bit.lotterysystem;

import com.bit.lotterysystem.controller.param.UserLoginByVerificationCodeParam;
import com.bit.lotterysystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserServiceTest {
    @Autowired
    UserServiceImpl userService;
    @Test
    void loginByCode(){
        UserLoginByVerificationCodeParam userLoginByVerificationCodeParam=new UserLoginByVerificationCodeParam();
        userLoginByVerificationCodeParam.setEmail("yixh_8464@qq.com");
        userLoginByVerificationCodeParam.setVerification("838519");
        userLoginByVerificationCodeParam.setIdentity("ADMIN");

        userService.userLoginByVerificationCode(userLoginByVerificationCodeParam);
    }
}
