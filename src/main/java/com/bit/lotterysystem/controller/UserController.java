package com.bit.lotterysystem.controller;

import com.bit.lotterysystem.common.errorcode.ControllerErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ControllerException;
import com.bit.lotterysystem.common.pojo.Result;
import com.bit.lotterysystem.common.utils.JacksonUtil;
import com.bit.lotterysystem.controller.param.*;
import com.bit.lotterysystem.controller.result.GetUserInfoResult;
import com.bit.lotterysystem.controller.result.UserLoginResult;
import com.bit.lotterysystem.controller.result.UserRegisterResult;

import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.GetUserInfoDTO;
import com.bit.lotterysystem.service.dto.UserLoginDTO;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import com.bit.lotterysystem.service.impl.UserServiceImpl;
import com.bit.lotterysystem.service.impl.VerificationCodeServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


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

    /**
     * 验证码服务
     * @param verificationCodeParam
     * @return
     */
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
     * 获取用户列表
     * yrl
     * 入参：身份
     * 响应：
     * 调用service层
     * 构造返回
     * @param getUserInfoParam
     * @return
     */
    @RequestMapping("/getUserList")
    public Result<List<GetUserInfoResult>> getUserList(GetUserInfoParam getUserInfoParam){


        List<GetUserInfoDTO> userInfoDTOList=userServiceImpl.getUserInfo(
                UserIdentityEnum.forName(getUserInfoParam.getIdentity())
        );
        return Result.success(convertToUserInfoList(userInfoDTOList));
    }

    private List<GetUserInfoResult> convertToUserInfoList(List<GetUserInfoDTO> userInfoDTOList) {
        if(CollectionUtils.isEmpty(userInfoDTOList)){
            return Arrays.asList();
        }

        return userInfoDTOList.stream().map(getUserInfoDTO->{
                GetUserInfoResult getUserInfoResult=new GetUserInfoResult();
                getUserInfoResult.setId(getUserInfoDTO.getId());
                getUserInfoResult.setUserName(getUserInfoDTO.getUserName());
                getUserInfoResult.setIdentity(getUserInfoDTO.getIdentity().name());
                return getUserInfoResult;
        }).collect(Collectors.toList());

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
     * 注册返回结果转换
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
