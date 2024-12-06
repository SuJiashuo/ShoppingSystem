package com.itheima.service;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.Properties;

public class MailService {
    public void sendEmail(String to, String subject, String body) {
        // QQ 邮箱 SMTP 配置
        String host = "smtp.qq.com"; // 邮件服务器地址
        String from = "2939346806@qq.com"; // 发送方邮箱
        String password = "crrabfpgdcjwdhda"; // QQ邮箱授权码

        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(properties, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // 创建邮件
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(body);

            // 发送邮件
            Transport.send(message);
            System.out.println("邮件发送成功！");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }
}
