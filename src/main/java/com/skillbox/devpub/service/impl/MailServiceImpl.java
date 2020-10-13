package com.skillbox.devpub.service.impl;

import com.skillbox.devpub.service.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;
    private final SimpleMailMessage template;
    @Value("${project.name}")
    private String projectName;

    public MailServiceImpl(JavaMailSender mailSender, SimpleMailMessage template) {
        this.mailSender = mailSender;
        this.template = template;
    }

    //@TODO сделать приветственное письмо
    @Override
    public void sendSimpleMessage(String to, String text, String subject) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(to);
            message.setText(text);
            message.setSubject(subject);

            mailSender.send(message);
//            log.info("Email sent to {}", to);
        } catch (MailException exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void sendPasswordRecovery(String to, String name, String link) {
        template.setText("Добрый день, %s!\nПерейдите по ссылке для восстановления пароля:\n%s");
        String text = String.format(template.getText(), name, link);
        sendSimpleMessage(to, text, "Восстановление пароля " + projectName);
//        log.info("Email with activation link is sent to {}", to);
    }
}
