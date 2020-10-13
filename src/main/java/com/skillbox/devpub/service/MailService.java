package com.skillbox.devpub.service;

public interface MailService {

    void sendSimpleMessage(String to, String text, String subject);
    void sendPasswordRecovery(String to, String name, String link);
}
