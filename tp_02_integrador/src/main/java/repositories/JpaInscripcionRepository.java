package repositories;

import dtos.InscripcionDTO;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import repositories.interfaces.RepositoryInscripcion;

import java.util.List;

public class JpaInscripcionRepository implements RepositoryInscripcion {
    private EntityManager em;
    private static JpaInscripcionRepository instance;

    private JpaInscripcionRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaInscripcionRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaInscripcionRepository(em);
        return instance;
    }

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Inscripcion t) {

    }

    @Override
    public InscripcionDTO selectById(int id) {
        return null;
    }

    @Override
    public List<InscripcionDTO> selectAll() {
        return List.of();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }
}
