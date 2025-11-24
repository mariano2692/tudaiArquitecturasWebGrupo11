package org.example.tp_03_integrador.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jdk.jfr.DataAmount;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class EstudianteRequestDTO {
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private int dni;
    private String ciudad;
    private Long lu;
}
