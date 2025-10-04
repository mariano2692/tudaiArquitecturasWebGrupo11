package helpers;

import entities.Carrera;
import entities.Estudiante;
import entities.Inscripcion;

import java.sql.SQLException;
import java.util.List;

public class DatabaseLoader {

    // Método para cargar todos los datos de los CSVs a la base de datos
    public static void cargarDatos(CSVreader reader) throws SQLException {
        // Obtener listas desde los CSVs
        List<Carrera> carreras = reader.leerArchivoCarreras();
        List<Estudiante> estudiantes = reader.leerArchivoEstudiantes();
        List<Inscripcion> inscripciones = reader.leerArchivoInscripciones(carreras, estudiantes);

        // Obtener los DAOs de las entidades
        DBFactory dbF = DBFactory.getFactory(1); // 1: Derby, 2: MySQL, etc.
        DAO<Carrera> carreraDAO = dbF.getCarreraDAO();
        DAO<Estudiante> estudianteDAO = dbF.getEstudianteDAO();
        DAO<Inscripcion> inscripcionDAO = dbF.getInscripcionDAO();

        // Eliminar tablas si existen (importante seguir el orden por claves foráneas)
        inscripcionDAO.dropTable();
        estudianteDAO.dropTable();
        carreraDAO.dropTable();

        // Crear tablas en orden correcto
        carreraDAO.createTable();
        estudianteDAO.createTable();
        inscripcionDAO.createTable();

        // Insertar datos (también respetando dependencias)
        cargarListaEnBaseDeDatos(carreras, carreraDAO);
        cargarListaEnBaseDeDatos(estudiantes, estudianteDAO);
        cargarListaEnBaseDeDatos(inscripciones, inscripcionDAO);
    }

    // Método genérico para cargar entidades con cualquier DAO
    public static <T> void cargarListaEnBaseDeDatos(List<T> lista, DAO<T> dao) throws SQLException {
        for (T entidad : lista) {
            dao.insert(entidad);
        }
    }
}
