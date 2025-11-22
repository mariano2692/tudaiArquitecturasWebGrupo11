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
        // Verificar si la tabla está vacía
        if (authorityRepository.count() == 0) {
            // Registros harcodeados
            List<Authority> defaultAuthorities = Arrays.asList(
                    new Authority("ADMIN"),
                    new Authority("USUARIO"),
                    new Authority("MANTENIMIENTO")
            );

            // Guardar en la base de datos
            authorityRepository.saveAll(defaultAuthorities);
            System.out.println("Roles iniciales insertados en la tabla Authority.");
        } else {
            System.out.println("La tabla Authority ya contiene registros.");
        }
    }
}
