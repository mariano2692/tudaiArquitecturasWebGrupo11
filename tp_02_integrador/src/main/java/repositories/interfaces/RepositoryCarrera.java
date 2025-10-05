package repositories.interfaces;

import dtos.CarreraDTO;
import entities.Carrera;
import entities.Estudiante;

import java.util.List;

public interface RepositoryCarrera {
    void save(Carrera t);

    CarreraDTO selectById(int id);

    List<CarreraDTO> selectAll();

    boolean delete(int id);

    abstract void matricularEstudianteEnCarrera(String nombreCarrera, String nombreEstudiante, String apellidoEstudiante);
}
