package org.example.gateway.chat.dto;

import jakarta.validation.constraints.NotBlank;

public class ChatRequest {

    @NotBlank(message = "El mensaje no puede estar vacío")
    private String mensaje;

    private Long idCuenta; // Opcional, para consultas específicas de cuenta

    public ChatRequest() {}

    public ChatRequest(String mensaje, Long idCuenta) {
        this.mensaje = mensaje;
        this.idCuenta = idCuenta;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }
}
