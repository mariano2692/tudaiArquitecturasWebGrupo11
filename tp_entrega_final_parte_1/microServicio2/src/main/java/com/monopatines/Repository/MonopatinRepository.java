package com.monopatines.Repository;


import com.monopatines.entities.Monopatin;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MonopatinRepository extends MongoRepository <Monopatin, Integer> {

    Optional<Monopatin> findById(int monopatinId);

    long countByEstado(String disponible);
}
