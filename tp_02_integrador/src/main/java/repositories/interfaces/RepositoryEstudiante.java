package repositories.interfaces;

import dtos.EstudianteDTO;
import entities.Estudiante;

import java.util.List;

public interface RepositoryEstudiante{
    void save(Estudiante t);

    EstudianteDTO selectById(int id);

    List<EstudianteDTO> selectAll();

    boolean delete(int id);

    List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre();
}
