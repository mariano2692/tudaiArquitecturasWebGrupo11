package utils;

import models.Carrera;
import models.Estudiante;
import models.EstudianteCarrera;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import repositories.CarreraRepository;
import repositories.EstudianteCarreraRepository;
import repositories.EstudianteRepository;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class CargaDeDatos {
    @Autowired
    private CarreraRepository carreraRepository;
    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private EstudianteCarreraRepository estudianteCarreraRepository;
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
                        csvRecord.get("nombre")
                );
                // Guardar carrera en la base de datos
                this.carreraRepository.saveAndFlush(carrera);
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

                estudianteRepository.save(estudiante); // Guardar estudiante en la base de datos
            }
        }

        try (FileReader readerEstudianteCarrera = new FileReader(estudianteCarreraCSV);
             CSVParser csvParser = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(readerEstudianteCarrera)) {

            for (CSVRecord csvRecord : csvParser) {
                // Manejo del Optional para el estudiante
                Estudiante estudiante = estudianteRepository.findById(Integer.parseInt(csvRecord.get("id_estudiante")))
                        .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con id=" + csvRecord.get("id_estudiante") + "!"));

                // Manejo del Optional para la carrera
                Carrera carrera = carreraRepository.findById(Integer.parseInt(csvRecord.get("id_carrera")))
                        .orElseThrow(() -> new RuntimeException("Carrera no encontrada con id=" + csvRecord.get("id_carrera") + "!"));

                // Creación de la entidad EstudianteCarrera
                EstudianteCarrera estudianteCarrera = new EstudianteCarrera(
                        Integer.parseInt(csvRecord.get("id")),
                        estudiante,
                        carrera,
                        Integer.parseInt(csvRecord.get("anioInscripcion")),
                        Integer.parseInt(csvRecord.get("anioEgreso")),
                        Integer.parseInt(csvRecord.get("antiguedad")),
                        Boolean.parseBoolean(csvRecord.get("graduado"))
                );

                // Guardar la inscripción en la base de datos
                estudianteCarreraRepository.save(estudianteCarrera);
            }
        }
    }
}
