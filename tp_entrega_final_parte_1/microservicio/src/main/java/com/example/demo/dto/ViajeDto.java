package com.example.demo.dto;

import com.example.demo.Entity.Pausa;

import java.time.LocalDateTime;
import java.util.List;

public class ViajeDto {
    private Long id;
    private Long idMonopatin;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long kmRecorridos;
    private Double valorTotal;

    public List<Pausa> getInicioPausasFinal() {
        return inicioPausasFinal;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public Long getKmRecorridos() {
        return kmRecorridos;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }

    public Long getId() {
        return id;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setKmRecorridos(Long kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    private List<Pausa> inicioPausasFinal;
}
