package repositories;

import dtos.CarreraConCantInscriptosDTO;
import dtos.InscripcionDTO;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
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
    public void save(Inscripcion inscripcion) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        if (inscripcion.getId() == 0){
            try {
                em.persist(inscripcion);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al insertar inscripcion! " + e.getMessage());
                throw e;
            }
        } else { // Si la inscripción ya existe, hace update
            try {
                em.merge(inscripcion);
                transaction.commit();
            } catch (PersistenceException e) {
                transaction.rollback();
                System.out.println("Error al actualizar inscripcion! " + e.getMessage());
                throw e;
            }
        }
    }

    @Override
    public InscripcionDTO selectById(int id) {
        return null;
    }

    @Override
    public List<InscripcionDTO> selectAll() {
        try {
            return em.createQuery(
                    "SELECT new dtos.InscripcionDTO(" +
                            "i.antiguedad, " +
                            "i.anioInscripcion, " +
                            "i.anioEgreso, " +
                            "i.graduado, " +
                            "c.nombre, " +
                            "e.lu) " +
                            "FROM Inscripcion i " +
                            "JOIN i.carrera c " +
                            "JOIN i.estudiante e",
                    InscripcionDTO.class
            ).getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener inscripciones! " + e.getMessage());
            throw e;
        }
    }



    @Override
    public boolean delete(int id) {
        return false;
    }

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos
    public List<CarreraConCantInscriptosDTO> recuperarCarrerasOrdenadasPorCantidadInscriptos() {
        try {
            return em.createQuery(
                            "SELECT new dtos.CarreraConCantInscriptosDTO(i.carrera.nombre, COUNT(i)) " +
                                    "FROM Inscripcion i " +
                                    "GROUP BY i.carrera.nombre " +
                                    "HAVING COUNT(i) > 0 " + // Solo incluir carreras con al menos un inscripto
                                    "ORDER BY COUNT(i) DESC", CarreraConCantInscriptosDTO.class)
                    .getResultList();
        } catch (PersistenceException e) {
            System.out.println("Error al obtener carreras con inscriptos! " + e.getMessage());
            throw e;
        }
    }

}
