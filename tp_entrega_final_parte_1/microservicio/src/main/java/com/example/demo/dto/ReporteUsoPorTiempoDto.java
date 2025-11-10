package com.example.demo.dto;

import com.example.demo.Entity.Pausa;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ReporteUsoPorTiempoDto {
    private Long idMonopatin;
    private LocalDateTime pausa;

    public ReporteUsoPorTiempoDto(Long idMonopatin, LocalDateTime pausa) {
        this.idMonopatin = idMonopatin;
        this.pausa = pausa;
    }

    public LocalDateTime getPausa() {
        return pausa;
    }

    public void setPausa(LocalDateTime pausa) {
        this.pausa = pausa;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }
}
