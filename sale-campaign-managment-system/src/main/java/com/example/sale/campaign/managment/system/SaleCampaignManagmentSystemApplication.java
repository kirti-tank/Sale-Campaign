package com.example.sale.campaign.managment.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SaleCampaignManagmentSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(SaleCampaignManagmentSystemApplication.class, args);
		System.out.println("API is Running!...");
	}

}
