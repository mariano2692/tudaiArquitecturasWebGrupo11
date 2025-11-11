package com.viajes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ViajesApplication {
    public static void main(String[] args) {
        SpringApplication.run(ViajesApplication.class, args);
    }
}
