package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.models.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante,Integer> {
}
