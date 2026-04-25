package com.bit.lotterysystem.service;

import com.bit.lotterysystem.controller.param.UserLoginByPasswordParam;
import com.bit.lotterysystem.controller.param.UserLoginByVerificationCodeParam;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.service.dto.GetUserInfoDTO;
import com.bit.lotterysystem.service.dto.UserLoginDTO;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import com.bit.lotterysystem.service.enums.UserIdentityEnum;

import java.util.List;

public interface UserService {

    UserRegisterDTO userRegister(UserRegisterParam param);


    UserLoginDTO userLoginByPassword(UserLoginByPasswordParam userLoginByPasswordParam);

    UserLoginDTO userLoginByVerificationCode(UserLoginByVerificationCodeParam userLoginByVerificationCodeParam);

    /**
     * 根据身份查询人员列表
     * @param identityEnum
     * @return
     */
    List<GetUserInfoDTO> getUserInfo(UserIdentityEnum identityEnum);
}
