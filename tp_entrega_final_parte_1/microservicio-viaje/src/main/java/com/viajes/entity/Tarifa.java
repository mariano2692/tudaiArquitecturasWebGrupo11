package com.viajes.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tarifas")
public class Tarifa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double precioBase;

    @Column(nullable = false)
    private Double precioPorKm;

    @Column(nullable = false)
    private Double precioPorMinutoPausa;

    @Column(nullable = false)
    private LocalDate fechaInicioVigencia;

    @Column(nullable = false)
    private Boolean activa;

    public Tarifa() {
    }

    public Tarifa(Double precioBase, Double precioPorKm, Double precioPorMinutoPausa, LocalDate fechaInicioVigencia) {
        this.precioBase = precioBase;
        this.precioPorKm = precioPorKm;
        this.precioPorMinutoPausa = precioPorMinutoPausa;
        this.fechaInicioVigencia = fechaInicioVigencia;
        this.activa = false;
    }

    public Long getId() {
        return id;
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

    public boolean isActiva() {
        return this.activa != null && this.activa
                && this.fechaInicioVigencia != null
                && this.fechaInicioVigencia.isBefore(LocalDate.now());
    }

    public boolean estaVigente() {
        return this.fechaInicioVigencia != null
                && !this.fechaInicioVigencia.isAfter(LocalDate.now());
    }

    public void activar() {
        this.activa = true;
    }

    public void desactivar() {
        this.activa = false;
    }
}
