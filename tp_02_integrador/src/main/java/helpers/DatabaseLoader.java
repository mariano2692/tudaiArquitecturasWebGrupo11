package helpers;

import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;
import factories.RepositoryFactory;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader {

    // Método para cargar todos los datos de los CSVs a la base de datos
    public static void cargarDatos(CSVreader reader, RepositoryFactory factory) throws SQLException, IOException {
        // Obtener listas desde los CSVs
        List<Carrera> carreras = reader.leerArchivoCarreras();
        List<Estudiante> estudiantes = reader.leerArchivoEstudiantes();

        // Obtener los Repositorios de las entidades usando el factory pasado como parámetro
        RepositoryEstudiante jpaEstudianteRepository = factory.getEstudianteRepository();
        RepositoryCarrera jpaCarreraRepository = factory.getCarreraRepository();
        RepositoryInscripcion jpaInscripcionRepository = factory.getInscripcionRepository();

        // PRIMERO: Insertar carreras y estudiantes para que tengan IDs
        cargarListaEnBaseDeDatosCarreras(carreras, jpaCarreraRepository);
        cargarListaEnBaseDeDatosEstudiantes(estudiantes, jpaEstudianteRepository);

        // SEGUNDO: Ahora leer las inscripciones (las carreras y estudiantes ya tienen IDs)
        List<Inscripcion> inscripciones = reader.leerArchivoEstudianteCarrera(carreras, estudiantes);
        cargarListaEnBaseDeDatosInscripciones(inscripciones, jpaInscripcionRepository);
    }

    // Método original para mantener compatibilidad
    public static void cargarDatos(CSVreader reader) throws SQLException, IOException {
        RepositoryFactory mySqlFactory = RepositoryFactory.getDAOFactory(1);
        cargarDatos(reader, mySqlFactory);
    }

    // Método genérico para cargar entidades con cualquier DAO
    public static void cargarListaEnBaseDeDatosCarreras(List<Carrera> lista, RepositoryCarrera repo) {
        for (Carrera entidad : lista) {
            repo.save(entidad);
        }
        System.out.println("Carreras guardadas: " + lista.size());
    }

    public static void cargarListaEnBaseDeDatosEstudiantes(List<Estudiante> lista, RepositoryEstudiante repo) {
        for (Estudiante entidad : lista) {
            repo.save(entidad);
        }
    }

    public static void cargarListaEnBaseDeDatosInscripciones(List<Inscripcion> lista, RepositoryInscripcion repo) {
        int guardadas = 0;
        int saltadas = 0;
        for (Inscripcion entidad : lista) {
            if (entidad.getCarrera() == null || entidad.getEstudiante() == null) {
                saltadas++;
                continue; // la salteo
            }
            repo.save(entidad);
            guardadas++;
        }
        System.out.println("Inscripciones guardadas: " + guardadas + ", Saltadas: " + saltadas);
    }

}
