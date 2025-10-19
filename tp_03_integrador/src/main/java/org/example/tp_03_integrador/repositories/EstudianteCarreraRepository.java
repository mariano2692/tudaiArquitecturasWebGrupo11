package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.entities.EstudianteCarrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteCarreraRepository extends JpaRepository<EstudianteCarrera,Integer> {
}
