package com.example.bank_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.example.bank_test.controller", "com.example.bank_test.repository",
		"com.example.bank_test.service"})
public class BankTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BankTestApplication.class, args);
	}

}
