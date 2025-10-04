package repositories;

import dtos.CarreraDTO;
import entities.Carrera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
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
    public void save(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if(carrera.getId() == 0){
            try {
                em.persist(carrera);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar carrera! " + e.getMessage());
                throw e;
            }
        } else { // Si la carrera ya existe, hace update
            try {
                em.merge(carrera);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar carrera! " + e.getMessage());
                throw e;
            }
        }
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
