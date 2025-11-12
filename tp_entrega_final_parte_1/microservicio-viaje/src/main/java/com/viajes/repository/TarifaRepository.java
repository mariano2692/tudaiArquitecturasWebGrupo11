package com.viajes.repository;

import com.viajes.entity.Tarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query("SELECT t FROM Tarifa t WHERE t.activa = true AND t.fechaInicioVigencia <= :fecha ORDER BY t.fechaInicioVigencia DESC")
    Optional<Tarifa> findTarifaVigenteEnFecha(LocalDate fecha);

    @Query("SELECT t FROM Tarifa t WHERE t.activa = true")
    Optional<Tarifa> findTarifaActiva();
}
