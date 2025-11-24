package org.example.gateway.config;

import jakarta.annotation.PostConstruct;
import org.example.gateway.repository.AuthorityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.example.gateway.entity.Authority;

import java.util.Arrays;
import java.util.List;

@Component
public class AuthorityInitializer {
    @Autowired
    private final AuthorityRepository authorityRepository;

    public AuthorityInitializer(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    @PostConstruct
    public void initializeAuthorities() {
        // Lista de autoridades que deben existir
        List<String> requiredAuthorities = Arrays.asList(
                "ADMIN",
                "USUARIO",
                "MANTENIMIENTO",
                "PREMIUM"
        );

        // Agregar las que falten
        for (String authorityName : requiredAuthorities) {
            if (!authorityRepository.existsById(authorityName)) {
                authorityRepository.save(new Authority(authorityName));
                System.out.println("Rol " + authorityName + " insertado en la tabla Authority.");
            }
        }
        System.out.println("Verificación de roles completada.");
    }
}
