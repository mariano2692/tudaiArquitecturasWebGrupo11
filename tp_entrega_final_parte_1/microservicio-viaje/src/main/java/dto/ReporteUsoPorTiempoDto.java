package dto;

import entity.Pausa;

import java.time.LocalDateTime;

public class ReporteUsoPorTiempoDto {
    private Long idMonopatin;
    private LocalDateTime pausa;

    public ReporteUsoPorTiempoDto(Long idMonopatin, LocalDateTime pausa) {
        this.idMonopatin = idMonopatin;
        this.pausa = pausa;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }

    public LocalDateTime getPausa() {
        return pausa;
    }
}
