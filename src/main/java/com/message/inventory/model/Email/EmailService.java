package com.message.inventory.model.Email;

public interface EmailService {
    void sendSimpleMail(EmailDetails detail);
    void sendMailWithAttechment(EmailDetails details);

}
