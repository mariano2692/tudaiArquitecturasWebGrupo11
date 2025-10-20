package controllers;

import dtos.CarreraDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CarreraServicio;

import java.util.List;

@RestController
@RequestMapping("/carreras")
public class CarreraController {

    private final CarreraServicio carreraServicio;

    public CarreraController(CarreraServicio carreraServicio) {
        this.carreraServicio = carreraServicio;
    }

    @GetMapping
    public List<CarreraDTO> getCarreras() {
        return carreraServicio.getCarreras();
    }

    @PostMapping
    public ResponseEntity<CarreraDTO> agregarCarrera(@RequestBody CarreraDTO carreraDTO) {
        try {
            CarreraDTO nuevaCarrera = carreraServicio.agregarCarrera(carreraDTO.getNombre());
            return ResponseEntity.ok(nuevaCarrera);
        } catch (RuntimeException e) {
            // Retorna 400 si ya existe o error de negocio
            return ResponseEntity.badRequest().body(null);
        }
    }

}
