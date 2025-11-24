package com.viajes.controller;

import com.viajes.dto.TarifaRequestDTO;
import com.viajes.dto.TarifaResponseDTO;
import com.viajes.entity.Tarifa;
import com.viajes.service.TarifaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/tarifas")
public class TarifaController {

    @Autowired
    private TarifaService tarifaService;

    /**
     * Punto f: Ajustar precios como administrador
     * El sistema habilita los nuevos precios a partir de la fecha especificada
     */
    @PostMapping("/ajustar")
    public ResponseEntity<?> ajustarPrecios(@RequestBody TarifaRequestDTO tarifaRequest) {
        try {
            Tarifa nuevaTarifa = new Tarifa(
                    tarifaRequest.getPrecioBase(),
                    tarifaRequest.getPrecioPorKm(),
                    tarifaRequest.getPrecioPorMinutoPausa(),
                    tarifaRequest.getFechaInicioVigencia()
            );

            Tarifa tarifaGuardada = tarifaService.ajustarPrecios(nuevaTarifa);
            TarifaResponseDTO response = new TarifaResponseDTO(tarifaGuardada);

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }



    /**
     * Obtener la tarifa activa actual
     */
    @GetMapping("/actual")
    public ResponseEntity<?> getTarifaActual() {
        try {
            Tarifa tarifa = tarifaService.getTarifaActual();
            if (tarifa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"No hay tarifa activa\"}");
            }
            TarifaResponseDTO response = new TarifaResponseDTO(tarifa);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * READ: Obtener tarifa por ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getTarifaById(@PathVariable Long id) {
        try {
            Tarifa tarifa = tarifaService.getTarifaById(id);
            if (tarifa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tarifa no encontrada con ID: " + id + "\"}");
            }
            TarifaResponseDTO response = new TarifaResponseDTO(tarifa);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }


    /**
     * Obtener todas las tarifas (historial)
     */
    @GetMapping("")
    public ResponseEntity<?> getAllTarifas() {
        try {
            List<Tarifa> tarifas = tarifaService.getAllTarifas();
            List<TarifaResponseDTO> response = tarifas.stream()
                    .map(TarifaResponseDTO::new)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * UPDATE: Modificar una tarifa existente
     * Útil para corregir tarifas programadas que aún no están activas
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarifa(
            @PathVariable Long id,
            @RequestBody TarifaRequestDTO tarifaRequest) {
        try {
            Tarifa tarifaActualizada = new Tarifa(
                    tarifaRequest.getPrecioBase(),
                    tarifaRequest.getPrecioPorKm(),
                    tarifaRequest.getPrecioPorMinutoPausa(),
                    tarifaRequest.getFechaInicioVigencia()
            );

            Tarifa tarifa = tarifaService.updateTarifa(id, tarifaActualizada);

            if (tarifa == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tarifa no encontrada con ID: " + id + "\"}");
            }

            TarifaResponseDTO response = new TarifaResponseDTO(tarifa);
            return ResponseEntity.ok(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * DELETE: Eliminar una tarifa
     * Solo se pueden eliminar tarifas que no están activas y no tienen viajes asociados
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarifa(@PathVariable Long id) {
        try {
            boolean eliminada = tarifaService.deleteTarifa(id);

            if (!eliminada) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("{\"error\":\"Tarifa no encontrada con ID: " + id + "\"}");
            }

            return ResponseEntity.ok("{\"message\":\"Tarifa eliminada correctamente\"}");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    /**
     * Activar tarifas programadas (puede ser llamado por un scheduler)
     */
    @PostMapping("/activar-programadas")
    public ResponseEntity<?> activarTarifasProgramadas() {
        try {
            tarifaService.activarTarifasProgramadas();
            return ResponseEntity.ok("{\"message\":\"Tarifas programadas activadas correctamente\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
}
