package repositories.interfaces;

import dtos.EstudianteDTO;
import entities.Estudiante;

import java.util.List;

public interface RepositoryEstudiante{
    void save(Estudiante t);

    EstudianteDTO selectById(int id);

    List<EstudianteDTO> selectAll();

    boolean delete(int id);
    
    // Nuevos métodos requeridos
    EstudianteDTO selectByLu(Long lu);
    
    List<EstudianteDTO> selectByGenero(String genero);

    List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre();
    
    EstudianteDTO darDeAltaEstudiante(int dni, String nombres, String apellido, int edad, String genero, String ciudadResidencia, Long lu);
}
