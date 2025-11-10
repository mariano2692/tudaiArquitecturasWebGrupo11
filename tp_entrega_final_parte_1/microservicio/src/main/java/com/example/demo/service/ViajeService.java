package com.example.demo.service;

import com.example.demo.Entity.Pausa;
import com.example.demo.Entity.Viaje;
import com.example.demo.dto.ReporteMonopatinesPorViajesYAnio;
import com.example.demo.dto.ReporteTotalFacturadoEntreMesesDeAnio;
import com.example.demo.dto.ReporteUsoPorTiempoDto;
import com.example.demo.repository.ViajeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ViajeService {
    @Autowired
    private ViajeRepository viajeRepository;
    // Create
    @Transactional
    public Viaje save(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    // Read
    @Transactional(readOnly = true)
    public List<Viaje> getAll() {
        return viajeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Viaje findById(Long id) {
        return viajeRepository.findById(id).orElse(null);
    }

    // Update
    public Viaje update(Viaje viaje) {
        return viajeRepository.save(viaje);
    }

    // Delete
    @Transactional
    public void delete(Viaje viaje) {
        viajeRepository.delete(viaje);
    }

    // Otros
    @Transactional
    public void registrarPausa(Long idViaje, LocalDateTime fechaHoraInicio) {
        // Lógica para registrar el inicio de una pausa
        Viaje viaje = viajeRepository.findById(idViaje).get();
        if (viaje != null) {
            Pausa p = new Pausa(fechaHoraInicio, viaje);
            viaje.getInicioPausas().add(p);
            viajeRepository.save(viaje);
        }
    }

    @Transactional
    public void asociarCuenta(Long idViaje, Long idCuenta) {
        Viaje viaje = viajeRepository.findById(idViaje)
                .orElseThrow(() -> new EntityNotFoundException("Viaje no encontrado"));
        viaje.setIdCuenta(idCuenta);
        viajeRepository.save(viaje);
    }

    @Transactional(readOnly = true)
    public LocalDateTime obtenerInicioUltimaPausa(Long monopatinId) {
        // Obtener el inicio de la última pausa
        Viaje viaje = viajeRepository.findById(monopatinId).get();
        if (viaje != null && !viaje.getInicioPausas().isEmpty()) {
            return viaje.getInicioPausas().get(viaje.getInicioPausas().size() - 1).getPausa();
        }
        return null;
    }

    @Transactional
    public void finalizarViaje(Long viajeId, LocalDateTime fechaHoraFin, Long kmRecorridos) {
        // Lógica para finalizar el viaje
        Viaje viaje = viajeRepository.findById(viajeId).orElse(null);
        if (viaje != null) {
            viaje.setFechaHoraFin(fechaHoraFin);
            viaje.setKmRecorridos(kmRecorridos);
            viajeRepository.save(viaje);
        }
    }

    @Transactional
    public Viaje iniciarViaje(Long monopatinId, LocalDateTime fechaHoraInicio) {
        // Lógica para iniciar un viaje
        Viaje viaje = new Viaje();
        viaje.setFechaHoraInicio(fechaHoraInicio);
        viaje.setIdMonopatin(monopatinId);
        viaje.setFechaHoraFin(null);
        viaje.setKmRecorridos(0L);
        viaje.setInicioPausas(new ArrayList<>());
        viajeRepository.save(viaje);
        return viaje;
    }

    @Transactional(readOnly = true)
    public Map<Long, Long> getDuracionPausas() {
        List<ReporteUsoPorTiempoDto> pausaMonopatines = viajeRepository.reporteUsoPorTiempo();
        Map<Long, List<LocalDateTime>> mapPausasPorMonopatin = new HashMap<>();
        for (ReporteUsoPorTiempoDto repo : pausaMonopatines) {
            if (!mapPausasPorMonopatin.containsKey(repo.getIdMonopatin())) {
                ArrayList<LocalDateTime> arr = new ArrayList<>();
                mapPausasPorMonopatin.put(repo.getIdMonopatin(), arr);
            }
            mapPausasPorMonopatin.get(repo.getIdMonopatin()).add(repo.getPausa());
        }
        return mapPausasPorMonopatin.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> this.sumarPausas(entry.getValue()))
                );
    }

    private Long sumarPausas(List<LocalDateTime> pausas) {
        Long duracion = 0L;
        if (pausas != null && !pausas.isEmpty())
            for (int i = 0; i < pausas.size() - 1; i += 2) {
                duracion += Duration.between(pausas.get(i), pausas.get(i + 1)).toMinutes();
            }
        return duracion;
    }

    @Transactional(readOnly = true)
    public List<ReporteMonopatinesPorViajesYAnio> getReportePorViajeYAnio(Long cantViajes, Long anio) {
        return viajeRepository.getReportePorViajeYAnio(cantViajes, anio);
    }

    @Transactional(readOnly = true)
    public ReporteTotalFacturadoEntreMesesDeAnio getReporteTotalFacturadoEntreMesesDeAnio(Long mesInicio, Long mesFin, Long anio) {
        return viajeRepository.getReporteTotalFacturadoEntreMesesDeAnio(mesInicio, mesFin, anio);
    }
}
