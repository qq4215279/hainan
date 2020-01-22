package com.gobestsoft.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "com.gobestsoft")
@SpringBootApplication

public class ApiApplication {

    public static void main(String[] args) {
//        System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(ApiApplication.class, args);
        System.out.println("ApiApplication Success");
    }
}
