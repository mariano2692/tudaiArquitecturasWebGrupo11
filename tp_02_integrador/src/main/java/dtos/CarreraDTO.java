package dtos;

import entities.Inscripcion;

import java.util.ArrayList;
import java.util.List;

public class CarreraDTO {
    private String nombre;
    private List<Inscripcion> inscripciones;

    public CarreraDTO() {}

    public CarreraDTO(String nombre) {
        this.nombre = nombre;
        this.inscripciones = new ArrayList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Inscripcion> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(Inscripcion inscripcion) {
        if (!inscripciones.contains(inscripcion)) {
            inscripciones.add(inscripcion);
        }
    }

    public void removeInscripcion(Inscripcion inscripcion) {
        inscripciones.remove(inscripcion);
    }

    @Override
    public String toString() {
        return "CarreraDTO{" +
                "nombre='" + nombre + '\'' +
                ", inscripciones=" + inscripciones +
                '}';
    }
}
