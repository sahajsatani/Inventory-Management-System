package com.message.inventory.configuration.email;

public interface EmailService {
    void sendSimpleMail(EmailDetails detail);

    void sendMailWithAttechment(EmailDetails details);
}
