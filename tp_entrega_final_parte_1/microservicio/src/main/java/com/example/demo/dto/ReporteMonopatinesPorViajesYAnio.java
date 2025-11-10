package com.example.demo.dto;

public class ReporteMonopatinesPorViajesYAnio {
    private Long idMonopatin;
    private Long cantViajes;
    private Long anio;

    public Long getIdMonopatin() {
        return idMonopatin;
    }
    public Long getCantViajes() {
        return cantViajes;
    }

    public Long getAnio() {
        return anio;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public void setCantViajes(Long cantViajes) {
        this.cantViajes = cantViajes;
    }
}
