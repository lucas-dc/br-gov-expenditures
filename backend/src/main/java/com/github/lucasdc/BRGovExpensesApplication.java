package com.github.lucasdc;

import com.github.lucasdc.config.GovAPIProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableConfigurationProperties(GovAPIProperties.class)
@EnableCaching
public class BRGovExpensesApplication {
    public static void main(String[] args) {
        SpringApplication.run(BRGovExpensesApplication.class, args);
    }
}
