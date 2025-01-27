package com.espe.exposiciones;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ExposicionesApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExposicionesApplication.class, args);
    }
}
