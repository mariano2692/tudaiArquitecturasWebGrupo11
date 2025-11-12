package com.monopatines.Controller;

import com.monopatines.DTO.MonopatinDTO;
import com.monopatines.DTO.ReporteUsoMonopatinDTO;
import com.monopatines.Service.MonopatinService;
import com.monopatines.entities.Monopatin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/monopatines")
public class MonopatinController {

    @Autowired
    private MonopatinService monopatinServicio;

    public MonopatinController(MonopatinService monopatinService) {
        this.monopatinServicio = monopatinService;
    }

    // --- OBTENER TODOS LOS MONOPATINES ---
    @GetMapping("")
    public List<MonopatinDTO> getAllMonopatines(){
        return monopatinServicio.getAllMonopatines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MonopatinDTO> getMonopatinById(@PathVariable int id){
        Optional<MonopatinDTO> m = monopatinServicio.getMonopatinById(id);
        if(m.isPresent()){
            return m.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
        }
        return ResponseEntity.notFound().build();
    }

    // --- AGREGAR UN MONOPATIN ---
    @PostMapping("")
    public MonopatinDTO addMonopatin(@RequestBody MonopatinDTO monopatinDTO){
        return monopatinServicio.addMonopatin(monopatinDTO);
    }

    @PostMapping("/agregarMuchos")
    public List<MonopatinDTO> addMonopatines(@RequestBody List<MonopatinDTO> dtos) {
        List<MonopatinDTO> result = new ArrayList<>();
        for (MonopatinDTO dto : dtos) {
            try {
                result.add(monopatinServicio.addMonopatin(dto));
            } catch (Exception e) {
                // loguear el error y seguir con los demás
                System.out.println("Error guardando: " + dto.getId() + " -> " + e.getMessage());
            }
        }
        return result;
    }

    // --- DAR DE BAJA UN MONOPATIN ---
    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteMonopatin(@PathVariable int id){
        if(monopatinServicio.getMonopatinById(id).isPresent()){
            monopatinServicio.deleteMonopatin(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }


    // --- PONER MONOPATIN EN MANTENIMIENTO ---
    @PutMapping("/ponerEnMantenimiento/{id}")
    public ResponseEntity<Object> ponerEnMantenimiento(@PathVariable int id){
        if(monopatinServicio.getMonopatinById(id).isPresent()){
            try{
                monopatinServicio.ponerEnMantenimiento(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.notFound().build();
    }

    // --- QUITAR MONOPATIN DE MANTENIMIENTO ---
    @PutMapping("/quitarDeMantenimiento/{id}")
    public ResponseEntity<Object> finalizarMantenimiento(@PathVariable int id){
        if(monopatinServicio.getMonopatinById(id).isPresent()){
            try{
                monopatinServicio.finalizarMantenimiento(id);
                return ResponseEntity.ok().build();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        return ResponseEntity.notFound().build();
    }


    // --- REPORTAJE POR KILOMETRO ---
    @GetMapping("/porKilometraje")
    public List<MonopatinDTO> porKilometraje(){
        return monopatinServicio.getMonopatinesPorKilometros();
    }

    // --- REPORTAJE POR TIEMPO DE USO ---
    @GetMapping("/porTiempoDeUso")
    public List<MonopatinDTO> porTiempoDeUso(){
        return monopatinServicio.getMonopatinesPorTiempoDeUso();
    }

    // --- REPORTAJE POR TIEMPO DE USO CON PAUSA ---
    /*
    @GetMapping("/porTiempoDeUsoConPausa")
    public List<MonopatinDTO> porTiempoDeUsoConPausa(){
        return monopatinServicio.getMonopatinesPorTiempoDeUsoConPausa();
    }


     */
    @GetMapping("/reporte/uso")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> getReporteUsoPorKm(
            @RequestParam(defaultValue = "false") boolean incluirPausa) {
        return ResponseEntity.ok(monopatinServicio.getReporteUsoPorKm(incluirPausa));
    }

    @GetMapping("/cercanos")
    public List<Monopatin> obtenerMonopatinesCercanos(
            @RequestParam double lat,
            @RequestParam double lon,
            @RequestParam(defaultValue = "1.0") double radioKm) {

        return monopatinServicio.obtenerMonopatinesCercanos(lat, lon, radioKm);
    }
}
