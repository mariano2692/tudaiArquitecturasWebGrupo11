package org.example.tp_03_integrador.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.tp_03_integrador.entities.Carrera;
import org.example.tp_03_integrador.entities.Estudiante;
import org.example.tp_03_integrador.entities.EstudianteCarrera;
import org.example.tp_03_integrador.repositories.CarreraRepository;
import org.example.tp_03_integrador.repositories.EstudianteCarreraRepository;
import org.example.tp_03_integrador.repositories.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CargaDeDatos {
    @Autowired
    private CarreraRepository rc;
    @Autowired
    private EstudianteRepository re;
    @Autowired
    private EstudianteCarreraRepository rec;

    public void cargarDatosDesdeCSV() throws IOException {
        File carrerasCSV = ResourceUtils.getFile("classpath:csv/carreras.csv");
        File estudiantesCSV = ResourceUtils.getFile("classpath:csv/estudiantes.csv");
        File estudianteCarreraCSV = ResourceUtils.getFile("classpath:csv/estudianteCarrera.csv");

        try (FileReader readerCarreras = new FileReader(carrerasCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerCarreras)) {

            for (CSVRecord csvRecord : csvParser) {
                // Creación de la entidad Carrera
                Carrera carrera = new Carrera(
                        Integer.parseInt(csvRecord.get("id_carrera")),
                        csvRecord.get("carrera")
                );
                // Guardar carrera en la base de datos
                this.rc.saveAndFlush(carrera);
            }
        }

        try (FileReader readerEstudiantes = new FileReader(estudiantesCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerEstudiantes)) {

            for (CSVRecord csvRecord : csvParser) {
                // Creación de la entidad Estudiante
                Estudiante estudiante = new Estudiante(
                        Integer.parseInt(csvRecord.get("DNI")),
                        csvRecord.get("nombre"),
                        csvRecord.get("apellido"),
                        Integer.parseInt(csvRecord.get("edad")),
                        csvRecord.get("genero"),
                        csvRecord.get("ciudad"),
                        Long.parseLong(csvRecord.get("LU"))
                );

                re.save(estudiante); // Guardar estudiante en la base de datos
            }
        }

        try (FileReader readerEstudianteCarrera = new FileReader(estudianteCarreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerEstudianteCarrera)) {

            for (CSVRecord csvRecord : csvParser) {
                try {
                    // Manejo del Optional para el estudiante
                    Estudiante estudiante = re.findByDni(Integer.parseInt(csvRecord.get("id_estudiante")))
                            .orElse(null);

                    // Si el estudiante no existe, saltamos este registro
                    if (estudiante == null) {
                        System.out.println("Saltando registro - Estudiante no encontrado con id=" + csvRecord.get("id_estudiante"));
                        continue;
                    }

                    // Manejo del Optional para la carrera
                    Carrera carrera = rc.findById(Integer.parseInt(csvRecord.get("id_carrera")));

                    // Si la carrera no existe, saltamos este registro
                    if (carrera == null) {
                        System.out.println("Saltando registro - Carrera no encontrada con id=" + csvRecord.get("id_carrera"));
                        continue;
                    }

                    // Creación de la entidad EstudianteCarrera
                    EstudianteCarrera estudianteCarrera = new EstudianteCarrera(
                            Integer.parseInt(csvRecord.get("id")),
                            estudiante,
                            carrera,
                            Integer.parseInt(csvRecord.get("inscripcion")),
                            Integer.parseInt(csvRecord.get("graduacion")),
                            Integer.parseInt(csvRecord.get("antiguedad")),
                            csvRecord.get("graduacion").equals("0") ? false : true
                    );

                    // Guardar la inscripción en la base de datos
                    rec.save(estudianteCarrera);
                } catch (Exception e) {
                    System.out.println("Error procesando registro: " + e.getMessage());
                    continue;
                }
            }
        }
    }
}
