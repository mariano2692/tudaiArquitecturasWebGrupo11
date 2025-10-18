package models;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@ToString
public class EstudianteCarrera {
    @Id
    @Setter(AccessLevel.NONE)
    private int id;

    // Relación muchos a uno con Estudiante
    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    // Relación muchos a uno con Carrera
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @Column(nullable = false)
    private int anioInscripcion;

    @Column
    private int anioEgreso;

    @Column
    private int antiguedad;

    private boolean graduado;

    public EstudianteCarrera() {}

    public EstudianteCarrera(int id, Estudiante estudiante, Carrera carrera, int anioInscripcion,
                             int anioEgreso, int antiguedad, boolean graduado) {
        this.id = id;
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.antiguedad = antiguedad;
        this.graduado = graduado;
    }
}
