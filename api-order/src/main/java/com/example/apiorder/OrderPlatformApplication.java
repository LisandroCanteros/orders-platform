package com.example.apiorder;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class OrderPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderPlatformApplication.class, args);
    }
}
