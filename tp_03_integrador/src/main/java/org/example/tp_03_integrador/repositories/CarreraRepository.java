package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO;
import org.example.tp_03_integrador.dtos.ReporteCarreraDTO;
import org.example.tp_03_integrador.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {

    Optional<Carrera> findCarreraById(int id);

    Carrera findById(int id);

    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT new org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO(" +
            "c.nombre, COUNT(ec)) " +
            "FROM Carrera c " +
            "JOIN c.inscripciones ec " +
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(ec) DESC"
    )
    List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos();


    // 2h) Generar un reporte de las carreras, que para cada carrera incluya información de los
    // inscriptos y egresados por año. Se deben ordenar las carreras alfabéticamente, y
    // presentar los años de manera cronológica.
    @Query("""
        SELECT new org.example.tp_03_integrador.dtos.ReporteCarreraDTO(
            c.nombre,
            ec.anioInscripcion,
            ec.anioEgreso,
            COUNT(CASE WHEN ec.anioEgreso IS NULL THEN 1 END),
            COUNT(CASE WHEN ec.anioEgreso IS NOT NULL THEN 1 END),
            MIN(e.lu)
        )
        FROM EstudianteCarrera ec
        JOIN ec.carrera c
        JOIN ec.estudiante e
        GROUP BY c.nombre, ec.anioInscripcion, ec.anioEgreso
        ORDER BY c.nombre ASC, ec.anioInscripcion ASC
    """)
    List<ReporteCarreraDTO> getReporteCarreras();

}
