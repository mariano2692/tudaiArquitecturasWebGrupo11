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
import repositories.interfaces.RepositoryInscripcion;

import java.time.LocalDate;
import java.util.List;

public class JpaInscripcionRepository implements RepositoryInscripcion {
    private EntityManager em;

    public JpaInscripcionRepository(EntityManager em) {
        this.em = em;
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


    /**
     * Obtiene una lista de estudiantes matriculados en una carrera específica
     * y que residen en una ciudad determinada.
     *
     * <p>El método construye un JPQL que selecciona inscripciones junto con
     * información del estudiante y de la carrera, y lo transforma en objetos
     * {@link InscripcionDTO} y {@link EstudianteDTO}.
     *
     * <p>Los resultados se ordenan alfabéticamente por apellido y nombre del estudiante.
     *
     * @param carrera El nombre de la carrera.
     * @param ciudad  La ciudad de residencia de los estudiantes.
     * @return Lista de {@link InscripcionDTO} que cumplen con los criterios.
     */
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

    /**
     * Verifica si un estudiante ya está inscrito en una carrera para un año específico.
     *
     * <p>El método consulta la base de datos usando JPQL contando cuántas
     * inscripciones existen que coincidan con el estudiante, la carrera y
     * el año de inscripción proporcionados.
     *
     * @param estudiante      El estudiante a verificar.
     * @param carrera         La carrera en la que se quiere verificar la inscripción.
     * @param anioInscripcion El año de inscripción a verificar.
     * @return {@code true} si existe al menos una inscripción que coincida,
     *         {@code false} en caso contrario.
     */
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

    @Override
    public InscripcionDTO matricularEstudianteEnCarrera(int dniEstudiante, int idCarrera) {
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            Estudiante estudiante = em.find(Estudiante.class, dniEstudiante);
            if (estudiante == null) {
                System.out.println("No se encontró estudiante con DNI: " + dniEstudiante);
                transaction.rollback();
                return null;
            }
            // Buscar la carrera por ID
            Carrera carrera = em.find(Carrera.class, idCarrera);
            if (carrera == null) {
                System.out.println("No se encontró carrera con ID: " + idCarrera);
                transaction.rollback();
                return null;
            }
            // Verificar si ya está inscrito en esta carrera para el año actual
            LocalDate anioActual = LocalDate.now();
            if (existeInscripcion(estudiante, carrera, anioActual)) {
                System.out.println("El estudiante ya está inscrito en esta carrera para el año actual");
                transaction.rollback();
                return null;
            }
            
            // Crear nueva inscripción
            Inscripcion nuevaInscripcion = new Inscripcion(carrera, estudiante);
            em.persist(nuevaInscripcion);
            
            // Actualizar las relaciones bidireccionales
            estudiante.addInscripcion(nuevaInscripcion);
            carrera.addInscripcion(nuevaInscripcion);
            
            transaction.commit();
            
            System.out.println("Estudiante matriculado exitosamente en la carrera: " + nuevaInscripcion);
            return convertirAInscripcionDTO(nuevaInscripcion);
            
        } catch (PersistenceException e) {
            if (transaction.isActive()) transaction.rollback();
            System.out.println("Error al matricular estudiante: " + e.getMessage());
            throw e;
        }
    }
    
    // Método helper para convertir Inscripcion a InscripcionDTO
    private InscripcionDTO convertirAInscripcionDTO(Inscripcion inscripcion) {
        return new InscripcionDTO(
                inscripcion.getAntiguedad(),
                inscripcion.getAnioInscripcion(),
                inscripcion.getAnioEgreso(),
                inscripcion.isGraduado(),
                inscripcion.getCarrera().getNombre(),
                new EstudianteDTO(
                        inscripcion.getEstudiante().getNombres(),
                        inscripcion.getEstudiante().getApellido(),
                        inscripcion.getEstudiante().getEdad(),
                        inscripcion.getEstudiante().getGenero(),
                        inscripcion.getEstudiante().getDni(),
                        inscripcion.getEstudiante().getCiudadResidencia(),
                        inscripcion.getEstudiante().getLu()
                )
        );
    }
}
