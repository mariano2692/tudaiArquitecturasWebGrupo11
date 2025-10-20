package org.example.tp_03_integrador.controllers;

import org.example.tp_03_integrador.dtos.EstudianteDTO;
import org.example.tp_03_integrador.services.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/estudiante")
public class EstudianteController {
@Autowired
private EstudianteService estudianteService;

    // 2c) Recuperar todos los estudiantes, y especificar algún criterio de ordenamiento simple -> Por APELLIDO
    @GetMapping("")
    public ResponseEntity<?> obtenerTodos() {
        try {
            List<EstudianteDTO> estudiantes = estudianteService.obtenerEstudiantesOrdenadosPorApellidoASC();
            return ResponseEntity.status(HttpStatus.OK).body(estudiantes);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al obtener estudiantes.\"}");
        }
    }


   // d) recuperar un estudiante, en base a su número de libreta universitaria.
    @GetMapping("/getEstudianteByLu/{lu}")
    public ResponseEntity<EstudianteDTO> getEstudianteByLu(
            @PathVariable int lu) {
        EstudianteDTO estudiante = estudianteService.getEstudianteByLu(lu);
        return ResponseEntity.ok(estudiante);
    }


    //e) recuperar todos los estudiantes, en base a su género.
    @GetMapping("/getAllEstudiantesByGenero/{genero}")
    public ResponseEntity<List<EstudianteDTO>> getAllEstudiantesByGenero(
            @PathVariable String genero) {
        List<EstudianteDTO> estudiantes = estudianteService.getAllEstudiantesByGenero(genero);
        return ResponseEntity.ok(estudiantes);
    }
}
