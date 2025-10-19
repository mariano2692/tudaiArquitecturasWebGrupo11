package org.example.tp_03_integrador.repositories;

import org.example.tp_03_integrador.entities.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarreraRepository extends JpaRepository<Carrera,Integer> {
}
