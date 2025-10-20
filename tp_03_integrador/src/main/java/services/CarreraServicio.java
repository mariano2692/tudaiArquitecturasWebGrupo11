package services;

import dtos.CarreraDTO;
import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import org.springframework.stereotype.Service;
import repositories.JpaCarreraRepository;
import repositories.JpaEstudianteRepository;
import repositories.JpaInscripcionRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CarreraServicio {

        private final JpaCarreraRepository jpaCarreraRepository;
        private final JpaEstudianteRepository jpaEstudianteRepository;
        private final JpaInscripcionRepository jpaInscripcionRepository;
        private final EstudianteServicio estudianteServicio;

        public CarreraServicio(JpaCarreraRepository jpaCarreraRepository,
                               JpaEstudianteRepository JpaEstudianteRepository,
                               JpaInscripcionRepository JpaInscripcionRepository,
                               EstudianteServicio estudianteServicio) {
            this.jpaCarreraRepository = jpaCarreraRepository;
            this.jpaEstudianteRepository = JpaEstudianteRepository;
            this.jpaInscripcionRepository = JpaInscripcionRepository;
            this.estudianteServicio = estudianteServicio;
        }
    // Obtener todas las carreras como DTO
    public List<CarreraDTO> getCarreras() {
        return jpaCarreraRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CarreraDTO agregarCarrera(String nombreCarrera) {
        // Validar si ya existe la carrera
        if (jpaCarreraRepository.findByNombre(nombreCarrera) != null) {
            throw new RuntimeException("La carrera ya existe: " + nombreCarrera);
        }

        // Crear entidad
        Carrera carrera = new Carrera();
        carrera.setNombre(nombreCarrera);

        // Guardar en base
        Carrera carreraGuardada = jpaCarreraRepository.save(carrera);

        // Retornar DTO
        return toDTO(carreraGuardada);
    }

    // Conversión de entidad Carrera a CarreraDTO
    private CarreraDTO toDTO(Carrera carrera) {
        CarreraDTO dto = new CarreraDTO(carrera.getNombre());

        // Convertir todas las inscripciones de la carrera a DTO
        for (Inscripcion ins : carrera.getInscripciones()) {
            InscripcionDTO insDTO = new InscripcionDTO(
                    ins.getAntiguedad(),
                    ins.getAnioInscripcion(),
                    ins.getAnioEgreso(),
                    ins.isGraduado(),
                    carrera.getNombre(),
                    estudianteServicio.toDTO(ins.getEstudiante())
            );
            dto.addInscripcion(insDTO);
        }

        return dto;
    }

}
