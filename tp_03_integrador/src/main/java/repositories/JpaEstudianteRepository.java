package repositories;

import entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JpaEstudianteRepository extends JpaRepository<Estudiante, Integer> {
    List<Estudiante> findByGenero(String genero);

    Estudiante findByLu(Long lu);

    List<Estudiante> findAllByOrderByNombresAsc();

    Estudiante findByNombresAndApellido(String nombres, String apellido);

}
