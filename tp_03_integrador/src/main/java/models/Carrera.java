package models;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
public class Carrera {
    @Id
    @Setter(AccessLevel.NONE)
    private int id;

    @Column(nullable = false)
    private String nombre;

    // Relación uno a muchos con la entidad Inscripcion
    @OneToMany(mappedBy = "carrera", cascade = CascadeType.ALL, fetch = FetchType.LAZY) // CascadeType.ALL significa que todas las operaciones de cascada se aplicarán a las entidades relacionadas
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private List<EstudianteCarrera> inscripciones;

    public Carrera() {
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public Carrera(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.inscripciones = new ArrayList<EstudianteCarrera>();
    }

    public List<EstudianteCarrera> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(EstudianteCarrera estudianteCarrera) {
        if (!inscripciones.contains(estudianteCarrera)) {
            inscripciones.add(estudianteCarrera);
            estudianteCarrera.setCarrera(this); // Mantener la relación bidireccional
        }
    }

    public void removeInscripcion(EstudianteCarrera estudianteCarrera) {
        if (inscripciones.contains(estudianteCarrera)) {
            inscripciones.remove(estudianteCarrera);
            estudianteCarrera.setCarrera(null); // Mantener la relación bidireccional
        }
    }
}
