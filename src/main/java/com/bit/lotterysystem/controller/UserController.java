package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.UserLoginByPasswordParam;
import com.bit.lotterysystem.controller.param.UserLoginByVerificationCodeParam;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.controller.param.VerificationCodeParam;
import com.bit.lotterysystem.controller.result.UserLoginResult;
import com.bit.lotterysystem.controller.result.UserRegisterResult;

import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.UserLoginDTO;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import com.bit.lotterysystem.service.impl.UserServiceImpl;
import com.bit.lotterysystem.service.impl.VerificationCodeServiceImpl;
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
    private UserService userService;

    @Autowired
    private VerificationCodeServiceImpl verificationCodeServiceImpl;
    @Autowired
    private UserServiceImpl userServiceImpl;

    /**
     * 用户注册
     * @param param
     * @return
     */
    @RequestMapping("/register")
    public Result<UserRegisterResult> userRegister(
            @Validated @RequestBody UserRegisterParam param){
        logger.info("userRegister UserRegisterParam:{}", JacksonUtil.writeValueAsString(param));

        UserRegisterDTO userRegisterDTO =userService.userRegister(param);

        return Result.success(convertToUserRegisterResult(userRegisterDTO));


    }


    @RequestMapping("/send/verification-code")
    public Result<Boolean> verificationCode(
            @Validated @RequestBody VerificationCodeParam verificationCodeParam){
        logger.info("verificationCodeParam:{}",verificationCodeParam.getEmail());
        verificationCodeServiceImpl.sendVerificationCodeService(verificationCodeParam.getEmail());
        return Result.success(Boolean.TRUE);
    }


    /**
     * 用户账密登录
     * @param userLoginByPasswordParam
     * @return
     */
    @RequestMapping("/login/password")
    public Result<UserLoginResult> userLoginByPassword(
            @Validated @RequestBody UserLoginByPasswordParam userLoginByPasswordParam){
        logger.info("userLoginParam : {}",JacksonUtil.writeValueAsString(userLoginByPasswordParam));

        UserLoginDTO userLoginDTO =userServiceImpl.userLoginByPassword(userLoginByPasswordParam);

        return Result.success(convertToUserLoginResult(userLoginDTO));
    }

    /**
     * 验证码登录
     * @param userLoginByVerificationCodeParam
     * @return
     */
    @RequestMapping("/login/verificationCode")
    public Result<UserLoginResult> userLoginByVerificationCode(
            @Validated @RequestBody UserLoginByVerificationCodeParam userLoginByVerificationCodeParam){
        logger.info("userLoginParam : {}",JacksonUtil.writeValueAsString(userLoginByVerificationCodeParam));

        UserLoginDTO userLoginDTO =userServiceImpl.userLoginByVerificationCode(userLoginByVerificationCodeParam);

        return Result.success(convertToUserLoginResult(userLoginDTO));
    }

    /**
     * 登录返回结果转换
     * @param userLoginDTO
     * @return
     */
    private UserLoginResult convertToUserLoginResult(UserLoginDTO userLoginDTO) {
        if (userLoginDTO==null){
            throw new ControllerException(ControllerErrorCodeConstants.LOGIN_ERROR);
        }

        UserLoginResult userLoginResult=new UserLoginResult();
        userLoginResult.setIdentity(userLoginDTO.getIdentity().name());
        userLoginResult.setToken(userLoginDTO.getToken());

        return userLoginResult;
    }

    /**
     * 用户注册返回参数转换
     * @param userRegisterDTO
     * @return
     */
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
