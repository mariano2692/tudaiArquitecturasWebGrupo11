package repositories;

import dtos.InscripcionDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import repositories.interfaces.RepositoryInscripcion;

import java.time.LocalDate;
import java.util.List;

public class JpaInscripcionRepository implements RepositoryInscripcion {
    private EntityManager em;
    //private static JpaInscripcionRepository instance;
    public JpaInscripcionRepository(EntityManager em) {this.em = em;}
/*
    private JpaInscripcionRepository(EntityManager em) {this.em = em;}

 */
/*
    public static JpaInscripcionRepository getInstance(EntityManager em) {
        if(instance == null)
            instance = new JpaInscripcionRepository(em);
        return instance;
    }

 */

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Inscripcion inscripcion) {

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(inscripcion); // usar persist, no merge
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Error al insertar inscripcion! " + e.getMessage());
            throw e;
        }
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

    // Punto 2)G)
    public List<InscripcionDTO> studentsByCareerAndCity(String carrera, String ciudad) {
        String jpql = """
        SELECT new dtos.InscripcionDTO(
            i.antiguedad,
            i.anioInscripcion,
            i.anioEgreso,
            i.graduado,
            c.nombre,
            new dtos.EstudianteDTO(
                s.nombres,
                s.apellido,
                s.edad,
                s.genero,
                s.dni,
                s.ciudadResidencia,
                s.lu
            )
        )
        FROM Inscripcion i
        JOIN i.estudiante s
        JOIN i.carrera c
        WHERE c.nombre = :carrera
          AND s.ciudadResidencia = :ciudad
        ORDER BY s.apellido, s.nombres
    """;

        return em.createQuery(jpql, InscripcionDTO.class)
                .setParameter("carrera", carrera)
                .setParameter("ciudad", ciudad)
                .getResultList();
    }

    public boolean existeInscripcion(Estudiante estudiante, Carrera carrera, LocalDate anioInscripcion) {
        String jpql = "SELECT COUNT(i) FROM Inscripcion i " +
                "WHERE i.estudiante = :estudiante AND i.carrera = :carrera AND i.anioInscripcion = :anio";
        Long count = em.createQuery(jpql, Long.class)
                .setParameter("estudiante", estudiante)
                .setParameter("carrera", carrera)
                .setParameter("anio", anioInscripcion)
                .getSingleResult();
        return count > 0;
    }
}
