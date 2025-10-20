package controllers;

import dtos.EstudianteDTO;
import entities.Estudiante;
import org.springframework.web.bind.annotation.*;
import services.EstudianteServicio;

import java.util.List;

@RestController
@RequestMapping("/estudiantes")
public class EstudianteController {

    private final EstudianteServicio estudianteServicio;

    public EstudianteController(EstudianteServicio estudianteServicio){
        this.estudianteServicio = estudianteServicio;
    }



    @GetMapping
    public List<EstudianteDTO> getEstudiantes(){
        return estudianteServicio.getEstudiantes();
    }

    @PostMapping
    public EstudianteDTO saveEstudiante(@RequestBody EstudianteDTO estudianteDTO){
        return estudianteServicio.save(estudianteDTO);
    }

    @GetMapping("/ordenadospornombre")
    public List<EstudianteDTO> obtenerEstudiantesOrdenadosPorNombre() {
        return estudianteServicio.obtenerEstudiantesOrdenadosPorNombre();
    }

    @GetMapping("/genero/{genero}")
    public List<EstudianteDTO> obtenerPorGenero(@PathVariable String genero) {
        return estudianteServicio.selectByGenero(genero);
    }


    @GetMapping("/{dni}")
    public EstudianteDTO obtenerPorId(@PathVariable int dni) {
        return estudianteServicio.selectById(dni);
    }

}
