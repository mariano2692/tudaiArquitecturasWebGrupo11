package repositories;

import dtos.CarreraDTO;
import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import repositories.interfaces.RepositoryCarrera;

import java.util.List;
import java.util.stream.Collectors;

public class JpaCarreraRepository implements RepositoryCarrera {
    private EntityManager em;

    public JpaCarreraRepository(EntityManager em) {this.em = em;}

    // Método para cerrar el EntityManager
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    @Override
    public void save(Carrera carrera) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(carrera); // merge inserta o actualiza
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Error al insertar/actualizar carrera! " + e.getMessage());
            throw e;
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

    @Override
    public void matricularEstudianteEnCarrera(String nombreCarrera, String nombreEstudiante, String apellidoEstudiante) {
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        Estudiante estudiante = em.createQuery("SELECT e FROM Estudiante e WHERE e.nombres = :nombreEstudiante AND e.apellido = :apellidoEstudiante", Estudiante.class)
                .setParameter("nombreEstudiante", nombreEstudiante)
                .setParameter("apellidoEstudiante", apellidoEstudiante)
                .getSingleResult();

        Carrera carrera = em.createQuery("SELECT c FROM Carrera c WHERE c.nombre = :nombreCarrera", Carrera.class)
                .setParameter("nombreCarrera", nombreCarrera)
                .getSingleResult();


        Inscripcion inscripcion = new Inscripcion(carrera, estudiante);
        em.persist(inscripcion);
        System.out.println("Estudiante inscipto!");
        transaction.commit();
    }

    /**
     * Genera un reporte de las carreras con cantidad de inscriptos y egresados por año.
     * Ordena las carreras alfabéticamente y los años cronológicamente.
     */
    public List<CarreraDTO> generarReporteCarreras() {
        List<Carrera> carreras = em.createQuery(
                "SELECT DISTINCT c FROM Carrera c LEFT JOIN FETCH c.inscripciones i ORDER BY c.nombre ASC",
                Carrera.class
        ).getResultList();

        return carreras.stream()
                .map(carrera -> {
                    CarreraDTO dto = new CarreraDTO(carrera.getNombre());
                    carrera.getInscripciones().forEach(dto::addInscripcion);
                    return dto;
                })
                .collect(Collectors.toList());
    }

}
