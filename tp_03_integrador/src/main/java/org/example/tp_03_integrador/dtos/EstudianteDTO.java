package org.example.tp_03_integrador.dtos;

import java.util.List;

public class EstudianteDTO {
    private int dni;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String ciudadResidencia;
    private long lu;

    // Constructor vacío
    public EstudianteDTO() {
    }

    // Constructor con todos los campos
    public EstudianteDTO(int dni, String nombre, String apellido, int edad,
                         String genero, String ciudadResidencia, long lu) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
    }

    // Getters y Setters
    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public long getLu() {
        return lu;
    }

    public void setLu(long lu) {
        this.lu = lu;
    }


}