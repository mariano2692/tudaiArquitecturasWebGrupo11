package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.entities.Carrera;
import org.example.tp_03_integrador.entities.Estudiante;
import org.example.tp_03_integrador.entities.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera,Integer> {

    List<EstudianteCarrera> findByEstudianteAndCarrera(Estudiante estudiante, Carrera carrera);
}
