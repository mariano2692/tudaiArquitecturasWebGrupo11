package com.monopatines.usuarios.controller;

import com.monopatines.usuarios.dto.CuentaRequestDTO;
import com.monopatines.usuarios.dto.CuentaResponseDTO;
import com.monopatines.usuarios.entities.TipoCuenta;
import com.monopatines.usuarios.service.CuentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/cuentas")
@RequiredArgsConstructor
public class CuentaController {

    private final CuentaService cuentaService;

    @GetMapping
    public ResponseEntity<List<CuentaResponseDTO>> getAllCuentas() {
        return ResponseEntity.ok(cuentaService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> getCuentaById(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.findById(id));
    }

    @PostMapping
    public ResponseEntity<CuentaResponseDTO> createCuenta(@Valid @RequestBody CuentaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(cuentaService.create(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaResponseDTO> updateCuenta(
            @PathVariable Long id,
            @Valid @RequestBody CuentaRequestDTO dto) {
        return ResponseEntity.ok(cuentaService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        cuentaService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // Punto 4.b - Anular cuenta
    @PatchMapping("/{id}/anular")
    public ResponseEntity<CuentaResponseDTO> anularCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.anularCuenta(id));
    }

    @PatchMapping("/{id}/activar")
    public ResponseEntity<CuentaResponseDTO> activarCuenta(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.activarCuenta(id));
    }

    // Asociar/desasociar usuarios
    @PostMapping("/{cuentaId}/usuarios/{usuarioId}")
    public ResponseEntity<CuentaResponseDTO> asociarUsuario(
            @PathVariable Long cuentaId,
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.asociarUsuario(cuentaId, usuarioId));
    }

    @DeleteMapping("/{cuentaId}/usuarios/{usuarioId}")
    public ResponseEntity<CuentaResponseDTO> desasociarUsuario(
            @PathVariable Long cuentaId,
            @PathVariable Long usuarioId) {
        return ResponseEntity.ok(cuentaService.desasociarUsuario(cuentaId, usuarioId));
    }

    // Cargar saldo
    @PatchMapping("/{id}/cargar-saldo")
    public ResponseEntity<CuentaResponseDTO> cargarSaldo(
            @PathVariable Long id,
            @RequestParam Double monto) {
        return ResponseEntity.ok(cuentaService.cargarSaldo(id, monto));
    }

    // Filtros
    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CuentaResponseDTO>> getCuentasByTipo(@PathVariable TipoCuenta tipo) {
        return ResponseEntity.ok(cuentaService.findByTipo(tipo));
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CuentaResponseDTO>> getCuentasActivas() {
        return ResponseEntity.ok(cuentaService.findByActiva(true));
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<CuentaResponseDTO>> getCuentasInactivas() {
        return ResponseEntity.ok(cuentaService.findByActiva(false));
    }
}
