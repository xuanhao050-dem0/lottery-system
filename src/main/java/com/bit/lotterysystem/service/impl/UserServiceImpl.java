package com.bit.lotterysystem.service.impl;

import cn.hutool.core.lang.Validator;
import cn.hutool.crypto.digest.DigestUtil;
import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.common.utils.JWTUtil;
import com.bit.lotterysystem.common.utils.RegexUtil;
import com.bit.lotterysystem.controller.param.UserLoginByPasswordParam;
import com.bit.lotterysystem.controller.param.UserLoginByVerificationCodeParam;
import com.bit.lotterysystem.controller.param.UserLoginParam;
import com.bit.lotterysystem.controller.param.UserRegisterParam;
import com.bit.lotterysystem.dao.dateobject.Encrypt;
import com.bit.lotterysystem.dao.dateobject.UserDO;
import com.bit.lotterysystem.dao.mapper.UserMapper;
import com.bit.lotterysystem.service.UserService;
import com.bit.lotterysystem.service.dto.UserLoginDTO;
import com.bit.lotterysystem.service.dto.UserRegisterDTO;
import com.bit.lotterysystem.service.enums.UserIdentityEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;
    @Autowired
    VerificationCodeServiceImpl verificationCodeService;

    Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    /**
     * 邮箱+验证码登录
     * 参数检验
     *  是否是邮箱，数据库是否有这个邮箱
     * 业务判断
     *  发送验证码
     *  校验验证码
     * 构造返回
     * @param userLoginByVerificationCodeParam
     * @return
     */
    @Override
    public UserLoginDTO userLoginByVerificationCode(UserLoginByVerificationCodeParam userLoginByVerificationCodeParam) {

        /**
         * 入参校验
         */
        UserDO userDO=null;
        if (Validator.isEmail(userLoginByVerificationCodeParam.getEmail())){
            userDO=userMapper.selectByEmail(userLoginByVerificationCodeParam.getEmail());
        }  else {
            throw new ServiceException(ServiceErrorCodeConstants.USER_NOT_EXIST);
        }

        /**
         * 业务判断
         * 1.是否在数据库中找到该邮箱信息
         * 2.身份不对--》没填写身份，获取的信息不是管理员--》无权限登录
         * 3.将从缓存获取的验证码与传入的做对比
         */
        logger.info("userLoginByVerificationCodeParam.getVerification():{}," +
                "verificationCodeService.getVerificationCodeService\n" +
                "                        (userLoginByVerificationCodeParam.getEmail():{}",
                "#"+userLoginByVerificationCodeParam.getVerification()+"#",
                "#"+verificationCodeService.getVerificationCodeService(userLoginByVerificationCodeParam.getEmail())+"#");
        if (userDO==null){
            throw new ServiceException(ServiceErrorCodeConstants.USER_NOT_EXIST);
        } else if (StringUtils.hasText(userLoginByVerificationCodeParam.getIdentity())
                && !userDO.getIdentity().equals(userLoginByVerificationCodeParam.getIdentity())) {
            //身份不对--》没填写身份，获取的信息不是管理员--》无权限登录
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_IDENTITY_INVALID);
        } else if (!userLoginByVerificationCodeParam.getVerification()
                .equals(verificationCodeService.getVerificationCodeService
                        (userLoginByVerificationCodeParam.getEmail()))
        ){
            throw new ServiceException(ServiceErrorCodeConstants.VERIFICATION_ERROR);
        }


        /**
         * 构造返回
         * 身份
         * 构造token
         */
        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setIdentity(UserIdentityEnum.forName(userDO.getIdentity()));

        //JWT生成的令牌可以解密的，不要存放敏感信息
        Map<String,Object> claim=new HashMap<>();
        claim.put("id",userDO.getId());
        claim.put("userName",userDO.getUserName());
        String token=JWTUtil.genJwt(claim);

        userLoginDTO.setToken(token);

        return userLoginDTO;
    }

    /**
     * 账密登录
     * 检验使用邮箱还是手机号登录
     * 邮箱==》使用邮箱查询
     * 手机号==》使用手机号查询
     * 查询结果和 传入密码比较，
     * 相同--》返回token
     * 不同--》登录失败
     * @param userLoginByPasswordParam
     * @return
     */
    @Override
    public UserLoginDTO userLoginByPassword(UserLoginByPasswordParam userLoginByPasswordParam) {

        /**
         * 
         * 数据库查找所需数据
         * 如果是邮箱--》通过邮箱查找
         * 如果是手机号--》通过手机号查找
         */
        UserDO userDO=null;
        if (Validator.isEmail(userLoginByPasswordParam.getUserName())){
            userDO=userMapper.selectByEmail(userLoginByPasswordParam.getUserName());
        } else if (Validator.isMobile(userLoginByPasswordParam.getUserName())) {
            userDO=userMapper.selectByMobile(new Encrypt(userLoginByPasswordParam.getUserName()));
        }else {
            throw new ServiceException(ServiceErrorCodeConstants.LOGIN_METHOD_NOT_EXIST);
        }

        /**
         * 业务校验
         * 数据库中没找到对应的--》校验DO是否为空
         * 身份不对--》没填写身份，获取的信息不是管理员--》无权限登录
         * 密码不对--》数据库中获取的密码是密文，要将参数中的明文转化为密文再比较--》密码错误
         */
        if (userDO==null){
            throw new ServiceException(ServiceErrorCodeConstants.USER_NOT_EXIST);
        } else if (StringUtils.hasText(userLoginByPasswordParam.getIdentity())
                && !userDO.getIdentity().equals(userLoginByPasswordParam.getIdentity())) {
            //身份不对--》没填写身份，获取的信息不是管理员--》无权限登录
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_IDENTITY_INVALID);
        } else if (!DigestUtil.sha256Hex(userLoginByPasswordParam.getPassword())
                .equals(userDO.getPassword())) {
            throw new ServiceException(ServiceErrorCodeConstants.PASSWORD_ERROR);
        }

        /**
         * 构造返回
         * 身份
         * 构造token
         */
        UserLoginDTO userLoginDTO=new UserLoginDTO();
        userLoginDTO.setIdentity(UserIdentityEnum.forName(userDO.getIdentity()));

        //JWT生成的令牌可以解密的，不要存放敏感信息
        Map<String,Object> claim=new HashMap<>();
        claim.put("id",userDO.getId());
        claim.put("userName",userDO.getUserName());
        String token=JWTUtil.genJwt(claim);

        userLoginDTO.setToken(token);

        return userLoginDTO;
    }

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
