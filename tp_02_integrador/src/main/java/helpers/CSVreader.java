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
                 CSVParser parser = CSVFormat.DEFAULT
                         .withFirstRecordAsHeader()
                         .withIgnoreHeaderCase()
                         .withTrim()
                         .parse(reader)) {

                for (CSVRecord row : parser) {
                    try {
                        String idStr = row.get("id_carrera");
                        String nombre = row.get("carrera");
                        String duracionStr = row.get("duracion");

                        if (idStr == null || idStr.isEmpty() ||
                                nombre == null || nombre.isEmpty() ||
                                duracionStr == null || duracionStr.isEmpty()) {
                            System.out.println("Fila inválida o incompleta: " + row);
                            continue;
                        }

                        int id = Integer.parseInt(idStr.trim());
                        int duracion = Integer.parseInt(duracionStr.trim());

                        Carrera c = new Carrera(id, nombre, duracion);
                        carreras.add(c);

                    } catch (Exception e) {
                        System.out.println("Error al procesar fila: " + row + " → " + e.getMessage());
                    }
                }

            } catch (IOException e) {
                System.out.println("Error al leer el archivo carreras.csv: " + e.getMessage());
                e.printStackTrace();
            } catch (NullPointerException e) {
                System.out.println("Archivo carreras.csv no encontrado en classpath.");
            }

            return carreras;
        }

        public List<Estudiante> leerArchivoEstudiantes() {
            List<Estudiante> estudiantes = new ArrayList<>();

            try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudiantes.csv")) {

                if (in == null) {
                    System.err.println("No se encontró el archivo estudiantes.csv en el classpath.");
                    return estudiantes;
                }

                try (Reader reader = new InputStreamReader(in);
                     CSVParser parser = CSVFormat.DEFAULT
                             .withFirstRecordAsHeader()
                             .withIgnoreHeaderCase()
                             .withTrim()
                             .parse(reader)) {

                    for (CSVRecord row : parser) {
                        try {
                            int dni = Integer.parseInt(row.get("DNI").trim());
                            String nombres = row.get("nombre").trim();
                            String apellido = row.get("apellido").trim();
                            int edad = Integer.parseInt(row.get("edad").trim());
                            String genero = row.get("genero").trim();
                            String ciudad = row.get("ciudad").trim();
                            Long lu = Long.parseLong(row.get("LU").trim());

                            Estudiante e = new Estudiante(dni, nombres, apellido, edad, genero, ciudad, lu);
                            estudiantes.add(e);

                        } catch (Exception ex) {
                            System.err.println("Error al procesar fila: " + row.toString() + " → " + ex.getMessage());
                        }
                    }

                }

            } catch (IOException e) {
                System.err.println("Error al leer el archivo estudiantes.csv: " + e.getMessage());
                e.printStackTrace();
            }

            return estudiantes;
        }

    public List<Inscripcion> leerArchivoEstudianteCarrera(List<Carrera> carreras, List<Estudiante> estudiantes) throws IOException {
        List<Inscripcion> inscripciones = new ArrayList<>();
        System.out.println("Iniciando lectura de inscripciones...");

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudianteCarrera.csv");
             Reader reader = new InputStreamReader(in)) {

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord row : records) {
                try {
                    int idEstudiante = Integer.parseInt(row.get("id_estudiante"));
                    int idCarrera = Integer.parseInt(row.get("id_carrera"));
                    int anioInscripcion = Integer.parseInt(row.get("inscripcion"));
                    int anioGraduacion = Integer.parseInt(row.get("graduacion"));
                    int antiguedad = Integer.parseInt(row.get("antiguedad"));

                    LocalDate fechaInscripcion = LocalDate.of(anioInscripcion, 1, 1);
                    LocalDate fechaGraduacion = (anioGraduacion > 0) ? LocalDate.of(anioGraduacion, 1, 1) : null;
                    boolean graduado = anioGraduacion > 0;

                    // Buscar estudiante en la lista por DNI
                    Estudiante estudiante = estudiantes.stream()
                            .filter(e -> e.getId() == idEstudiante)
                            .findFirst()
                            .orElse(null);

                    // Buscar carrera en la lista por ID
                    Carrera carrera = carreras.stream()
                            .filter(c -> c.getId() == idCarrera)
                            .findFirst()
                            .orElse(null);

                    if (estudiante != null && carrera != null) {
                        // Crear inscripción sin asignar ID
                        Inscripcion inscripcion = new Inscripcion(carrera, estudiante, fechaInscripcion, fechaGraduacion, graduado, antiguedad);
                        inscripciones.add(inscripcion);

                        // Mantener relación bidireccional
                        estudiante.addInscripcion(inscripcion);
                        carrera.addInscripcion(inscripcion);
                    }

                } catch (Exception e) {
                    System.out.println("⚠ Error procesando fila: " + row);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Total inscripciones cargadas: " + inscripciones.size());
        return inscripciones;
    }






}