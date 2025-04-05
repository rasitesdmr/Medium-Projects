package com.rasitesdmr.springboottransactionmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootTransactionManagementApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootTransactionManagementApplication.class, args);
    }

}
