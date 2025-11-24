package org.example.tp_03_integrador.dtos;

public class ReporteCarreraDTO {
    private String nombreCarrera;
    private int anioInscripcion;
    private int anioEgreso;
    private long cantidadInscriptos;
    private long cantidadEgresados;
    private Long luEstudiante;

    public ReporteCarreraDTO(String nombreCarrera, int anioInscripcion, int anioEgreso,
                             long cantidadInscriptos, long cantidadEgresados, Long luEstudiante) {
        this.nombreCarrera = nombreCarrera;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.cantidadInscriptos = cantidadInscriptos;
        this.cantidadEgresados = cantidadEgresados;
        this.luEstudiante = luEstudiante;
    }


    public String getNombreCarrera() {
        return nombreCarrera;
    }

    public void setNombreCarrera(String nombreCarrera) {
        this.nombreCarrera = nombreCarrera;
    }

    public int getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(int anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public long getCantidadEgresados() {
        return cantidadEgresados;
    }

    public void setCantidadEgresados(long cantidadEgresados) {
        this.cantidadEgresados = cantidadEgresados;
    }

    public long getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(long cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    public Long getLuEstudiante() {
        return luEstudiante;
    }

    public void setLuEstudiante(Long luEstudiante) {
        this.luEstudiante = luEstudiante;
    }
}
