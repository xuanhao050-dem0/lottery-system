package com.bit.lotterysystem.common.errorcode;

public interface ServiceErrorCodeConstants {
    //------ 人员模块错误码 ------
    ErrorCode REGISTER_USERINFO_IS_EMPTY= new ErrorCode(100,"用户信息为空");
    ErrorCode REGISTER_EMAIL_FORMAT_INVALID= new ErrorCode(101,"邮箱格式不合法");
    ErrorCode REGISTER_PHONE_NUMBER_FORMAT_INVALID= new ErrorCode(102,"手机号格式不合法");
    ErrorCode REGISTER_IDENTITY_INVALID= new ErrorCode(103,"人员信息不合法");
    ErrorCode REGISTER_PASSWORD_IS_EMPTY= new ErrorCode(104,"管理员注册密码为空");
    ErrorCode REGISTER_PASSWORD_IS_WEAK= new ErrorCode(105,"注册密码强度低");
    ErrorCode EMAIL_USED= new ErrorCode(106,"邮箱被使用");
    ErrorCode PHONE_NUMBER_USED= new ErrorCode(107,"手机号被使用");
    ErrorCode LOGIN_METHOD_NOT_EXIST= new ErrorCode(108,"登录方式不存在");
    ErrorCode USER_NOT_EXIST= new ErrorCode(109,"用户不存在");
    ErrorCode PASSWORD_ERROR= new ErrorCode(110,"密码错误");
    ErrorCode VERIFICATION_ERROR= new ErrorCode(111,"验证码错误");

    //------ 活动模块错误码 ------



    //------ 奖品模块错误码 ------



    //------ 抽奖模块错误码 ------


}
