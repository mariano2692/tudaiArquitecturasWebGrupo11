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
        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e", Estudiante.class)
                    .getResultList();
            return estudiantes.stream()
                    .map(this::convertirAEstudianteDTO)
                    .toList();
        } catch (Exception e) {
            System.out.println("Error al obtener todos los estudiantes: " + e.getMessage());
            return List.of();
        }
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public EstudianteDTO selectByLu(Long lu) {
        try {
            Estudiante estudiante = em.createQuery("SELECT e FROM Estudiante e WHERE e.lu = :lu", Estudiante.class)
                    .setParameter("lu", lu)
                    .getSingleResult();
            return convertirAEstudianteDTO(estudiante);
        } catch (Exception e) {
            System.out.println("No se encontró estudiante con LU: " + lu);
            return null;
        }
    }

    @Override
    public List<EstudianteDTO> selectByGenero(String genero) {
        try {
            List<Estudiante> estudiantes = em.createQuery("SELECT e FROM Estudiante e WHERE e.genero = :genero", Estudiante.class)
                    .setParameter("genero", genero)
                    .getResultList();
            return estudiantes.stream()
                    .map(this::convertirAEstudianteDTO)
                    .toList();
        } catch (Exception e) {
            System.out.println("Error al buscar estudiantes por género: " + e.getMessage());
            return List.of();
        }
    }

    // Método helper para convertir Estudiante a EstudianteDTO
    private EstudianteDTO convertirAEstudianteDTO(Estudiante estudiante) {
        return new EstudianteDTO(
                estudiante.getNombres(),
                estudiante.getApellido(),
                estudiante.getEdad(),
                estudiante.getGenero(),
                estudiante.getDni(),
                estudiante.getCiudadResidencia(),
                estudiante.getLu()
        );
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
