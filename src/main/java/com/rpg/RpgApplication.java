package com.rpg;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.rpg.repository")
@EntityScan(basePackages = "com.rpg.model")
public class RpgApplication {
    public static void main(String[] args) {
        SpringApplication.run(RpgApplication.class, args);
    }
} 