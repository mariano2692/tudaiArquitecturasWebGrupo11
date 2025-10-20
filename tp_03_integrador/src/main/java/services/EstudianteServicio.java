package services;

import dtos.EstudianteDTO;
import entities.Estudiante;
import org.springframework.stereotype.Service;
import repositories.JpaEstudianteRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EstudianteServicio {

    private final JpaEstudianteRepository jpaEstudianteRepository;


    public EstudianteServicio(JpaEstudianteRepository jpaEstudianteRepository){
        this.jpaEstudianteRepository = jpaEstudianteRepository;
    }

    public List<EstudianteDTO> getEstudiantes(){
        return jpaEstudianteRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    public EstudianteDTO save(EstudianteDTO estudianteDTO){
        Estudiante estudiante = new Estudiante(
            estudianteDTO.getDni(),
            estudianteDTO.getNombres(),
            estudianteDTO.getApellido(),
            estudianteDTO.getEdad(),
            estudianteDTO.getGenero(),
            estudianteDTO.getCiudadResidencia(),
            estudianteDTO.getLu()
            );
        jpaEstudianteRepository.save(estudiante);
    return toDTO(estudiante);
    }

    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre() {
        List<Estudiante> estudiantes = jpaEstudianteRepository.findAllByOrderByNombresAsc();
        return estudiantes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public List<EstudianteDTO> selectByGenero(String genero) {
        List<Estudiante> estudiantes = jpaEstudianteRepository.findByGenero(genero);
        return estudiantes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public EstudianteDTO selectById(int id) {
        Estudiante estudiante = jpaEstudianteRepository.findById(id).orElse(null);
        if (estudiante == null) {
            throw new RuntimeException("Estudiante con ID " + id + " no encontrado");
        }
        return toDTO(estudiante);
    }

     EstudianteDTO toDTO(Estudiante estudiante){
        return new EstudianteDTO(
                estudiante.getNombres(),
                estudiante.getApellido(),
                estudiante.getEdad(),
                estudiante.getGenero(),
                estudiante.getDni(),
                estudiante.getCiudadResidencia(),
                estudiante.getLu()
        );
    }

}
