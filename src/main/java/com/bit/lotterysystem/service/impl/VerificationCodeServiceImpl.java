package com.bit.lotterysystem.service.impl;

import cn.hutool.core.lang.Validator;
import com.bit.lotterysystem.common.errorcode.ServiceErrorCodeConstants;
import com.bit.lotterysystem.common.exception.ServiceException;
import com.bit.lotterysystem.common.utils.CaptchaUtil;
import com.bit.lotterysystem.common.utils.MailUtil;
import com.bit.lotterysystem.common.utils.RedisUtil;
import com.bit.lotterysystem.common.utils.RegexUtil;
import com.bit.lotterysystem.service.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {
    public static final String EMAIL_SUBJECT = "【抽奖系统】";

    public static final String EMAIL_CONTENT = "您的验证码为：%s，有效期为%s秒。请勿泄露给他人。";

    public static final Long VERIFICATION_CODE_TIMEOUT = 60L;

    //区分不同的redis的key，给key加上前缀
    public static final String VERIFICATION_CODE_PREFIX="VERIFICATION_CODE_";

    @Autowired
    private MailUtil mailUtil;
    @Autowired
    private RedisUtil redisUtil;
    @Override
    public void setVerificationCodeService(String email) {
        //校验邮箱
        if (!Validator.isEmail(email)){
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_EMAIL_FORMAT_INVALID);
        }

        //生成随机验证码
        String captcha= CaptchaUtil.getCaptcha(6);

        //发送验证码
        String FINAL_EMAIL_CONTENT=String.format(EMAIL_CONTENT,captcha,VERIFICATION_CODE_TIMEOUT);
        mailUtil.sendSimpleMail(email,EMAIL_SUBJECT,FINAL_EMAIL_CONTENT);
        //缓存验证码
        redisUtil.setValue(VERIFICATION_CODE_PREFIX+email,captcha,60L);
    }

    @Override
    public String getVerificationCodeService(String email) {
        //校验邮箱
        if (!Validator.isEmail(email)){
            throw new ServiceException(ServiceErrorCodeConstants.REGISTER_EMAIL_FORMAT_INVALID);
        }

        return redisUtil.getValue(VERIFICATION_CODE_PREFIX+email);
    }
}
