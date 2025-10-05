package entities;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private int antiguedad;

    @Column(nullable = false)
    private LocalDate anioInscripcion;

    private LocalDate anioEgreso;

    private boolean graduado;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    // Constructores
    public Inscripcion() {

    }

    public Inscripcion(Carrera carrera, Estudiante estudiante) {
        this.carrera = carrera;
        this.estudiante = estudiante;
        this.anioInscripcion = LocalDate.now();
        this.antiguedad = 0;
        this.graduado = false;
        this.anioEgreso = null;
    }

    public Inscripcion(int antiguedad, LocalDate anioInscripcion, LocalDate anioEgreso, boolean graduado, Carrera carrera, Estudiante estudiante) {
        this.antiguedad = antiguedad;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.graduado = graduado;
        this.carrera = carrera;
        this.estudiante = estudiante;
    }

    // Getters y Setters
    public int getId() {
        return id;
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

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", antiguedad=" + antiguedad +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", graduado=" + graduado +
                ", carrera=" + carrera.getNombre() +
                ", LU del estudiante=" + estudiante.getLu() +
                '}';
    }
}