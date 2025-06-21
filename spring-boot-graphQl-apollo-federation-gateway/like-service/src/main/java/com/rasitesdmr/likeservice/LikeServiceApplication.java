package com.rasitesdmr.likeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LikeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(LikeServiceApplication.class, args);
    }

}
