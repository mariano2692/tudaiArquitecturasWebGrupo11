package org.example;

import dtos.CarreraDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import dtos.CarreraConCantInscriptosDTO;
import helpers.CSVreader;
import helpers.DatabaseLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryInscripcion;
import repositories.interfaces.RepositoryEstudiante;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        CSVreader reader = new CSVreader();

        // Crear EntityManagerFactory
        EntityManagerFactory emf = JpaMySqlRepositoryFactory.getEntityManagerFactory();

        // --- EM compartido para cargar datos ---
        EntityManager emCarga = emf.createEntityManager();

        try {
            DatabaseLoader.cargarDatos(reader,  emCarga);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            emCarga.close();
        }


        // --- NUEVO EM PARA CONSULTAS ---
        EntityManager emConsulta = emf.createEntityManager();
        RepositoryFactory factory = RepositoryFactory.getDAOFactory(RepositoryFactory.MYSQL_JDBC);
        RepositoryInscripcion repositoryInscripcion = factory.getInscripcionRepository(emConsulta);
        RepositoryEstudiante repositoryEstudiante = factory.getEstudianteRepository(emConsulta);



        // d) Recuperar un estudiante por número de libreta universitaria
        System.out.println("\n--- Buscando estudiante por LU ---");
        EstudianteDTO estudiantePorLu = repositoryEstudiante.selectByLu(34978L);
        if (estudiantePorLu != null) {
            System.out.println("Estudiante encontrado: " + estudiantePorLu);
        } else {
            System.out.println("No se encontró estudiante con LU 34978");
        }

        // e) Recuperar todos los estudiantes por género
        System.out.println("\n--- Buscando estudiantes por género 'Male' ---");
        List<EstudianteDTO> estudiantesMasculinos = repositoryEstudiante.selectByGenero("Male");
        System.out.println("Estudiantes masculinos encontrados: " + estudiantesMasculinos.size());
        for (int i = 0; i < Math.min(3, estudiantesMasculinos.size()); i++) {
            System.out.println("- " + estudiantesMasculinos.get(i));
        }
        if (estudiantesMasculinos.size() > 3) {
            System.out.println("... y " + (estudiantesMasculinos.size() - 3) + " más");
        }

        System.out.println("\n--- Buscando estudiantes por género 'Polygender' ---");
        List<EstudianteDTO> estudiantesPolygender = repositoryEstudiante.selectByGenero("Polygender");
        System.out.println("Estudiantes polygender encontrados: " + estudiantesPolygender.size());
        for (EstudianteDTO estudiante : estudiantesPolygender) {
            System.out.println("- " + estudiante);
        }


        //2c recupera los estudiantes ordenados por nombre

        List<EstudianteDTO> estudiantesOrdenadosPorNombre = repositoryEstudiante.obtenerEstudiantesOrdenadosPorNombre();

        System.out.println("===============================================================================================================================" +
                " Estudiantes ordenados por nombre " +
                "===============================================================================================================================");

        for(EstudianteDTO estudianteDTO : estudiantesOrdenadosPorNombre) {
            System.out.println(estudianteDTO);
        }

        System.out.println();

        // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos

        //RepositoryInscripcion repositoryInscripcion = mySqlFactory.getInscripcionRepository();

        List<CarreraConCantInscriptosDTO> listaCarrerasConCantInscriptos = repositoryInscripcion.recuperarCarrerasOrdenadasPorCantidadInscriptos();


        System.out.println("=================================================================");
        System.out.println("Carreras ordenadas por cantidad de inscriptos:");
        System.out.println("=================================================================");

        for (CarreraConCantInscriptosDTO c : listaCarrerasConCantInscriptos) {
            System.out.println(c);
        }

        System.out.println("=================================================================");
        System.out.println("Total de carreras: " + listaCarrerasConCantInscriptos.size());
        System.out.println("=================================================================");

// g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
        // --- PARÁMETROS DE EJEMPLO ---
        String carrera = "TUDAI";
        String ciudad = "Rauch";

        // --- CONSULTA ---
        List<InscripcionDTO> resultados = repositoryInscripcion.studentsByCareerAndCity(carrera, ciudad);

        // --- RESULTADOS ---
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron estudiantes para la carrera " + carrera + " en " + ciudad);
        } else {
            System.out.println("Estudiantes inscriptos en " + carrera + " desde " + ciudad + ":");
            resultados.forEach(System.out::println);
        }

        //3) Generar un reporte de las carreras, que para cada carrera incluya información de los
        //inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y presentar
        //los años de manera cronológica.
        RepositoryCarrera repositoryCarrera = factory.getCarreraRepository(emConsulta);
        List<CarreraDTO> reporte = repositoryCarrera.generarReporteCarreras();

        System.out.println("=== Reporte de Carreras ===");
        for (CarreraDTO c : reporte) {
            System.out.println("Carrera: " + c.getNombre());
            c.getResumenPorAnio().forEach(System.out::println); // solo resumen por año
            System.out.println("----------------------------");
        }



        // --- NUEVAS FUNCIONALIDADES ---
        
        // a) Dar de alta un estudiante
        System.out.println("\n=== DAR DE ALTA UN ESTUDIANTE ===");
        EstudianteDTO nuevoEstudiante = repositoryEstudiante.darDeAltaEstudiante(
                12345678,  // DNI
                "Juan",     // Nombres
                "Pérez",    // Apellido
                20,         // Edad
                "Male",     // Género
                "Tandil",   // Ciudad de residencia
                50000L      // LU (Libreta Universitaria)
        );
        
        if (nuevoEstudiante != null) {
            System.out.println("Estudiante dado de alta exitosamente: " + nuevoEstudiante);
        } else {
            System.out.println("No se pudo dar de alta el estudiante (posiblemente ya existe)");
        }
        
        // b) Matricular un estudiante en una carrera
        System.out.println("\n=== MATRICULAR ESTUDIANTE EN CARRERA ===");
        
        // Primero intentamos matricular al estudiante que acabamos de crear
        InscripcionDTO nuevaInscripcion = repositoryInscripcion.matricularEstudianteEnCarrera(
                12345678,  // DNI del estudiante
                1          // ID de la carrera TUDAI
        );
        
        if (nuevaInscripcion != null) {
            System.out.println("Estudiante matriculado exitosamente: " + nuevaInscripcion);
        } else {
            System.out.println("No se pudo matricular el estudiante");
        }
        
        // Intentamos matricular a un estudiante existente en otra carrera
        System.out.println("\n--- Matriculando estudiante existente en otra carrera ---");
        InscripcionDTO inscripcionExistente = repositoryInscripcion.matricularEstudianteEnCarrera(
                34978,     // DNI de un estudiante que sabemos que existe
                2          // ID de la carrera Abogacía
        );
        
        if (inscripcionExistente != null) {
            System.out.println("Estudiante existente matriculado exitosamente: " + inscripcionExistente);
        } else {
            System.out.println("No se pudo matricular el estudiante existente");
        }

        // --- CERRAR EM Y EMF ---
        emConsulta.close();
        emf.close();



    }
}