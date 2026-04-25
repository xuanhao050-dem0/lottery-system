package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.controller.result.GetUserInfoResult;
import com.bit.lotterysystem.service.dto.GetUserInfoDTO;
import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import com.bit.lotterysystem.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    @Autowired
    UserServiceImpl userService;

    @Test
    void getUserList() {
        List<GetUserInfoDTO>userInfoResults= userService.getUserInfo(UserIdentityEnum.forName(null));
        userInfoResults.stream().forEach(item -> System.out.println("user: " + userInfoResults));
    }
}