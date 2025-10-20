package services;

import dtos.CarreraDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Inscripcion;
import org.springframework.stereotype.Service;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InscripcionServicio {

    private final JpaInscripcionRepository jpaInscripcionRepository;
    private final JpaCarreraRepository jpaCarreraRepository;
    private final JpaEstudianteRepository jpaEstudianteRepository;
    private final EstudianteServicio estudianteServicio; // para toDTO

    public InscripcionServicio(JpaInscripcionRepository jpaInscripcionRepository,
                              JpaCarreraRepository jpaCarreraRepository,
                              JpaEstudianteRepository jpaEstudianteRepository,
                              EstudianteServicio estudianteServicio) {
        this.jpaInscripcionRepository = jpaInscripcionRepository;
        this.jpaCarreraRepository = jpaCarreraRepository;
        this.jpaEstudianteRepository = jpaEstudianteRepository;
        this.estudianteServicio = estudianteServicio;
    }

    /**
     * Matricula un estudiante en una carrera a partir de los IDs.
     */
    public String matricularEstudianteEnCarrera(int idCarrera, int idEstudiante) {
        var estudiante = jpaEstudianteRepository.findById(idEstudiante).orElse(null);
        if (estudiante == null) {
            return "Estudiante no encontrado con ID: " + idEstudiante;
        }

        var carrera = jpaCarreraRepository.findById(idCarrera).orElse(null);
        if (carrera == null) {
            return "Carrera no encontrada con ID: " + idCarrera;
        }

        Inscripcion inscripcion = new Inscripcion(carrera, estudiante);
        jpaInscripcionRepository.save(inscripcion);

        return "Estudiante matriculado correctamente en la carrera con ID: " + idCarrera;
    }

    /**
     * Devuelve todas las inscripciones como DTO.
     */
    public List<InscripcionDTO> obtenerTodasLasInscripciones() {
        return jpaInscripcionRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<CarreraDTO> obtenerCarrerasOrdenadasPorCantidadInscriptos() {
        // Traemos todas las carreras
        List<Carrera> carreras = jpaCarreraRepository.findAll();

        // Convertimos a DTO y contamos inscripciones
        return carreras.stream()
                .map(carrera -> {
                    CarreraDTO dto = new CarreraDTO(carrera.getNombre());
                    // Convertir las inscripciones a DTO si quieres
                    List<InscripcionDTO> inscripcionesDTO = carrera.getInscripciones().stream()
                            .map(this::toDTO)
                            .toList();
                    inscripcionesDTO.forEach(dto::addInscripcion);
                    return dto;
                })
                // Ordenamos por cantidad de inscriptos descendente
                .sorted((c1, c2) -> Integer.compare(c2.getInscripciones().size(), c1.getInscripciones().size()))
                .toList();
    }

    public List<CarreraDTO> generarReporteCarreras() {
        // Traemos todas las carreras
        List<Carrera> carreras = jpaCarreraRepository.findAll();

        // Convertimos a DTO y generamos el resumen anual
        return carreras.stream()
                .map(carrera -> {
                    CarreraDTO dto = new CarreraDTO(carrera.getNombre());

                    // Agregamos inscripciones convertidas a DTO
                    List<InscripcionDTO> inscripcionesDTO = carrera.getInscripciones().stream()
                            .map(this::toDTO)
                            .toList();
                    inscripcionesDTO.forEach(dto::addInscripcion);

                    // Ya puedes usar el método getResumenPorAnio() de CarreraDTO
                    dto.getResumenPorAnio(); // Devuelve lista de ResumenAnualDTO ordenada por año

                    return dto;
                })
                // Ordenamos alfabeticamente por nombre de carrera
                .sorted(Comparator.comparing(CarreraDTO::getNombre))
                .toList();
    }

    public List<EstudianteDTO> obtenerEstudiantesPorCarreraYCiudad(int idCarrera, String ciudad) {
        // Buscar la carrera
        Carrera carrera = jpaCarreraRepository.findById(idCarrera).orElse(null);
        if (carrera == null) {
            throw new RuntimeException("Carrera con ID " + idCarrera + " no encontrada");
        }

        // Filtrar inscripciones por ciudad del estudiante
        return carrera.getInscripciones().stream()
                .map(Inscripcion::getEstudiante)           // Obtener estudiante de cada inscripcion
                .filter(e -> ciudad.equalsIgnoreCase(e.getCiudadResidencia()))
                .map(estudianteServicio::toDTO)                 // Convertir a EstudianteDTO
                .toList();
    }

    /**
     * Convierte una entidad Inscripcion a InscripcionDTO
     */
    public InscripcionDTO toDTO(Inscripcion inscripcion) {
        EstudianteDTO estudianteDTO = estudianteServicio.toDTO(inscripcion.getEstudiante());
        return new InscripcionDTO(
                inscripcion.getAntiguedad(),
                inscripcion.getAnioInscripcion(),
                inscripcion.getAnioEgreso(),
                inscripcion.isGraduado(),
                inscripcion.getCarrera().getNombre(),
                estudianteDTO
        );
    }
}
