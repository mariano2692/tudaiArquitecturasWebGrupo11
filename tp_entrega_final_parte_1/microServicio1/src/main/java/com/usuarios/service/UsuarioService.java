package com.usuarios.service;

import com.usuarios.dto.*;
import com.usuarios.dto.CuentaSimpleDTO;
import com.usuarios.dto.UsuarioRequestDTO;
import com.usuarios.dto.UsuarioResponseDTO;
import com.usuarios.dto.UsuarioSimpleDTO;
import com.usuarios.entities.Usuario;
import com.usuarios.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        Usuario usuario = usuarioRepository.findByIdWithCuentas(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
        return convertToResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO create(UsuarioRequestDTO dto) {
        if (usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        Usuario usuario = new Usuario(
                dto.getNombre(),
                dto.getApellido(),
                dto.getEmail(),
                dto.getCelular()
        );

        usuario = usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO dto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));

        // Verificar si el email ya existe (excepto el propio usuario)
        if (!usuario.getEmail().equals(dto.getEmail()) && usuarioRepository.existsByEmail(dto.getEmail())) {
            throw new RuntimeException("Ya existe un usuario con el email: " + dto.getEmail());
        }

        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setCelular(dto.getCelular());

        usuario = usuarioRepository.save(usuario);
        return convertToResponseDTO(usuario);
    }

    @Transactional
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findUsuariosMasActivos() {
        return usuarioRepository.findUsuariosMasActivos()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }


    // Métodos de conversión DTO
    private UsuarioResponseDTO convertToResponseDTO(Usuario usuario) {
        UsuarioResponseDTO dto = new UsuarioResponseDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setCelular(usuario.getCelular());

        if (usuario.getCuentas() != null) {
            dto.setCuentas(usuario.getCuentas().stream()
                    .map(cuenta -> new CuentaSimpleDTO(
                            cuenta.getId(),
                            cuenta.getFechaAlta(),
                            cuenta.getTipo(),
                            cuenta.getSaldo(),
                            cuenta.getActiva()
                    ))
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public UsuarioSimpleDTO convertToSimpleDTO(Usuario usuario) {
        return new UsuarioSimpleDTO(
                usuario.getId(),
                usuario.getNombre(),
                usuario.getApellido(),
                usuario.getEmail(),
                usuario.getCelular()
        );
    }
}
