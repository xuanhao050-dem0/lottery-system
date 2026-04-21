package com.bit.lotterysystem;

import com.bit.lotterysystem.common.utils.MailUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MailUtilTest {
    @Autowired
    private MailUtil mailUtil;

    @Test
    void setMailUtil(){
        mailUtil.sendSimpleMail("yixh_8464@qq.com","【抽奖系统登录】","您的验证码是1234");
    }

}
