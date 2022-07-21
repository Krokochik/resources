package com.krokochik.CampfireGallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.krokochik.CampfireGallery.controllers.AppRestController.authenticationManager;
import static com.krokochik.CampfireGallery.controllers.AppRestController.devKey;

@SpringBootApplication
public class RandomNumber {

	public static void main(String[] args) throws Exception{
		SpringApplication.run(RandomNumber.class, args);
		System.out.println("dev key: " + devKey);
		try{authenticationManager.addKey("g21<gZigPB$6D^jhYUz6^d$<>1>r2~1$L74F1JlG");}catch(Exception ignored){}
	}
}
