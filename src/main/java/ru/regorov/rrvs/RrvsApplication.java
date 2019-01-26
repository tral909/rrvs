package ru.regorov.rrvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class RrvsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RrvsApplication.class, args);
    }

}