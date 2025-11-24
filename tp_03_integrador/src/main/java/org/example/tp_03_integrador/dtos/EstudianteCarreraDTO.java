package org.example.tp_03_integrador.dtos;

public class EstudianteCarreraDTO {

    private String nombreCarrera;
    private int antiguedad;
    private boolean seGraduo;

    public EstudianteCarreraDTO() {}

    public EstudianteCarreraDTO(String nombreCarrera, int antiguedad, boolean seGraduo) {
        this.nombreCarrera = nombreCarrera;
        this.antiguedad = antiguedad;
        this.seGraduo = seGraduo;
    }

    public EstudianteCarreraDTO(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
        this.antiguedad = 0;
        this.seGraduo = false;
    }

    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isSeGraduo() {
        return seGraduo;
    }

    public void setSeGraduo(boolean seGraduo) {
        this.seGraduo = seGraduo;
    }
}
