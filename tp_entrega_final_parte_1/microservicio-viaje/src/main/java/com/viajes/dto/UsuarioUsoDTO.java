package com.viajes.dto;

public class UsuarioUsoDTO {
    private Long idCuenta;
    private String tipoCuenta;
    private Long cantidadViajes;
    private Long tiempoTotalEnMinutos;
    private Long kmTotales;

    public UsuarioUsoDTO(Long idCuenta, String tipoCuenta, Long cantidadViajes, Long tiempoTotalEnMinutos, Long kmTotales) {
        this.idCuenta = idCuenta;
        this.tipoCuenta = tipoCuenta;
        this.cantidadViajes = cantidadViajes;
        this.tiempoTotalEnMinutos = tiempoTotalEnMinutos;
        this.kmTotales = kmTotales;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }

    public Long getCantidadViajes() {
        return cantidadViajes;
    }

    public void setCantidadViajes(Long cantidadViajes) {
        this.cantidadViajes = cantidadViajes;
    }

    public Long getTiempoTotalEnMinutos() {
        return tiempoTotalEnMinutos;
    }

    public void setTiempoTotalEnMinutos(Long tiempoTotalEnMinutos) {
        this.tiempoTotalEnMinutos = tiempoTotalEnMinutos;
    }

    public Long getKmTotales() {
        return kmTotales;
    }

    public void setKmTotales(Long kmTotales) {
        this.kmTotales = kmTotales;
    }
}
