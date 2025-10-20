package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO;
import org.example.tp_03_integrador.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {
    // 2f) Recuperar las carreras con estudiantes inscriptos, y ordenar por cantidad de inscriptos.
    @Query("SELECT new org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO(" +
            "c.nombre, COUNT(ec)) " +
            "FROM Carrera c " +
            "JOIN c.inscripciones ec " +
            "GROUP BY c.nombre " +
            "ORDER BY COUNT(ec) DESC"
    )
    List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos();
}
