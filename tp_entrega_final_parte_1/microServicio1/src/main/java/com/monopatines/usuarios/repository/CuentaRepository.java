package com.monopatines.usuarios.repository;

import com.monopatines.usuarios.entities.Cuenta;
import com.monopatines.usuarios.entities.TipoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CuentaRepository extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByTipo(TipoCuenta tipo);

    List<Cuenta> findByActiva(Boolean activa);

    @Query("SELECT c FROM Cuenta c LEFT JOIN FETCH c.usuarios WHERE c.id = :id")
    Optional<Cuenta> findByIdWithUsuarios(@Param("id") Long id);

    @Query("SELECT c FROM Cuenta c WHERE c.cuentaMercadoPago = :cuentaMercadoPago")
    List<Cuenta> findByCuentaMercadoPago(@Param("cuentaMercadoPago") String cuentaMercadoPago);
}
