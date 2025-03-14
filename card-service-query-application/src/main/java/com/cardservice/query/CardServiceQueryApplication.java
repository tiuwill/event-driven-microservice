package com.cardservice.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.cardservice.query.repository", "com.cardservice.query.service", "com.cardservice.query.controller", "com.cardservice.query.config"})
public class CardServiceQueryApplication {

	public static void main(String[] args) {
		SpringApplication.run(CardServiceQueryApplication.class, args);
	}

}
