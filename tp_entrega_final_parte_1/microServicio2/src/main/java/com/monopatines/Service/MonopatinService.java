package com.monopatines.Service;

import com.monopatines.DTO.MonopatinDTO;
import com.monopatines.DTO.ReporteUsoMonopatinDTO;
import com.monopatines.Repository.MonopatinRepository;
import com.monopatines.entities.Monopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service("Monopatines")
public class MonopatinService {

    @Autowired
    private MonopatinRepository monopatinRepositorio;

    @Autowired
    //private ParadaRepository paradaRepositorio;

    public MonopatinService(MonopatinRepository monopatinRepository) {
        this.monopatinRepositorio = monopatinRepository;
    }

    public List<MonopatinDTO> getAllMonopatines() {
        return monopatinRepositorio.findAll().stream().map(MonopatinDTO::new).collect(Collectors.toList());
    }

    public MonopatinDTO addMonopatin(MonopatinDTO monopatinDTO) {
        // Verificar que el idParada no es nulo ni vacío
       /* if (monopatinDTO.getIdParada() != null && !monopatinDTO.getIdParada().isEmpty()) {
            // Consultar si existe la parada con el idParada proporcionado
            boolean paradaExists = paradaRepository.existsById(monopatinDTO.getIdParada());

            if (!paradaExists) {
                throw new IllegalArgumentException("La parada con el id " + monopatinDTO.getIdParada() + " no existe.");
            }
        }*/


        Monopatin nuevoMonopatin = new Monopatin();
        nuevoMonopatin.setId(monopatinDTO.getId());
        nuevoMonopatin.setEstado(monopatinDTO.getEstado());
        nuevoMonopatin.setIdParada(monopatinDTO.getIdParada());
        nuevoMonopatin.setLongitud(monopatinDTO.getLongitud());
        nuevoMonopatin.setLatitud(monopatinDTO.getLatitud());
        nuevoMonopatin.setTiempoUso(monopatinDTO.getTiempoUso());
        nuevoMonopatin.setTiempoPausa(monopatinDTO.getTiempoPausa());
        nuevoMonopatin.setKmRecorridos(monopatinDTO.getKmRecorridos());


        Monopatin monopatin = monopatinRepositorio.save(nuevoMonopatin);

        return new MonopatinDTO(monopatin);
    }

    public Optional<MonopatinDTO> getMonopatinById(int id) {
        return monopatinRepositorio.findById(id).map(MonopatinDTO::new);
    }

    public void deleteMonopatin(int id) {
        monopatinRepositorio.deleteById(id);
    }

    public void ponerEnMantenimiento(int id) {
        Monopatin monopatin = monopatinRepositorio.findById(id).orElse(null);
        if (monopatin != null) {
            monopatin.setEstado("en mantenimiento");
            monopatinRepositorio.save(monopatin);
        }
    }

    public void finalizarMantenimiento(int id) {
        Monopatin monopatin = monopatinRepositorio.findById(id).orElse(null);
        if (monopatin != null && monopatin.getEstado().equals("en mantenimiento")) {
            monopatin.setEstado("disponible");
            monopatin.setKmRecorridos(0);
            monopatin.setTiempoUso(0);
            monopatin.setTiempoPausa(0);
            monopatinRepositorio.save(monopatin);
        }
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

    public List<MonopatinDTO> getMonopatinesPorTiempoDeUsoConPausa() {
        return monopatinRepositorio.findAll().stream()
                .map(MonopatinDTO::new)
                .sorted(Comparator.comparingInt(
                        (MonopatinDTO m) -> m.getTiempoUso() + m.getTiempoPausa()
                ).reversed())
                .collect(Collectors.toList());

    }

    /**
     * Punto g: Buscar monopatines cercanos a una ubicación
     * Usa la fórmula de Haversine para calcular distancia
     */
    public List<MonopatinDTO> getMonopatinesCercanos(double lat, double lon, double radioKm) {
        return monopatinRepositorio.findAll().stream()
                .map(MonopatinDTO::new)
                .filter(m -> calcularDistancia(lat, lon, m.getLatitud(), m.getLongitud()) <= radioKm)
                .sorted(Comparator.comparingDouble(
                        m -> calcularDistancia(lat, lon, m.getLatitud(), m.getLongitud())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Calcula la distancia entre dos puntos geográficos usando la fórmula de Haversine
     * @return distancia en kilómetros
     */
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {
        final int RADIO_TIERRA_KM = 6371;

        double latDistancia = Math.toRadians(lat2 - lat1);
        double lonDistancia = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistancia / 2) * Math.sin(latDistancia / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistancia / 2) * Math.sin(lonDistancia / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return RADIO_TIERRA_KM * c;
    }

    /**
     * Punto a: Reporte de uso por kilómetros para mantenimiento
     * Configurable para incluir o no tiempos de pausa
     */
    public List<ReporteUsoMonopatinDTO> getReporteUsoPorKilometraje(boolean incluirPausas) {
        return monopatinRepositorio.findAll().stream()
                .map(m -> new ReporteUsoMonopatinDTO(
                        m.getId(),
                        m.getEstado(),
                        m.getKmRecorridos(),
                        m.getTiempoUso(),
                        incluirPausas ? m.getTiempoPausa() : null,
                        m.getLatitud(),
                        m.getLongitud()
                ))
                .sorted(Comparator.comparing(ReporteUsoMonopatinDTO::getKmRecorridos).reversed())
                .collect(Collectors.toList());
    }

}
