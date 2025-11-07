package com.monopatines.usuarios.dto;

import com.monopatines.usuarios.entities.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaResponseDTO {
    private Long id;
    private LocalDate fechaAlta;
    private TipoCuenta tipo;
    private Double saldo;
    private String cuentaMercadoPago;
    private Double cupoMensualUsado;
    private Boolean activa;
    private List<UsuarioSimpleDTO> usuarios;
}
