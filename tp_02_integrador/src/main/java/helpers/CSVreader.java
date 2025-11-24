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

            if (in == null) {
                System.err.println("ERROR: No se pudo encontrar el archivo carreras.csv en el classpath");
                return carreras;
            }

            System.out.println("Leyendo archivo carreras.csv...");
            for (CSVRecord row : parser) {
                Carrera c = new Carrera(row.get("carrera"));
                carreras.add(c);
            }
            System.out.println("Carreras leídas: " + carreras.size());

        } catch (IOException e) {
            System.err.println("Error al leer carreras.csv: " + e.getMessage());
            e.printStackTrace();
        }

        return carreras;
    }

    public List<Estudiante> leerArchivoEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudiantes.csv");
             Reader reader = new InputStreamReader(in);
             CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(reader)) {

            if (in == null) {
                System.err.println("ERROR: No se pudo encontrar el archivo estudiantes.csv en el classpath");
                return estudiantes;
            }

            System.out.println("Leyendo archivo estudiantes.csv...");
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
            System.out.println("Estudiantes leídos: " + estudiantes.size());

        } catch (IOException e) {
            System.err.println("Error al leer estudiantes.csv: " + e.getMessage());
            e.printStackTrace();
        }

        return estudiantes;
    }

    public List<Inscripcion> leerArchivoEstudianteCarrera(List<Carrera> carreras, List<Estudiante> estudiantes) throws IOException {
        List<Inscripcion> inscripciones = new ArrayList<>();

        try (InputStream in = getClass().getClassLoader().getResourceAsStream("csv_files/estudianteCarrera.csv");
             Reader reader = new InputStreamReader(in)) {

            if (in == null) {
                System.err.println("ERROR: No se pudo encontrar el archivo estudianteCarrera.csv en el classpath");
                return inscripciones;
            }

            System.out.println("Leyendo archivo estudianteCarrera.csv...");
            System.out.println("Carreras disponibles: " + carreras.size());
            System.out.println("Estudiantes disponibles: " + estudiantes.size());

            // Debug: mostrar IDs de las primeras 3 carreras
            for (int i = 0; i < Math.min(3, carreras.size()); i++) {
                System.out.println("Carrera " + i + ": ID=" + carreras.get(i).getId() + ", Nombre=" + carreras.get(i).getNombre());
            }

            // Debug: mostrar DNIs de los primeros 3 estudiantes
            for (int i = 0; i < Math.min(3, estudiantes.size()); i++) {
                System.out.println("Estudiante " + i + ": DNI=" + estudiantes.get(i).getDni() + ", LU=" + estudiantes.get(i).getLu());
            }

            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            int contador = 0;
            int encontrados = 0;
            for (CSVRecord row : records) {
                contador++;
                Long id = Long.parseLong(row.get("id"));
                Long idEstudiante = Long.parseLong(row.get("id_estudiante"));
                int idCarrera = Integer.parseInt(row.get("id_carrera"));

                int anioInscripcion = Integer.parseInt(row.get("inscripcion"));
                int anioGraduacion = Integer.parseInt(row.get("graduacion"));
                int antiguedad = Integer.parseInt(row.get("antiguedad"));

                LocalDate inscripcion = LocalDate.of(anioInscripcion, 1, 1);
                LocalDate graduacion = LocalDate.of(anioGraduacion, 1, 1);



                Estudiante estudiante = estudiantes.stream()
                        .filter(e -> e.getDni() == idEstudiante.intValue())
                        .findFirst()
                        .orElse(null);

                Carrera carrera = carreras.stream()
                        .filter(c -> c.getId() == idCarrera)
                        .findFirst()
                        .orElse(null);

                if (estudiante == null) {
                    System.out.println("No se encontró estudiante con DNI: " + idEstudiante);
                } else {
                    encontrados++;
                }
                if (carrera == null) {
                    System.out.println("No se encontró carrera con ID: " + idCarrera);
                }

                // Debug para los primeros 3 registros
                if (contador <= 3) {
                    System.out.println("Registro " + contador + ": DNI=" + idEstudiante + ", ID_Carrera=" + idCarrera +
                            ", Estudiante=" + (estudiante != null ? "ENCONTRADO" : "NULL") +
                            ", Carrera=" + (carrera != null ? "ENCONTRADA" : "NULL"));
                }

                inscripciones.add(new Inscripcion(antiguedad, inscripcion, graduacion, false, carrera, estudiante));

                // Debug cada 10 registros
                if (contador % 10 == 0) {
                    System.out.println("Procesados: " + contador + ", Encontrados: " + encontrados);
                }
            }
            System.out.println("Inscripciones leídas: " + inscripciones.size());
        }
        return inscripciones;
    }


}

