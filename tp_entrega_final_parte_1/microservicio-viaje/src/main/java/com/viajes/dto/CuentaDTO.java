package com.viajes.dto;

public class CuentaDTO {
    private Long id;
    private String tipo;

    public CuentaDTO() {
    }

    public CuentaDTO(Long id, String tipo) {
        this.id = id;
        this.tipo = tipo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
