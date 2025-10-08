package org.example;

import dtos.CarreraConCantInscriptosDTO;
import dtos.EstudianteDTO;
import dtos.InscripcionDTO;
import factories.RepositoryFactory;
import helpers.CSVreader;
import helpers.DatabaseLoader;
import repositories.JpaEstudianteRepository;
import repositories.interfaces.RepositoryCarrera;
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

        try {

            // Crear el factory una sola vez
            RepositoryFactory mySqlFactory = RepositoryFactory.getDAOFactory(1);

            // Cargar datos usando el mismo factory
            DatabaseLoader.cargarDatos(reader, mySqlFactory);

            // Usar el mismo factory para obtener el repositorio
            RepositoryEstudiante estudianteRepository = mySqlFactory.getEstudianteRepository();

//
//            List<EstudianteDTO> estudiantesOrdenadosPorNombre = estudianteRepository.obtenerEstudiantesOrdenadosPorNombre();
//
//            System.out.println("===============================================================================================================================" +
//                    " Estudiantes ordenados por nombre " +
//                    "===============================================================================================================================");
//
//            for(EstudianteDTO estudianteDTO : estudiantesOrdenadosPorNombre) {
//                System.out.println(estudianteDTO);
//            }
//
//            System.out.println();

            // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos

            RepositoryInscripcion repositoryInscripcion = mySqlFactory.getInscripcionRepository();

            List<CarreraConCantInscriptosDTO> listaCarrerasConCantInscriptos = repositoryInscripcion.recuperarCarrerasOrdenadasPorCantidadInscriptos();



            List<InscripcionDTO> inscripciones = repositoryInscripcion.selectAll();
            // Mostrar los resultados en consola
            System.out.println("=================================================================");
            System.out.println("Lista de inscripciones:");
            System.out.println("=================================================================");

            for (InscripcionDTO i : inscripciones) {
                System.out.println(i);
            }

            System.out.println("=================================================================");
            System.out.println("Total de inscripciones: " + inscripciones.size());
            System.out.println("=================================================================");


//            System.out.println("===============================================================================================================================" +
//                    " Carreras obtenidas con estudiantes inscriptos y ordenadas por la cantidad de ellos " +
//                    "===============================================================================================================================");
//
//            for (CarreraConCantInscriptosDTO carrera : listaCarrerasConCantInscriptos) {
//                System.out.println(carrera);
//            }
//
//            System.out.println();



        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}