package org.example.tp_03_integrador.services;

import org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO;
import org.example.tp_03_integrador.repositories.CarreraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CarreraService {
    @Autowired
    private CarreraRepository carreraRepository;

    @Transactional(readOnly = true)
    public List<CarreraConCantInscriptosDTO> getCarrerasOrdenadasPorInscriptos(){
        return carreraRepository.getCarrerasOrdenadasPorInscriptos();
    }
}
