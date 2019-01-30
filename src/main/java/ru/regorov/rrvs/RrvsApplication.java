package ru.regorov.rrvs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication//(exclude = {SecurityAutoConfiguration.class})
public class RrvsApplication {
    public static void main(String[] args) {
        SpringApplication.run(RrvsApplication.class, args);
    }
}