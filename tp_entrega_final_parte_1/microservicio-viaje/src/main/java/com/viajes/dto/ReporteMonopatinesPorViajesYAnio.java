package com.viajes.dto;

public class ReporteMonopatinesPorViajesYAnio {
    private Long idMonopatin;
    private Long cantViajes;
    private Long anio;

    public void setIdMonopatin(Long idMonopatin) {
        this.idMonopatin = idMonopatin;
    }

    public void setCantViajes(Long cantViajes) {
        this.cantViajes = cantViajes;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }

    public Long getAnio() {
        return anio;
    }

    public Long getCantViajes() {
        return cantViajes;
    }
}
