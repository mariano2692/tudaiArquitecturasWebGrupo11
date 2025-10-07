package repositories.interfaces;

import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;

import java.time.LocalDate;
import java.util.List;

public interface RepositoryInscripcion {
    void save(Inscripcion t);

    InscripcionDTO selectById(int id);

    List<InscripcionDTO> selectAll();

    boolean delete(int id);

    public List<InscripcionDTO> studentsByCareerAndCity(String career, String city);

    public boolean existeInscripcion(Estudiante estudiante, Carrera carrera, LocalDate anioInscripcion);
}
