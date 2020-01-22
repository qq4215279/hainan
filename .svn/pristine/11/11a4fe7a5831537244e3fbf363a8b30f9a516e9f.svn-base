package com.gobestsoft.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * Created by duanmu on 2019/1/2.
 */
@Component
public class MailUtil {

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Autowired
    JavaMailSender javaMailSender;

    @Async
    public void sendHtmlMail(String sendTo, String subject, String content) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom(sendFrom);
        helper.setTo(sendTo);
        helper.setSubject(subject);
        helper.setText(content, true);
        // 发送邮件
        javaMailSender.send(mimeMessage);
    }
}
