package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
public class EmpVueApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmpVueApplication.class, args);
    }

}
