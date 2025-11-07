package com.monopatines.usuarios.dto;

import com.monopatines.usuarios.entities.TipoCuenta;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaRequestDTO {

    @NotNull(message = "El tipo de cuenta es obligatorio")
    private TipoCuenta tipo;

    @NotNull(message = "El saldo inicial es obligatorio")
    private Double saldo;

    @NotBlank(message = "La cuenta de MercadoPago es obligatoria")
    private String cuentaMercadoPago;
}
