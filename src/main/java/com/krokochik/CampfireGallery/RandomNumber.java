package com.krokochik.CampfireGallery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.krokochik.CampfireGallery.controllers.AppRestController.authenticationManager;
import static com.krokochik.CampfireGallery.controllers.AppRestController.devKey;

@SpringBootApplication
public class RandomNumber {

	public static void main(String[] args) {
		SpringApplication.run(RandomNumber.class, args);
		System.out.println("dev key: " + devKey);
		try{authenticationManager.addKey("3~01YZH>Iysc8~e><A8x");}catch(Exception ignored){}
	}
}
