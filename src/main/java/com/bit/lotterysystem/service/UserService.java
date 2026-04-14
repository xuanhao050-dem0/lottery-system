package com.bit.lotterysystem.service;

import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;

public interface UserService {

    UserRegisterDTO userRegister(UserRegisterParam param);

}
