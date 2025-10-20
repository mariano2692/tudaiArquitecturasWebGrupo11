package dtos;

import entities.Inscripcion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CarreraDTO {
    private String nombre;
    private List<InscripcionDTO> inscripciones;

    public CarreraDTO() {
        this.inscripciones = new ArrayList<>();
    }

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

    public List<InscripcionDTO> getInscripciones() {
        return new ArrayList<>(inscripciones);
    }

    public void addInscripcion(InscripcionDTO i) {
        inscripciones.add(i);
    }

    public void removeInscripcion(InscripcionDTO i) {
        inscripciones.remove(i);
    }

    /**
     * Devuelve un resumen anual de inscriptos y egresados, ordenado por año.
     */
    public List<ResumenAnualDTO> getResumenPorAnio() {
        return inscripciones.stream()
                .collect(Collectors.groupingBy(i -> i.getAnioInscripcion())) // ya es int
                .entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(entry -> {
                    int anio = entry.getKey();
                    List<InscripcionDTO> delAnio = entry.getValue();

                    long inscriptos = delAnio.size();
                    long egresados = delAnio.stream().filter(InscripcionDTO::isGraduado).count();

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