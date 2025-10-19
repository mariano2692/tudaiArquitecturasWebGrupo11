package org.example.tp_03_integrador;

import org.example.tp_03_integrador.utils.CargaDeDatos;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

@SpringBootApplication
public class Tp03IntegradorApplication {

    public static void main(String[] args) {

        ConfigurableApplicationContext context = SpringApplication.run(Tp03IntegradorApplication.class, args);

        // Obtener el bean 'CargaDeDatos' gestionado por Spring
        CargaDeDatos cargaDeDatos = context.getBean(CargaDeDatos.class);

        // Cargar los datos
        try {
            cargaDeDatos.cargarDatosDesdeCSV();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
