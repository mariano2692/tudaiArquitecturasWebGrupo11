package org.example.tp_03_integrador.dtos;

public class CarreraConCantInscriptosDTO {
    private String nombreCarrera;
    private Long cantInscriptos;

    public CarreraConCantInscriptosDTO(String nombreCarrera, Long cantInscriptos) {
        this.nombreCarrera = nombreCarrera;
        this.cantInscriptos = cantInscriptos;
    }
}
