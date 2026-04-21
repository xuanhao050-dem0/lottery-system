package com.bit.lotterysystem;

import com.bit.lotterysystem.service.impl.VerificationCodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class VerificationCodeServiceTest {
    @Autowired
    VerificationCodeServiceImpl verificationCodeService;

    @Test
    void test(){
        verificationCodeService.setVerificationCodeService("wang_wt_5789@163.com");
        System.out.println(verificationCodeService.getVerificationCodeService("wang_wt_5789@163.com"));
    }

    @Test
    void test1(){
        verificationCodeService.setVerificationCodeService("yixh_8464@qq.com");
    }
}
