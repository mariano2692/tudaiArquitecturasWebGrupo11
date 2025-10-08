package repositories;

import dtos.EstudianteDTO;
import entities.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;

import java.util.List;

public class JpaEstudianteRepository implements RepositoryEstudiante {
    private EntityManager em;
    private static JpaEstudianteRepository instance;

    private JpaEstudianteRepository(EntityManager em) {
        this.em = em;
    }

    public static JpaEstudianteRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaEstudianteRepository(em);
        return instance;
    }

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Estudiante estudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();


            try {
                em.persist(estudiante);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar estudiante! " + e.getMessage());
                throw e;
            }

        }

    @Override
    public EstudianteDTO selectById(int id) {
        return null;
    }

    @Override
    public List<EstudianteDTO> selectAll() {
        return List.of();
    }

    @Override
    public boolean delete(int id) {
        return false;
    }


    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por nombre
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre() {
        try {
            return em.createQuery(
                    "SELECT new dtos.EstudianteDTO(e.nombres, e.apellido, e.edad, e.genero, e.dni, e.ciudadResidencia, e.lu) " +
                            "FROM Estudiante e " +
                            "ORDER BY e.nombres"
                    , EstudianteDTO.class).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener estudiantes ordenados por nombre! " + e.getMessage());
            throw e;
        }
    }

}
