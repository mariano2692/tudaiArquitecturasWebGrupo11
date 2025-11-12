package com.viajes.service;

import com.viajes.entity.Tarifa;
import com.viajes.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
public class TarifaService {

    @Autowired
    private TarifaRepository tarifaRepository;

    @Transactional
    public Tarifa ajustarPrecios(Tarifa nuevaTarifa) {
        // Si la fecha de inicio de vigencia es hoy o antes, desactivar la tarifa actual
        if (nuevaTarifa.getFechaInicioVigencia().isBefore(LocalDate.now()) ||
            nuevaTarifa.getFechaInicioVigencia().isEqual(LocalDate.now())) {
            // Desactivar todas las tarifas actuales
            List<Tarifa> tarifasActivas = tarifaRepository.findAll().stream()
                    .filter(Tarifa::getActiva)
                    .toList();

            for (Tarifa tarifa : tarifasActivas) {
                tarifa.setActiva(false);
                tarifaRepository.save(tarifa);
            }

            // Activar la nueva tarifa
            nuevaTarifa.setActiva(true);
        } else {
            // Si la fecha es futura, guardar como inactiva
            nuevaTarifa.setActiva(false);
        }

        return tarifaRepository.save(nuevaTarifa);
    }

    @Transactional(readOnly = true)
    public Tarifa getTarifaVigente(LocalDate fecha) {
        return tarifaRepository.findTarifaVigenteEnFecha(fecha)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public Tarifa getTarifaActual() {
        return tarifaRepository.findTarifaActiva()
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public List<Tarifa> getAllTarifas() {
        return tarifaRepository.findAll();
    }

    @Transactional
    public void activarTarifasProgramadas() {
        LocalDate hoy = LocalDate.now();
        List<Tarifa> tarifasProgramadas = tarifaRepository.findAll().stream()
                .filter(t -> !t.getActiva() &&
                        (t.getFechaInicioVigencia().isEqual(hoy) ||
                         t.getFechaInicioVigencia().isBefore(hoy)))
                .toList();

        if (!tarifasProgramadas.isEmpty()) {
            // Desactivar tarifas actuales
            List<Tarifa> tarifasActivas = tarifaRepository.findAll().stream()
                    .filter(Tarifa::getActiva)
                    .toList();

            for (Tarifa tarifa : tarifasActivas) {
                tarifa.setActiva(false);
                tarifaRepository.save(tarifa);
            }

            // Activar la tarifa programada más reciente
            Tarifa tarifaMasReciente = tarifasProgramadas.stream()
                    .max((t1, t2) -> t1.getFechaInicioVigencia().compareTo(t2.getFechaInicioVigencia()))
                    .orElse(null);

            if (tarifaMasReciente != null) {
                tarifaMasReciente.setActiva(true);
                tarifaRepository.save(tarifaMasReciente);
            }
        }
    }
}
