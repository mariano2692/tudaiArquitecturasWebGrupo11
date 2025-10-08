package dtos;

import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import repositories.JpaEstudianteRepository;

import java.time.LocalDate;

public class InscripcionDTO {
    private int antiguedad;
    private LocalDate anioInscripcion;
    private LocalDate anioEgreso;
    private boolean graduado;
    private String nombreCarrera;
    private EstudianteDTO estudiante;

    public InscripcionDTO() {}

    // Constructor para recibir EstudianteDTO
    public InscripcionDTO(int antiguedad, LocalDate anioInscripcion, LocalDate anioEgreso,
                          boolean graduado, String nombreCarrera, EstudianteDTO estudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.nombreCarrera = nombreCarrera;
        this.estudiante = estudiante;
    }

    public InscripcionDTO(int antiguedad, LocalDate anioInscripcion, LocalDate anioEgreso, boolean graduado, String nombreCarrera, Long luEstudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.nombreCarrera = nombreCarrera;

    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public LocalDate getAnioInscripcion() {
        return anioInscripcion;
    }

    public void setAnioInscripcion(LocalDate anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    public LocalDate getAnioEgreso() {
        return anioEgreso;
    }

    public void setAnioEgreso(LocalDate anioEgreso) {
        this.anioEgreso = anioEgreso;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
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
                ", graduado=" + graduado +
                ", carrera=" + nombreCarrera +
                ", estudiante=" + estudiante +
                '}';
    }
}
