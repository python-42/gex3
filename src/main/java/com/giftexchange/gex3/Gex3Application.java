package com.giftexchange.gex3;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.giftexchange.gex3.gex.Constants;

@SpringBootApplication
public class Gex3Application {

	public static void main(String[] args) {
		Constants.initNavblockConstant();
		SpringApplication.run(Gex3Application.class, args);
	}
}
