package com.bit.lotterysystem;

import cn.hutool.core.lang.Validator;
import org.junit.jupiter.api.Test;
import org.springframework.validation.annotation.Validated;

public class RegexUtil {
    @Test
    void RegexTest(){
        boolean email = Validator.isEmail("1392755975@qq.com");
        System.out.println(email);
    }

    @Test
    void phoneTest(){
        System.out.println(Validator.isMobile("13927555976"));
    }
}
