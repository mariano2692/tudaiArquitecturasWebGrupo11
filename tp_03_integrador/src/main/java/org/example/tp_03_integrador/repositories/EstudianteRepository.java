package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.entities.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Integer> {
    Optional<Estudiante> findByDni(Integer dni);


    List<Estudiante> findByLu(Integer lu);


    List<Estudiante> getAllEstudiantesByGenero(String genero);

}
