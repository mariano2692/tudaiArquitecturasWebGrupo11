package com.viajes.dto;

import java.time.LocalDate;

public class TarifaRequestDTO {
    private Double precioBase;
    private Double precioPorKm;
    private Double precioPorMinutoPausa;
    private LocalDate fechaInicioVigencia;

    public TarifaRequestDTO() {
    }

    public TarifaRequestDTO(Double precioBase, Double precioPorKm, Double precioPorMinutoPausa, LocalDate fechaInicioVigencia) {
        this.precioBase = precioBase;
        this.precioPorKm = precioPorKm;
        this.precioPorMinutoPausa = precioPorMinutoPausa;
        this.fechaInicioVigencia = fechaInicioVigencia;
    }

    public Double getPrecioBase() {
        return precioBase;
    }

    public void setPrecioBase(Double precioBase) {
        this.precioBase = precioBase;
    }

    public Double getPrecioPorKm() {
        return precioPorKm;
    }

    public void setPrecioPorKm(Double precioPorKm) {
        this.precioPorKm = precioPorKm;
    }

    public Double getPrecioPorMinutoPausa() {
        return precioPorMinutoPausa;
    }

    public void setPrecioPorMinutoPausa(Double precioPorMinutoPausa) {
        this.precioPorMinutoPausa = precioPorMinutoPausa;
    }

    public LocalDate getFechaInicioVigencia() {
        return fechaInicioVigencia;
    }

    public void setFechaInicioVigencia(LocalDate fechaInicioVigencia) {
        this.fechaInicioVigencia = fechaInicioVigencia;
    }
}
