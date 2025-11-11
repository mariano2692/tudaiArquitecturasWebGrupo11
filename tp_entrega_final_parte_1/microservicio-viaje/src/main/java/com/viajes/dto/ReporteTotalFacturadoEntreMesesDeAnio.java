package com.viajes.dto;

public class ReporteTotalFacturadoEntreMesesDeAnio {
    private Double totalFacturado;
    private Long anio;
    private Long mesInicio;
    private Long mesFin;

    public ReporteTotalFacturadoEntreMesesDeAnio(Double totalFacturado, Long anio, Long mesInicio, Long mesFin) {
        this.totalFacturado = totalFacturado;
        this.anio = anio;
        this.mesInicio = mesInicio;
        this.mesFin = mesFin;
    }

    public ReporteTotalFacturadoEntreMesesDeAnio() {
    }

    public Double getTotalFacturado() {
        return totalFacturado;
    }

    public Long getAnio() {
        return anio;
    }

    public Long getMesInicio() {
        return mesInicio;
    }

    public Long getMesFin() {
        return mesFin;
    }

    public void setTotalFacturado(Double totalFacturado) {
        this.totalFacturado = totalFacturado;
    }

    public void setAnio(Long anio) {
        this.anio = anio;
    }

    public void setMesInicio(Long mesInicio) {
        this.mesInicio = mesInicio;
    }

    public void setMesFin(Long mesFin) {
        this.mesFin = mesFin;
    }
}
