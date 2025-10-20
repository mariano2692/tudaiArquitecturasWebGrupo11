package repositories;

import dtos.CarreraDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositories.interfaces.RepositoryCarrera;

import java.util.List;
import java.util.stream.Collectors;
@Repository
public interface JpaCarreraRepository extends JpaRepository<Carrera, Integer> {

    Carrera findByNombre(String nombre);

}
