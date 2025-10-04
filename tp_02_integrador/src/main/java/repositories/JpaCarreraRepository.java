package repositories;

import dtos.CarreraDTO;
import entities.Carrera;
import jakarta.persistence.EntityManager;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryInscripcion;

import java.util.List;

public class JpaCarreraRepository implements RepositoryCarrera {
    private EntityManager em;
    private static JpaCarreraRepository instance;

    private JpaCarreraRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaCarreraRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaCarreraRepository(em);
        return instance;
    }

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Carrera t) {

    }

    @Override
    public CarreraDTO selectById(int id) {
        return null;
    }

    @Override
    public List<CarreraDTO> selectAll() {
        return List.of();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
