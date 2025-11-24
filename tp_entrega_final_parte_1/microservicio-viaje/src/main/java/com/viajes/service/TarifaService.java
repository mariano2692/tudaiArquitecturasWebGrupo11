package com.viajes.service;

import com.viajes.entity.Tarifa;
import com.viajes.repository.TarifaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
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

    /**
     * Obtener tarifa por ID
     */
    public Tarifa getTarifaById(Long id) {
        return tarifaRepository.findById(id).orElse(null);
    }

    /**
     * Actualizar una tarifa existente
     * Solo se pueden modificar tarifas que aún no están activas
     */
    @Transactional
    public Tarifa updateTarifa(Long id, Tarifa tarifaActualizada) {
        Tarifa tarifaExistente = tarifaRepository.findById(id).orElse(null);

        if (tarifaExistente == null) {
            return null;
        }

        // Validar que no se pueda modificar una tarifa ya activa
        if (tarifaExistente.isActiva()) {
            throw new IllegalStateException("No se puede modificar una tarifa que ya está activa");
        }

        LocalDateTime ahora = LocalDateTime.now();
        if (tarifaActualizada.getFechaInicioVigencia().isBefore(ChronoLocalDate.from(ahora))) {
            throw new IllegalArgumentException("La fecha de inicio de vigencia debe ser futura");
        }

        // Actualizar campos
        tarifaExistente.setPrecioBase(tarifaActualizada.getPrecioBase());
        tarifaExistente.setPrecioPorKm(tarifaActualizada.getPrecioPorKm());
        tarifaExistente.setPrecioPorMinutoPausa(tarifaActualizada.getPrecioPorMinutoPausa());
        tarifaExistente.setFechaInicioVigencia(tarifaActualizada.getFechaInicioVigencia());

        return tarifaRepository.save(tarifaExistente);
    }

    /**
     * Eliminar una tarifa
     * Solo se pueden eliminar tarifas que no están activas y no tienen viajes asociados
     */
    @Transactional
    public boolean deleteTarifa(Long id) {
        Tarifa tarifa = tarifaRepository.findById(id).orElse(null);

        if (tarifa == null) {
            return false;
        }

        // Validar que no se pueda eliminar una tarifa activa
        if (tarifa.isActiva()) {
            throw new IllegalStateException("No se puede eliminar una tarifa activa");
        }

        // Opcional: Verificar que no tenga viajes asociados
        // if (viajeRepository.countByTarifa(tarifa) > 0) {
        //     throw new IllegalStateException("No se puede eliminar una tarifa con viajes asociados");
        // }

        tarifaRepository.delete(tarifa);
        return true;
    }
}
