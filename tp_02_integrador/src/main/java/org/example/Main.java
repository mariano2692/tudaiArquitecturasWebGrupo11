package org.example;

import dtos.CarreraDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import entities.Estudiante;
import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import dtos.CarreraConCantInscriptosDTO;
import dtos.InscripcionDTO;
import helpers.CSVreader;
import helpers.DatabaseLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.RepositoryCarrera;
import repositories.interfaces.RepositoryInscripcion;
import dtos.EstudianteDTO;
import factories.RepositoryFactory;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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


        // --- NUEVO EM PARA CONSULTA ---
        EntityManager emConsulta = emf.createEntityManager();
        RepositoryFactory factory = RepositoryFactory.getDAOFactory(RepositoryFactory.MYSQL_JDBC);
        RepositoryInscripcion repositoryInscripcion = factory.getInscripcionRepository(emConsulta);
        RepositoryInscripcion repositoryEstudiante = factory.getInscripcionRepository(emConsulta);
        // --- PARÁMETROS DE EJEMPLO ---
        String carrera = "TUDAI";
        String ciudad = "Rauch";

        // --- EJECUTAR CONSULTA ---
        List<InscripcionDTO> resultados = repositoryInscripcion.studentsByCareerAndCity(carrera, ciudad);

        // --- MOSTRAR RESULTADOS ---
        if (resultados.isEmpty()) {
            System.out.println("No se encontraron estudiantes para la carrera " + carrera + " en " + ciudad);
        } else {
            System.out.println("Estudiantes inscriptos en " + carrera + " desde " + ciudad + ":");
            resultados.forEach(System.out::println);
        }

        RepositoryCarrera repositoryCarrera = factory.getCarreraRepository(emConsulta);
        List<CarreraDTO> reporte = repositoryCarrera.generarReporteCarreras();

        System.out.println("=== Reporte de Carreras ===");
        for (CarreraDTO c : reporte) {
            System.out.println("Carrera: " + c.getNombre());
            c.getResumenPorAnio().forEach(System.out::println); // solo resumen por año
            System.out.println("----------------------------");
        }

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





        // --- CERRAR EM Y EMF ---
        emConsulta.close();
        emf.close();



    }
}