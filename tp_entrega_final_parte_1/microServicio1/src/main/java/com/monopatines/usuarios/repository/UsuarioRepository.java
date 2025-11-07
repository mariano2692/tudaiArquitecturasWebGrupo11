package com.monopatines.usuarios.repository;

import com.monopatines.usuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByEmail(String email);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM Usuario u LEFT JOIN FETCH u.cuentas WHERE u.id = :id")
    Optional<Usuario> findByIdWithCuentas(@Param("id") Long id);
}
