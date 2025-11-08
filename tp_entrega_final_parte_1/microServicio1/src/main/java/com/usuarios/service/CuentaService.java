package com.usuarios.service;

import com.usuarios.dto.*;
import com.usuarios.dto.CuentaRequestDTO;
import com.usuarios.dto.CuentaResponseDTO;
import com.usuarios.entities.Cuenta;
import com.usuarios.entities.TipoCuenta;
import com.usuarios.entities.Usuario;
import com.usuarios.repository.CuentaRepository;
import com.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> findAll() {
        return cuentaRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CuentaResponseDTO findById(Long id) {
        Cuenta cuenta = cuentaRepository.findByIdWithUsuarios(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));
        return convertToResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO create(CuentaRequestDTO dto) {
        Cuenta cuenta = new Cuenta(
                LocalDate.now(),
                dto.getTipo(),
                dto.getSaldo(),
                dto.getCuentaMercadoPago()
        );

        cuenta = cuentaRepository.save(cuenta);
        return convertToResponseDTO(cuenta);
    }

    @Transactional
    public CuentaResponseDTO update(Long id, CuentaRequestDTO dto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));

        cuenta.setTipo(dto.getTipo());
        cuenta.setSaldo(dto.getSaldo());
        cuenta.setCuentaMercadoPago(dto.getCuentaMercadoPago());

        cuenta = cuentaRepository.save(cuenta);
        return convertToResponseDTO(cuenta);
    }

    @Transactional
    public void delete(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new RuntimeException("Cuenta no encontrada con id: " + id);
        }
        cuentaRepository.deleteById(id);
    }

    // Punto 4.b - Anular cuenta
    @Transactional
    public CuentaResponseDTO anularCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));

        cuenta.anular();
        cuenta = cuentaRepository.save(cuenta);
        return convertToResponseDTO(cuenta);
    }

    // Activar cuenta
    @Transactional
    public CuentaResponseDTO activarCuenta(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));

        cuenta.activar();
        cuenta = cuentaRepository.save(cuenta);
        return convertToResponseDTO(cuenta);
    }

    // Asociar usuario a cuenta
    @Transactional
    public CuentaResponseDTO asociarUsuario(Long cuentaId, Long usuarioId) {
        Cuenta cuenta = cuentaRepository.findByIdWithUsuarios(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + cuentaId));

        Usuario usuario = usuarioRepository.findByIdWithCuentas(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        // Sincronizar AMBOS lados de la relación bidireccional
        cuenta.getUsuarios().add(usuario);
        usuario.getCuentas().add(cuenta);

        cuenta = cuentaRepository.save(cuenta);

        return convertToResponseDTO(cuenta);
    }

    // Desasociar usuario de cuenta
    @Transactional
    public CuentaResponseDTO desasociarUsuario(Long cuentaId, Long usuarioId) {
        Cuenta cuenta = cuentaRepository.findByIdWithUsuarios(cuentaId)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + cuentaId));

        Usuario usuario = usuarioRepository.findByIdWithCuentas(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + usuarioId));

        // Sincronizar AMBOS lados de la relación bidireccional
        cuenta.getUsuarios().remove(usuario);
        usuario.getCuentas().remove(cuenta);

        cuenta = cuentaRepository.save(cuenta);

        return convertToResponseDTO(cuenta);
    }

    // Cargar saldo
    @Transactional
    public CuentaResponseDTO cargarSaldo(Long id, Double monto) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + id));

        if (monto <= 0) {
            throw new RuntimeException("El monto debe ser mayor a 0");
        }

        cuenta.cargarSaldo(monto);
        cuenta = cuentaRepository.save(cuenta);
        return convertToResponseDTO(cuenta);
    }

    // Obtener cuentas por tipo
    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> findByTipo(TipoCuenta tipo) {
        return cuentaRepository.findByTipo(tipo)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Obtener cuentas activas/inactivas
    @Transactional(readOnly = true)
    public List<CuentaResponseDTO> findByActiva(Boolean activa) {
        return cuentaRepository.findByActiva(activa)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Métodos de conversión DTO
    private CuentaResponseDTO convertToResponseDTO(Cuenta cuenta) {
        CuentaResponseDTO dto = new CuentaResponseDTO();
        dto.setId(cuenta.getId());
        dto.setFechaAlta(cuenta.getFechaAlta());
        dto.setTipo(cuenta.getTipo());
        dto.setSaldo(cuenta.getSaldo());
        dto.setCuentaMercadoPago(cuenta.getCuentaMercadoPago());
        dto.setCupoMensualUsado(cuenta.getCupoMensualUsado());
        dto.setActiva(cuenta.getActiva());

        if (cuenta.getUsuarios() != null) {
            dto.setUsuarios(cuenta.getUsuarios().stream()
                    .map(usuarioService::convertToSimpleDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }
}
