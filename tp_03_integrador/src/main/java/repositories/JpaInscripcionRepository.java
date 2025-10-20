package repositories;

import dtos.CarreraConCantInscriptosDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import repositories.interfaces.RepositoryInscripcion;

import java.time.LocalDate;
import java.util.List;
@Repository
public interface JpaInscripcionRepository extends JpaRepository<Inscripcion, Integer> {
    List<Inscripcion> findByCarreraId(int idCarrera);

}
