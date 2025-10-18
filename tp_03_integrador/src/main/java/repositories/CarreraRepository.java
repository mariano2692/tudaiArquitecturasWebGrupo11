package repositories;

import models.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("CarreraRepository")
public interface CarreraRepository extends JpaRepository<Carrera, Integer> {
}
