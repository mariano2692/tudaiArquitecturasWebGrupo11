package org.example;

import dtos.CarreraDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import entities.Estudiante;
import factories.JpaMySqlRepositoryFactory;
import factories.RepositoryFactory;
import helpers.CSVreader;
import helpers.DatabaseLoader;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import repositories.JpaInscripcionRepository;
import repositories.interfaces.RepositoryCarrera;
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


        // --- CERRAR EM Y EMF ---
        emConsulta.close();
        emf.close();



    }
}