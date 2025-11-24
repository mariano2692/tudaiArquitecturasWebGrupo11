package com.usuarios.dto;

import com.usuarios.entities.TipoCuenta;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaSimpleDTO {
    private Long id;
    private LocalDate fechaAlta;
    private TipoCuenta tipo;
    private Double saldo;
    private Boolean activa;
}
