package org.example.tp_03_integrador.controllers;

import org.example.tp_03_integrador.dtos.CarreraConCantInscriptosDTO;
import org.example.tp_03_integrador.dtos.ReporteCarreraDTO;
import org.example.tp_03_integrador.services.CarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    @Autowired
    private CarreraService carreraService;


    // 2f) Recuperar carreras ordenadas por cantidad de inscriptos
    @GetMapping("/ordenadas-inscriptos")
    public ResponseEntity<?> getCarrerasOrdenadasPorInscriptos() {
        try {
            List<CarreraConCantInscriptosDTO> carreras = carreraService.getCarrerasOrdenadasPorInscriptos();
            return ResponseEntity.status(HttpStatus.OK).body(carreras);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // 2h) Generar un reporte de las carreras
    @GetMapping("/reporte")
    public ResponseEntity<?> generarReporteCarreras() {
        try {
            List<ReporteCarreraDTO> reporte = carreraService.generarReporteCarreras();
            return ResponseEntity.status(HttpStatus.OK).body(reporte);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

}
