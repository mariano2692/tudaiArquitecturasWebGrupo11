package entities;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Inscripcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Hibernate genera el ID
    private Integer id;

    @Column
    private int antiguedad;

    @Column(nullable = false)
    private int anioInscripcion;

    private int anioEgreso;

    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private Carrera carrera;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    public Inscripcion() {}

    // Constructor sin ID para persistencia
    public Inscripcion(Carrera carrera, Estudiante estudiante, int anioInscripcion,
                       int anioEgreso, int antiguedad) {
        this.carrera = carrera;
        this.estudiante = estudiante;
        this.anioInscripcion = anioInscripcion;
        this.anioEgreso = anioEgreso;
        this.antiguedad = antiguedad;
    }

    public Inscripcion(Carrera carrera, Estudiante estudiante) {
        this.carrera = carrera;
        this.estudiante = estudiante;
        this.anioInscripcion = LocalDate.now().getYear();
        this.antiguedad = 0;
        this.anioEgreso = 0;
    }

    // Getters y setters
    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public int getAntiguedad() { return antiguedad; }

    public void setAntiguedad(int antiguedad) { this.antiguedad = antiguedad; }

    public int getAnioInscripcion() { return anioInscripcion; }

    public void setAnioInscripcion(int anioInscripcion) { this.anioInscripcion = anioInscripcion; }

    public int getAnioEgreso() { return anioEgreso; }

    public void setAnioEgreso(int anioEgreso) { this.anioEgreso = anioEgreso; }

    public Carrera getCarrera() { return carrera; }

    public void setCarrera(Carrera carrera) { this.carrera = carrera; }

    public Estudiante getEstudiante() { return estudiante; }

    public void setEstudiante(Estudiante estudiante) { this.estudiante = estudiante; }

    @Override
    public String toString() {
        return "Inscripcion{" +
                "id=" + id +
                ", antiguedad=" + antiguedad +
                ", anioInscripcion=" + anioInscripcion +
                ", anioEgreso=" + anioEgreso +
                ", carrera=" + carrera.getNombre() +
                ", LU del estudiante=" + estudiante.getLu() +
                '}';
    }

    public boolean isGraduado() {
        return this.anioEgreso > this.anioInscripcion;
    }
}
