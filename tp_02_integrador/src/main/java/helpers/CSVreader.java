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
                Carrera c = new Carrera(row.get("carrera"));
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
                        row.get("nombre"),
                        row.get("apellido"),
                        Integer.parseInt(row.get("edad")),
                        row.get("genero"),
                        Integer.parseInt(row.get("DNI")),
                        row.get("ciudad"),
                        Long.parseLong(row.get("LU"))
                );
                estudiantes.add(e);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return estudiantes;
    }

    public List<Inscripcion> leerArchivoEstudianteCarrera(List<Carrera> carreras, List<Estudiante> estudiantes) throws IOException {
        List<Inscripcion> inscripciones = new ArrayList<>();

        // Intentar abrir el archivo CSV desde resources
        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudianteCarrera.csv")) {

            if (in == null) {
                System.err.println("❌ ERROR: No se pudo encontrar el archivo 'csv_files/estudianteCarrera.csv' en el classpath");
                return inscripciones; // devuelve lista vacía
            }

            System.out.println("✅ Leyendo archivo estudianteCarrera.csv...");

            // Preparar el lector y el parser
            try (Reader reader = new InputStreamReader(in)) {
                Iterable<CSVRecord> records = CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .parse(reader);

                int contador = 0;

                for (CSVRecord row : records) {
                    try {
                        Long idEstudiante = Long.parseLong(row.get("id_estudiante"));
                        int idCarrera = Integer.parseInt(row.get("id_carrera"));
                        int anioInscripcion = Integer.parseInt(row.get("inscripcion"));
                        int anioGraduacion = Integer.parseInt(row.get("graduacion"));
                        int antiguedad = Integer.parseInt(row.get("antiguedad"));

                        // Crear fechas de inscripción y graduación
                        LocalDate inscripcion = LocalDate.of(anioInscripcion, 1, 1);
                        LocalDate graduacion = LocalDate.of(anioGraduacion, 1, 1);

                        // Buscar estudiante y carrera
                        Estudiante estudiante = estudiantes.stream()
                                .filter(e -> e.getLu().equals(idEstudiante))
                                .findFirst()
                                .orElse(null);

                        Carrera carrera = carreras.stream()
                                .filter(c -> c.getId() == idCarrera)
                                .findFirst()
                                .orElse(null);

                        // Validar que existan antes de crear la inscripción
                        if (estudiante == null || carrera == null) {
                            System.out.println("⚠️ No se encontró estudiante o carrera para id_estudiante=" +
                                    idEstudiante + ", id_carrera=" + idCarrera);
                            continue; // salta ese registro
                        }

                        // Crear la inscripción
                        Inscripcion inscripcionObj = new Inscripcion(
                                antiguedad,
                                inscripcion,
                                graduacion,
                                false,
                                carrera,
                                estudiante
                        );

                        inscripciones.add(inscripcionObj);
                        contador++;

                    } catch (NumberFormatException e) {
                        System.out.println("⚠️ Error de formato en fila: " + row + " -> " + e.getMessage());
                    }
                }

                System.out.println("✅ Inscripciones leídas correctamente: " + contador);
            }

        } catch (IOException e) {
            System.err.println("❌ Error al leer el archivo CSV de inscripciones: " + e.getMessage());
            throw e;
        }

        return inscripciones;
    }


}
