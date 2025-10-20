package org.example.tp_03_integrador.controllers;

import org.example.tp_03_integrador.dtos.EstudianteCarreraRequestDTO;
import org.example.tp_03_integrador.dtos.EstudianteDTO;
import org.example.tp_03_integrador.services.EstudianteCarreraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/inscripcion")
public class InscripcionController {

    @Autowired
    private EstudianteCarreraService EstudianteCarreraService;

    public InscripcionController(EstudianteCarreraService EstudianteCarreraService) {
        this.EstudianteCarreraService = EstudianteCarreraService;
    }

    //b matricular un estudiante en una carrera
    @PostMapping("/matricular")
    public ResponseEntity<EstudianteDTO> matricularEstudiante(@RequestBody EstudianteCarreraRequestDTO ECRDTO) {
        final var result = this.EstudianteCarreraService.matricularEstudiante(ECRDTO);
        return ResponseEntity.accepted().body(result);
    }

}
