package helpers;

import entities.Carrera;
import entities.Estudiante;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import entities.Inscripcion;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

public class CSVreader {

    public List<Carrera> leerArchivoCarreras() {
        List<Carrera> carreras = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/carreras.csv");
             Reader reader = new InputStreamReader(in);
             CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            for (CSVRecord row : parser) {
                Carrera c = new Carrera(row.get("nombre"));
                carreras.add(c);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return carreras;
    }

    public List<Estudiante> leerArchivoEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudiantes.csv");
             Reader reader = new InputStreamReader(in);
             CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            for (CSVRecord row : parser) {
                Estudiante e = new Estudiante(
                        row.get("nombres"),
                        row.get("apellido"),
                        Integer.parseInt(row.get("edad")),
                        row.get("genero"),
                        Integer.parseInt(row.get("dni")),
                        row.get("ciudadResidencia"),
                        Long.parseLong(row.get("lu"))
                );
                estudiantes.add(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }

    public List<Inscripcion> leerArchivoInscripciones(List<Carrera> carreras, List<Estudiante> estudiantes) {
        List<Inscripcion> inscripciones = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/inscripciones.csv");
             Reader reader = new InputStreamReader(in);
             CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            for (CSVRecord row : parser) {
                int antiguedad = Integer.parseInt(row.get("antiguedad"));
                LocalDate anioInscripcion = LocalDate.parse(row.get("anioInscripcion"));
                LocalDate anioEgreso = row.get("anioEgreso").isEmpty() ? null : LocalDate.parse(row.get("anioEgreso"));
                boolean graduado = Boolean.parseBoolean(row.get("graduado"));

                int idCarrera = Integer.parseInt(row.get("idCarrera"));
                int idEstudiante = Integer.parseInt(row.get("idEstudiante"));

                Carrera carrera = carreras.stream()
                        .filter(c -> c.getId() == idCarrera)
                        .findFirst()
                        .orElse(null);

                Estudiante estudiante = estudiantes.stream()
                        .filter(e -> e.getId() == idEstudiante)
                        .findFirst()
                        .orElse(null);

                Inscripcion i = new Inscripcion(antiguedad, anioInscripcion, anioEgreso, graduado, carrera, estudiante);
                inscripciones.add(i);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return inscripciones;
    }
}
