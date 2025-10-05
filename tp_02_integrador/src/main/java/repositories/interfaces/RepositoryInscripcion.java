package repositories.interfaces;

import dtos.InscripcionDTO;
import entities.Inscripcion;

import java.util.List;

public interface RepositoryInscripcion {
    void save(Inscripcion t);

    InscripcionDTO selectById(int id);

    List<InscripcionDTO> selectAll();

    boolean delete(int id);
}
