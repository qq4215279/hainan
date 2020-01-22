package com.gobestsoft.company;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gobestsoft")
@SpringBootApplication
@EnableCaching
public class HnCompanyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HnCompanyApplication.class, args);
    }
}
