package org.example.tp_03_integrador.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Estudiante {
    @Id
    private int dni;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private int edad;

    @Column
    private String genero;

    @Column
    private String ciudadResidencia;

    @Column
    private Long lu;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "estudiante", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    private List<EstudianteCarrera> inscripciones;

    public Estudiante() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public Estudiante(int dni, String nombre, String apellido, int edad, String genero,
                      String ciudadResidencia, Long lu) {
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.ciudadResidencia = ciudadResidencia;
        this.lu = lu;
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public List<EstudianteCarrera> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(EstudianteCarrera estudianteCarrera) {
        if (!inscripciones.contains(estudianteCarrera)) {
            inscripciones.add(estudianteCarrera);
            estudianteCarrera.setEstudiante(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(EstudianteCarrera estudianteCarrera) {
        if (inscripciones.contains(estudianteCarrera)) {
            inscripciones.remove(estudianteCarrera);
            estudianteCarrera.setEstudiante(null); // Mantener la relación bidireccional
        }
    }
}
