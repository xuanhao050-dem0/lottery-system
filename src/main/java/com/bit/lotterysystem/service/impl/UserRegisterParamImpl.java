package com.bit.lotterysystem.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.digest.DigestUtil;
import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.common.utils.RegexUtil;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.dao.dateobject.Encrypt;
import com.bit.lotterysystem.dao.dateobject.UserDO;
import com.bit.lotterysystem.dao.mapper.UserMapper;
import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


@Service
public class UserRegisterParamImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public UserRegisterDTO userRegister(UserRegisterParam param) {

        //校验注册信息
        checkUserInfo(param);
        //加密私密数据（构造dao层请求）
        UserDO userDO=new UserDO();
        userDO.setUserName(param.getName());
        userDO.setEmail(param.getEmail());
        userDO.setPhoneNumber(new Encrypt(param.getPhoneNumber()));
        userDO.setIdentity(param.getIdentity());
        if (!StringUtils.hasText(param.getPassword())){
            userDO.setPassword(DigestUtil.sha256Hex(param.getPassword()));
        }
        //保存数据
        userMapper.insertUserInfo(userDO);
        //构造返回
        UserRegisterDTO userRegisterDTO=new UserRegisterDTO();
        userRegisterDTO.setUserId(userDO.getId());
        return userRegisterDTO;
    }

    private void checkUserInfo(UserRegisterParam param) {
        //校验是否为空
        if(param==null){
            throw new ServiceException(
                    ServiceErrorCodeConstants.REGISTER_USERINFO_IS_EMPTY);
        }
        //校验邮箱 通过hutool的Validator中的校验方法
        if(!Validator.isEmail(param.getEmail())){
            throw new ServiceException(
                    ServiceErrorCodeConstants.REGISTER_EMAIL_FORMAT_INVALID);
        }
        //校验手机号 自定义工具类的校验方法
        if(!RegexUtil.checkMobile(param.getPhoneNumber())){
            throw new ServiceException(
                    ServiceErrorCodeConstants.REGISTER_PHONE_NUMBER_FORMAT_INVALID);
        }

        //校验身份信息（管理员/用户）
        //枚举身份信息，将传进来的信息比对是否有枚举中的身份信息
        if (UserIdentityEnum.forName(param.getIdentity())==null){
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_IDENTITY_INVALID);
        }



        //校验管理员密码必填 1.是管理员。 2.密码有长度
        if (param.getIdentity().equalsIgnoreCase(UserIdentityEnum.ADMIN.name())
                && !StringUtils.hasText(param.getPassword()))
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_PASSWORD_IS_EMPTY);

        //密码校验，必须六位
        if (StringUtils.hasText(param.getPassword())
                && !RegexUtil.checkPassword(param.getPassword()))
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_PASSWORD_IS_WEAK);
        /**
         * 校验邮箱是否被使用（防止一个手机号匹配多个邮箱）
         * 通过参数的邮箱寻找，返回的结果大于1，抛出异常
         */
        if (checkMailUsed(param.getEmail())){
            throw new ServiceException(ServiceErrorCodeConstants.EMAIL_USED);
        }

        /**
         * 校验手机号是否被使用（防止一个邮箱匹配多个手机号）
         * 访问数据库的手机号，使用encrypt
         */

        if (checkPhoneNumberUsed(param.getPhoneNumber())){
            throw new ServiceException(ServiceErrorCodeConstants.PHONE_NUMBER_USED);
        }
    }

    private boolean checkPhoneNumberUsed(String phoneNumber) {
        return userMapper.countByPhoneNumber(new Encrypt(phoneNumber))>=1;
    }

    private boolean checkMailUsed(String mail) {

        return userMapper.countByEmail(mail)>=1;
    }
}
