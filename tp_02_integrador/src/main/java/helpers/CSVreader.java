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
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord row : records) {
                Long id = Long.parseLong(row.get("id"));
                Long idEstudiante = Long.parseLong(row.get("id_estudiante"));
                int idCarrera = Integer.parseInt(row.get("id_carrera"));

                int anioInscripcion = Integer.parseInt(row.get("inscripcion"));
                int anioGraduacion = Integer.parseInt(row.get("graduacion"));
                int antiguedad = Integer.parseInt(row.get("antiguedad"));

                LocalDate inscripcion = LocalDate.of(anioInscripcion, 1, 1);
                LocalDate graduacion = LocalDate.of(anioGraduacion, 1, 1);



                Estudiante estudiante = estudiantes.stream()
                        .filter(e -> e.getLu().equals(idEstudiante))
                        .findFirst()
                        .orElse(null);

                Carrera carrera = carreras.stream()
                        .filter(c -> c.getId() == idCarrera)
                        .findFirst()
                        .orElse(null);

                inscripciones.add(new Inscripcion(antiguedad, inscripcion, graduacion, false, carrera, estudiante));
            }
            System.out.println("Inscripciones leídas: " + inscripciones.size());
        }
        return inscripciones;
    }


}
