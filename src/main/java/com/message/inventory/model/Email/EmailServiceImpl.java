package com.message.inventory.model.Email;

import com.message.inventory.model.Entity.Admin;
import com.message.inventory.repositories.AdminRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class EmailServiceImpl implements EmailService{
    @Autowired
    JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;
    @Autowired
    AdminRepo adminRepo;
    @Override
    public void sendSimpleMail(EmailDetails detail) {
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(sender);
            message.setTo(detail.getRecipient());
            message.setText(detail.getMsgBody());
            message.setSubject(detail.getSubject());
            javaMailSender.send(message);
        }catch (Exception e){
            List<Admin> list = adminRepo.findAll();
            for(Admin i : list) {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(sender);
                message.setTo(i.getEmail());
                message.setSubject("Alert of email problem!");
                message.setText("error on " + e.getMessage());
            }
        }
    }
    @Override
    public void sendMailWithAttechment(EmailDetails details){
        try{
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setTo(details.getRecipient());
            mimeMessageHelper.setText(details.getMsgBody());
            mimeMessageHelper.setSubject(details.getSubject());
            FileSystemResource fileSystemResource = new FileSystemResource(new File(details.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename(),fileSystemResource);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
