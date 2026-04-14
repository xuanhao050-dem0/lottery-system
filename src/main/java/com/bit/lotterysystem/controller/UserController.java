package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.controller.result.UserRegisterResult;

import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/user")
public class UserController {


    private static final Logger logger= LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/register")
    public Result<UserRegisterResult> userRegister(
            @Validated @RequestBody UserRegisterParam param){
        logger.info("userRegister UserRegisterParam:{}", JacksonUtil.writeValueAsString(param));

        UserRegisterDTO userRegisterDTO =userService.userRegister(param);

        return Result.success(convertToUserRegisterResult(userRegisterDTO));


    }

    private UserRegisterResult convertToUserRegisterResult(UserRegisterDTO userRegisterDTO) {
        //判断是否为空，如果为空，抛出异常
        UserRegisterResult userRegisterResult=new UserRegisterResult();
        if (userRegisterDTO==null){
            throw new ControllerException(ControllerErrorCodeConstants.REGISTER_ERROR);
        }
        userRegisterResult.setUserId(userRegisterDTO.getUserId());
        return userRegisterResult;
    }

}
