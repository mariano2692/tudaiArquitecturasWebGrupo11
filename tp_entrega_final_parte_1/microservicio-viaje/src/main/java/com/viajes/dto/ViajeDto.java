package com.viajes.dto;

import com.viajes.entity.Pausa;

import java.time.LocalDateTime;
import java.util.List;

public class ViajeDto {
    private Long id;
    private Long idMonopatin;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long kmRecorridos;
    private Double valorTotal;

    private List<Pausa> inicioPausasFinal;

    public Long getId() {
        return id;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Long getKmRecorridos() {
        return kmRecorridos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public List<Pausa> getInicioPausasFinal() {
        return inicioPausasFinal;
    }
}
