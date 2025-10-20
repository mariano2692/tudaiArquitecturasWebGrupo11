package org.example.tp_03_integrador.dtos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CarreraSimpleDTO {
    private String nombre;

    public CarreraSimpleDTO(String nombre) {
        this.nombre = nombre;
    }


}
