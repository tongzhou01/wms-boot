package com.bs.wms.util;

import com.alibaba.fastjson.JSON;
import com.bs.wms.constant.BaseConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailUtil {
    private static JavaMailSenderImpl mailSender = createMailSender();
    private static final Logger LOGGER = LoggerFactory.getLogger(MailUtil.class);
    private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

    /**
     * 邮件发送器
     *
     */
    private static JavaMailSenderImpl createMailSender() {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        sender.setHost(BaseConstant.EMAIL_HOST_NAME);
        sender.setPort(BaseConstant.EMAIL_PORT);
        sender.setUsername(BaseConstant.EMAIL_USERNAME);
        sender.setPassword(BaseConstant.EMAIL_AUTH);
        sender.setDefaultEncoding("Utf-8");
        Properties p = new Properties();
        p.setProperty("mail.smtp.socketFactory.class", SSL_FACTORY);
        p.setProperty("mail.smtp.timeout", "3000");
        p.setProperty("mail.smtp.auth", "true");
        sender.setJavaMailProperties(p);
        return sender;
    }

    /**
     * 发送邮件
     *
     * @param to      接受人
     * @param subject 主题
     * @param html    发送内容
     */
    public static boolean sendMail(String to, String subject, String html){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        boolean b = false;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(BaseConstant.EMAIL_USERNAME, BaseConstant.EMAIL_PERSONAL);
            messageHelper.setTo(to);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            mailSender.send(mimeMessage);
            LOGGER.info("成功发送邮件到 - " + to);
            b = true;
        } catch (Exception e) {
            LOGGER.error("发送邮件出错:{}", e);
        }
        return b;
    }

    /**
     * 发送邮件
     *
     * @param emailArray      接受人
     * @param subject 主题
     * @param html    发送内容
     */
    public static boolean sendMail(String[] emailArray, String subject, String html){
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper messageHelper;
        boolean b = false;
        try {
            messageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            messageHelper.setFrom(BaseConstant.EMAIL_USERNAME, BaseConstant.EMAIL_PERSONAL);
            messageHelper.setTo(emailArray);
            messageHelper.setSubject(subject);
            messageHelper.setText(html, true);
            mailSender.send(mimeMessage);
            LOGGER.info("成功发送邮件到 - " + JSON.toJSONString(emailArray));
            b = true;
        } catch (Exception e) {
            LOGGER.error("发送邮件出错:{}", e);
        }
        return b;
    }

    public static void main(String[] args) {
        sendMail("tz568309196@163.com","haha","<html>\n" +
                "<head>\n" +
                "<title>\n" +
                "我是网页标题\n" +
                "</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<h1>我是网页内容，够简单了吧？</h1>\n" +
                "</body>\n" +
                "</html>");
    }
}
