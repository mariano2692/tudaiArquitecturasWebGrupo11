package dtos;

import entities.Inscripcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    /**
     * Devuelve una lista de resúmenes (inscriptos / egresados) por año, ordenados cronológicamente.
     */
    public List<ResumenAnualDTO> getResumenPorAnio() {
        return inscripciones.stream()
                .collect(Collectors.groupingBy(i -> i.getAnioInscripcion().getYear()))
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey()) // ordena los años
                .map(entry -> {
                    int anio = entry.getKey();
                    List<Inscripcion> delAnio = entry.getValue();

                    long inscriptos = delAnio.size();
                    long egresados = delAnio.stream().filter(Inscripcion::isGraduado).count();

                    return new ResumenAnualDTO(anio, inscriptos, egresados);
                })
                .collect(Collectors.toList());
    }

    @Override
    public String toString() {
        return "CarreraDTO{" +
                "nombre='" + nombre + '\'' +
                ", inscripciones=" + inscripciones +
                '}';
    }
}
