package com.krokochik.CampfireGallery;

import com.krokochik.CampfireGallery.models.Cryptographer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import static com.krokochik.CampfireGallery.controllers.AppRestController.authenticationManager;
import static com.krokochik.CampfireGallery.controllers.AppRestController.devKey;

@SpringBootApplication
public class RandomNumber {

	public static void main(String[] args) {
		SpringApplication.run(RandomNumber.class, args);
		System.out.println("dev key: " + devKey);
		try{authenticationManager.addKey("v752zpwZR~CV^^U$8<m6");}catch(Exception ignored){}
		System.out.println(Cryptographer.encode("v752zpwZR~CV^^U$8<m6", 0));
		System.out.println(Cryptographer.decode(Cryptographer.encode("v752zpwZR~CV^^U$8<m6", 0), 0));
	}
}
