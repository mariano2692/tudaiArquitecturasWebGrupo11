package org.example;

import dtos.CarreraConCantInscriptosDTO;
import dtos.InscripcionDTO;
import helpers.CSVreader;
import helpers.DatabaseLoader;
import dtos.EstudianteDTO;
import factories.RepositoryFactory;
import repositories.interfaces.RepositoryEstudiante;
import repositories.interfaces.RepositoryInscripcion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        CSVreader reader = new CSVreader();

        try {
            // Crear el factory una sola vez
            RepositoryFactory mySqlFactory = RepositoryFactory.getDAOFactory(1);
            
            // Cargar datos usando el mismo factory
            DatabaseLoader.cargarDatos(reader, mySqlFactory);
            
            // Usar el mismo factory para obtener el repositorio
            RepositoryEstudiante estudianteRepository = mySqlFactory.getEstudianteRepository();

            // d) Recuperar un estudiante por número de libreta universitaria
            System.out.println("\n--- Buscando estudiante por LU ---");
            EstudianteDTO estudiantePorLu = estudianteRepository.selectByLu(34978L);
            if (estudiantePorLu != null) {
                System.out.println("Estudiante encontrado: " + estudiantePorLu);
            } else {
                System.out.println("No se encontró estudiante con LU 34978");
            }
            
            // e) Recuperar todos los estudiantes por género
            System.out.println("\n--- Buscando estudiantes por género 'Male' ---");
            List<EstudianteDTO> estudiantesMasculinos = estudianteRepository.selectByGenero("Male");
            System.out.println("Estudiantes masculinos encontrados: " + estudiantesMasculinos.size());
            for (int i = 0; i < Math.min(3, estudiantesMasculinos.size()); i++) {
                System.out.println("- " + estudiantesMasculinos.get(i));
            }
            if (estudiantesMasculinos.size() > 3) {
                System.out.println("... y " + (estudiantesMasculinos.size() - 3) + " más");
            }
            
            System.out.println("\n--- Buscando estudiantes por género 'Polygender' ---");
            List<EstudianteDTO> estudiantesPolygender = estudianteRepository.selectByGenero("Polygender");
            System.out.println("Estudiantes polygender encontrados: " + estudiantesPolygender.size());
            for (EstudianteDTO estudiante : estudiantesPolygender) {
                System.out.println("- " + estudiante);
            }


            //2c recupera los estudiantes ordenados por nombre

            List<EstudianteDTO> estudiantesOrdenadosPorNombre = estudianteRepository.obtenerEstudiantesOrdenadosPorNombre();

            System.out.println("===============================================================================================================================" +
                    " Estudiantes ordenados por nombre " +
                    "===============================================================================================================================");

            for(EstudianteDTO estudianteDTO : estudiantesOrdenadosPorNombre) {
                System.out.println(estudianteDTO);
            }

            System.out.println();

            // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos

            RepositoryInscripcion repositoryInscripcion = mySqlFactory.getInscripcionRepository();

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

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}