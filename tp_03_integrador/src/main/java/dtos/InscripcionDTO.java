package dtos;

import java.time.LocalDate;

public class InscripcionDTO {
    private int antiguedad;
    private int anioInscripcion;
    private int anioEgreso;
    boolean isGraduado;
    private String nombreCarrera;
    private EstudianteDTO estudiante;

    public InscripcionDTO() {}

    // Constructor para recibir EstudianteDTO
    public InscripcionDTO(int antiguedad, int anioInscripcion, int anioEgreso, boolean graduado,
                          String nombreCarrera, EstudianteDTO estudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.isGraduado = graduado;
        this.nombreCarrera = nombreCarrera;
        this.estudiante = estudiante;
    }

    public InscripcionDTO(int antiguedad, int anioInscripcion, int anioEgreso, String nombreCarrera, Long luEstudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.nombreCarrera = nombreCarrera;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public int getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public int getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(int anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public boolean getIsGraduado() {
        return isGraduado;
    }

    public void setIsGraduado(boolean isGraduado) {
        this.isGraduado = isGraduado;
    }

    public String getCarrera() {
        return nombreCarrera;
    }

    public void setCarrera(String nombreCarrera) { this.nombreCarrera = nombreCarrera; }

    public EstudianteDTO getEstudiante() { return estudiante; }


    @Override
    public String toString() {
        return "InscripcionDTO{" +
                "antiguedad=" + antiguedad +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", carrera=" + nombreCarrera +
                ", estudiante=" + estudiante +
                '}';
    }

    public boolean isGraduado() {
        return this.anioEgreso > this.anioInscripcion;
    }
}
