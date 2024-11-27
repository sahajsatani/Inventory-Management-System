package com.message.inventory.configuration.email;

public interface EmailService {
    void sendSimpleMail(EmailDetailsDTO detail);
    void sendMailWithAttechment(EmailDetailsDTO details);
}
