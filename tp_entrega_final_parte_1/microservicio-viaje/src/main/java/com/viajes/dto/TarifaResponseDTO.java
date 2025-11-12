package com.viajes.dto;

import com.viajes.entity.Tarifa;

import java.time.LocalDate;

public class TarifaResponseDTO {
    private Long id;
    private Double precioBase;
    private Double precioPorKm;
    private Double precioPorMinutoPausa;
    private LocalDate fechaInicioVigencia;
    private Boolean activa;

    public TarifaResponseDTO() {
    }

    public TarifaResponseDTO(Tarifa tarifa) {
        this.id = tarifa.getId();
        this.precioBase = tarifa.getPrecioBase();
        this.precioPorKm = tarifa.getPrecioPorKm();
        this.precioPorMinutoPausa = tarifa.getPrecioPorMinutoPausa();
        this.fechaInicioVigencia = tarifa.getFechaInicioVigencia();
        this.activa = tarifa.getActiva();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Boolean getActiva() {
        return activa;
    }

    public void setActiva(Boolean activa) {
        this.activa = activa;
    }
}
