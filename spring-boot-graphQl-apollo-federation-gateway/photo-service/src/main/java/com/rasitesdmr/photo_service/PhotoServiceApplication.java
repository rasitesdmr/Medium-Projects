package com.rasitesdmr.photo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhotoServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PhotoServiceApplication.class, args);
	}

}
