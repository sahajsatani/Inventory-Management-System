package com.message.inventory.schedulers;

import com.message.inventory.model.entity.Admin;
import com.message.inventory.configuration.email.EmailDetailsDTO;
import com.message.inventory.configuration.email.EmailService;
import com.message.inventory.model.entity.Product;
import com.message.inventory.repositories.AdminRepo;
import com.message.inventory.repositories.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import java.io.FileWriter;
import java.io.Writer;
import java.util.List;

@EnableScheduling
@Configuration
@Component
public class Scheduler {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    EmailService emailService;
    @Autowired
    AdminRepo adminRepo;
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void sendReport() {
        try{
            //create report of total selling
            List<Product> list = productRepo.findAll();
            Writer writer = new FileWriter("C:\\Users\\sahaj\\IdeaProjects\\inventory\\report.csv");
            ICsvBeanWriter iCsvBeanWriter = new CsvBeanWriter(writer, CsvPreference.STANDARD_PREFERENCE);
            String[] header = {"productName", "inventoryStoke", "sold"};
            iCsvBeanWriter.writeHeader(header);
            boolean flage = false;
            for (Product product : list) {
                if (product.getSold() > 0) {
                    flage = true;
                    iCsvBeanWriter.write(product, header);
                }
            }
            iCsvBeanWriter.close();
            writer.close();

            //Send report if sold any product
            if(flage){
                List<Admin> admins = adminRepo.findAll();
                EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                        .msgBody("Today's total sold product:")
                        .subject("Daliy Report")
                        .attachment("C:\\Users\\sahaj\\IdeaProjects\\inventory\\report.csv")
                        .build();
                for (Admin admin : admins) {
                    emailDetailsDTO.setRecipient(admin.getEmail());
                    emailService.sendMailWithAttechment(emailDetailsDTO);
                }
                productRepo.updateSold();
            }
            //Send msg if not sell any product
            else{
                List<Admin> admins = adminRepo.findAll();
                EmailDetailsDTO emailDetailsDTO = EmailDetailsDTO.builder()
                        .msgBody("Not sell any one product")
                        .subject("Daliy Report")
                        .build();
                for (Admin admin : admins) {
                    emailDetailsDTO.setRecipient(admin.getEmail());
                    emailService.sendSimpleMail(emailDetailsDTO);
                }
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
    }
}
