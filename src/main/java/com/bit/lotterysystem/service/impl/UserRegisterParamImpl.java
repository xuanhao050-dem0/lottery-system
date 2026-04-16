package com.bit.lotterysystem.service.impl;

import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import org.springframework.stereotype.Service;

@Service
public class UserRegisterParamImpl implements UserService {

    @Override
    public UserRegisterDTO userRegister(UserRegisterParam param) {

        //校验注册信息
        checkUserInfo(param);
        //加密私密数据（构造dao层请求）

        //保存数据

        //构造返回
        UserRegisterDTO userRegisterDTO=new UserRegisterDTO();
        userRegisterDTO.setUserId(12L);
        return userRegisterDTO;
    }

    private void checkUserInfo(UserRegisterParam param) {
        //校验是否为空

        //校验邮箱

        //校验手机号

        //校验密码
    }
}
