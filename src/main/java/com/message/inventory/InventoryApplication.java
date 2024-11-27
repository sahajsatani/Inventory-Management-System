package com.message.inventory;

import com.message.inventory.configuration.SMSconfig.TwilioDTO;
import com.twilio.Twilio;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
public class InventoryApplication {

	@Autowired
	private TwilioDTO twilioDTO;

	@PostConstruct
	public void initTwilio(){
		Twilio.init(twilioDTO.getAccountSid(), twilioDTO.getAuthToken());
	}
	public static void main(String[] args) {
		SpringApplication.run(InventoryApplication.class, args);
		System.out.println("Application start...");
	}

}
