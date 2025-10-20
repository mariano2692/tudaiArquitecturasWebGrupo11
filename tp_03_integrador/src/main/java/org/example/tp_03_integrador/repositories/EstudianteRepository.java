package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Integer> {

    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple. -> Por APELLIDO
    @Query("SELECT e FROM Estudiante e ORDER BY e.apellido ASC")
    List<Estudiante> obtenerEstudiantesOrdenadosPorApellidoASC();

    Optional<Estudiante> findByDni(Integer dni);


    List<Estudiante> findByLu(Integer lu);


    List<Estudiante> getAllEstudiantesByGenero(String genero);







}
