package com.example.demo.dto;

public class ReporteTotalFacturadoEntreMesesDeAnio {
    private Double totalFacturado;
    private Long anio;
    private Long mesInicio;
    private Long mesFin;

    public Double getTotalFacturado() {
        return totalFacturado;
    }

    public Long getMesFin() {
        return mesFin;
    }

    public Long getMesInicio() {
        return mesInicio;
    }

    public Long getAnio() {
        return anio;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public void setMesFin(Long mesFin) {
        this.mesFin = mesFin;
    }

    public void setMesInicio(Long mesInicio) {
        this.mesInicio = mesInicio;
    }

    public void setTotalFacturado(Double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }
}
