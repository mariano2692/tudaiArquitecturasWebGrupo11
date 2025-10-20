package controllers;

import dtos.CarreraDTO;
import dtos.EstudianteDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import services.CarreraServicio;
import services.InscripcionServicio;

import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionServicio inscripcionServicio;

    public InscripcionController(InscripcionServicio inscripcionServicio) {
        this.inscripcionServicio = inscripcionServicio;
    }

    @PostMapping("/matricular")
    public String matricular(@RequestParam int idCarrera, @RequestParam int idEstudiante) {
        return inscripcionServicio.matricularEstudianteEnCarrera(idCarrera, idEstudiante);
    }

    @GetMapping("/carreras/ordenadas")
    public List<CarreraDTO> getCarrerasOrdenadasPorCantidadInscriptos() {
        return inscripcionServicio.obtenerCarrerasOrdenadasPorCantidadInscriptos();
    }

    @GetMapping("/estudiantesporcarrerayciudad")
    public List<EstudianteDTO> getEstudiantesPorCarreraYCiudad(@RequestParam int idCarrera,
                                                               @RequestParam String ciudad) {
        return inscripcionServicio.obtenerEstudiantesPorCarreraYCiudad(idCarrera, ciudad);
    }

    @GetMapping("/reportecarreras")
    public List<CarreraDTO> getReporteCarreras() {
        return inscripcionServicio.generarReporteCarreras();
    }
}