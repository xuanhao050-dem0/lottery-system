package com.bit.lotterysystem.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailUtil {

    @Autowired
    private JavaMailSender mailSender;

    // 读取配置文件中的发件人邮箱
    @Value("${spring.mail.username}")
    private String from;

    /**
     * 发送简单文本验证码邮件
     * @param to      收件人邮箱
     * @param subject 邮件主题
     * @param content 邮件内容（验证码）
     */
    @Async // 开启异步执行，需要在启动类加 @EnableAsync
    public void sendSimpleMail(String to, String subject, String content) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);

            mailSender.send(message);
            log.info("邮件验证码已成功发送至：{}", to);
        } catch (Exception e) {
            log.error("邮件发送失败，收件人：{}，错误信息：{}", to, e.getMessage());
            // 这里可以根据业务需求抛出自定义异常
        }
    }
}
