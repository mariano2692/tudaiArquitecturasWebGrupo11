package com.microservicio.viaje;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = {
        "com.microservicio.viaje",
        "controller",
        "service",
        "repository"
})
@EntityScan(basePackages = "entity")
@EnableJpaRepositories(basePackages = "repository")
public class ViajeApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViajeApplication.class, args);
    }

}
