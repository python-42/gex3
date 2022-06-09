package com.giftexchange.gex3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Gex3Application {

	public static void main(String[] args) {
		Constants.NavblockConstants.initNavblockConstants();
		SpringApplication.run(Gex3Application.class, args);
	}
}
