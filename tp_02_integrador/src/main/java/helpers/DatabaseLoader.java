package helpers;

import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.RepositoryFactory;
import jakarta.persistence.EntityManager;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader {
    public static void cargarDatos(CSVreader reader, EntityManager em) throws SQLException, IOException {
        // Obtener listas desde los CSVs
        List<Estudiante> estudiantes = reader.leerArchivoEstudiantes();
        List<Carrera> carreras = reader.leerArchivoCarreras();

        RepositoryFactory factory = RepositoryFactory.getDAOFactory(RepositoryFactory.MYSQL_JDBC);

        // Usar el mismo EM para todos los repositorios
        RepositoryEstudiante repoEstudiante = factory.getEstudianteRepository(em);
        RepositoryCarrera repoCarrera = factory.getCarreraRepository(em);
        RepositoryInscripcion repoInscripcion = factory.getInscripcionRepository(em);

        // Guardar entidades
        cargarListaEnBaseDeDatosEstudiantes(estudiantes, repoEstudiante);
        cargarListaEnBaseDeDatosCarreras(carreras, repoCarrera);

        List<Inscripcion> inscripciones = reader.leerArchivoEstudianteCarrera(carreras, estudiantes);
        cargarListaEnBaseDeDatosInscripciones(inscripciones, repoInscripcion);
    }

    // Método genérico para cargar entidades con cualquier DAO
    public static void cargarListaEnBaseDeDatosCarreras(List<Carrera> lista, RepositoryCarrera repo) {
        for (Carrera entidad : lista) {
            repo.save(entidad);
        }
    }

    public static void cargarListaEnBaseDeDatosEstudiantes(List<Estudiante> lista, RepositoryEstudiante repo) {
        for (Estudiante entidad : lista) {
            repo.save(entidad);
        }
    }

    public static void cargarListaEnBaseDeDatosInscripciones(List<Inscripcion> lista, RepositoryInscripcion repo) {
        for (Inscripcion entidad : lista) {
            System.out.println(entidad);

            if (entidad.getCarrera() == null || entidad.getEstudiante() == null) {
                continue; // la salteo
            }

            // Verifico si ya existe por estudiante + carrera + añoInscripcion
            if (!repo.existeInscripcion(entidad.getEstudiante(), entidad.getCarrera(), entidad.getAnioInscripcion())) {
                repo.save(entidad);
            } else {
                System.out.println("Inscripción ya existente, se omite: " + entidad);
            }
        }
    }

}
