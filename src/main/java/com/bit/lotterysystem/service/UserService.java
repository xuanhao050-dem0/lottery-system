package com.bit.lotterysystem.service;

import com.bit.lotterysystem.controller.param.UserLoginByPasswordParam;
import com.bit.lotterysystem.controller.param.UserLoginByVerificationCodeParam;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.service.dto.UserLoginDTO;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;

public interface UserService {

    UserRegisterDTO userRegister(UserRegisterParam param);


    UserLoginDTO userLoginByPassword(UserLoginByPasswordParam userLoginByPasswordParam);

    UserLoginDTO userLoginByVerificationCode(UserLoginByVerificationCodeParam userLoginByVerificationCodeParam);
}
