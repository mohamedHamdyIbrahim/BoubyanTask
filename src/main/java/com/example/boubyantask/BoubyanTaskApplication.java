package com.example.boubyantask;

import com.example.boubyantask.config.RsaKeyProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BoubyanTaskApplication {
    public static void main(String[] args) {
        SpringApplication.run(BoubyanTaskApplication.class, args);
    }

}
