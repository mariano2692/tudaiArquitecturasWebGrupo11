package com.monopatines.Service;

import com.monopatines.DTO.MonopatinDTO;
import com.monopatines.DTO.ReporteUsoMonopatinDTO;
import com.monopatines.Repository.MonopatinRepository;
import com.monopatines.entities.Monopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service("Monopatines")
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepositorio;
    private static final double RADIO_TIERRA_KM = 6371.0;

    public List<MonopatinDTO> getAllMonopatines() {
        return monopatinRepositorio.findAll()
                .stream()
                .map(MonopatinDTO::new)
                .collect(Collectors.toList());
    }

    public MonopatinDTO addMonopatin(MonopatinDTO monopatinDTO) {
        Monopatin nuevoMonopatin = new Monopatin();
        nuevoMonopatin.setId(monopatinDTO.getId());
        nuevoMonopatin.setEstado(monopatinDTO.getEstado());
        nuevoMonopatin.setIdParada(monopatinDTO.getIdParada());
        nuevoMonopatin.setLongitud(monopatinDTO.getLongitud());
        nuevoMonopatin.setLatitud(monopatinDTO.getLatitud());
        nuevoMonopatin.setTiempoUso(monopatinDTO.getTiempoUso());
        nuevoMonopatin.setTiempoPausa(monopatinDTO.getTiempoPausa());
        nuevoMonopatin.setKmRecorridos(monopatinDTO.getKmRecorridos());

        return new MonopatinDTO(monopatinRepositorio.save(nuevoMonopatin));
    }

    public Optional<MonopatinDTO> getMonopatinById(int id) {
        return monopatinRepositorio.findById(id).map(MonopatinDTO::new);
    }

    public void deleteMonopatin(int id) {
        monopatinRepositorio.deleteById(id);
    }

    public void ponerEnMantenimiento(int id) {
        monopatinRepositorio.findById(id).ifPresent(monopatin -> {
            monopatin.setEstado("en mantenimiento");
            monopatinRepositorio.save(monopatin);
        });
    }

    public void finalizarMantenimiento(int id) {
        monopatinRepositorio.findById(id).ifPresent(monopatin -> {
            if ("en mantenimiento".equals(monopatin.getEstado())) {
                monopatin.setEstado("disponible");
                monopatin.setKmRecorridos(0);
                monopatin.setTiempoUso(0);
                monopatin.setTiempoPausa(0);
                monopatinRepositorio.save(monopatin);
            }
        });
    }

    public List<MonopatinDTO> getMonopatinesPorKilometros() {
        return monopatinRepositorio.findAll().stream()
                .map(MonopatinDTO::new)
                .sorted(Comparator.comparing(MonopatinDTO::getKmRecorridos).reversed())
                .collect(Collectors.toList());
    }

    public List<MonopatinDTO> getMonopatinesPorTiempoDeUso() {
        return monopatinRepositorio.findAll().stream()
                .map(MonopatinDTO::new)
                .sorted(Comparator.comparing(MonopatinDTO::getTiempoUso).reversed())
                .collect(Collectors.toList());
    }
/*
    public List<MonopatinDTO> getMonopatinesPorTiempoDeUsoConPausa() {
        return monopatinRepositorio.findAll().stream()
                .map(MonopatinDTO::new)
                .sorted(Comparator.comparingInt(
                        m -> m.getTiempoUso() + m.getTiempoPausa()
                ).reversed())
                .collect(Collectors.toList());
    }

 */

    /**
     * Genera un reporte de uso por km. Si incluirPausa = true, suma tiempo de pausa al uso total.
     */
    public List<ReporteUsoMonopatinDTO> getReporteUsoPorKm(boolean incluirPausa) {
        return monopatinRepositorio.findAll().stream()
                .map(m -> {
                    ReporteUsoMonopatinDTO dto = new ReporteUsoMonopatinDTO();
                    dto.setIdMonopatin(m.getId());
                    dto.setKmRecorridos(m.getKmRecorridos());
                    dto.setTiempoPausa(m.getTiempoPausa());
                    dto.setTiempoUso(incluirPausa ? m.getTiempoUso() + m.getTiempoPausa() : m.getTiempoUso());
                    return dto;
                })
                .sorted(Comparator.comparing(ReporteUsoMonopatinDTO::getKmRecorridos).reversed())
                .collect(Collectors.toList());
    }

    public List<Monopatin> obtenerMonopatinesCercanos(double latUsuario, double lonUsuario, double radioKm) {
        List<Monopatin> disponibles = monopatinRepositorio.findByEstado("disponible");

        return disponibles.stream()
                .filter(m -> distancia(latUsuario, lonUsuario, m.getLatitud(), m.getLongitud()) <= radioKm)
                .collect(Collectors.toList());
    }

    /**
     * Calcula la distancia entre dos coordenadas geográficas (en km)
     * usando la fórmula de Haversine.
     */
    private double distancia(double lat1, double lon1, double lat2, double lon2) {
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }
}
