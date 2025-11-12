package com.viajes.dto;

import java.time.LocalDate;

public class UsoMonopatinDTO {
    private Long idUsuario;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Long cantidadViajes;
    private Long tiempoTotalEnMinutos;
    private Long kmTotales;
    private Boolean incluyeUsuariosRelacionados;

    public UsoMonopatinDTO() {
    }

    public UsoMonopatinDTO(Long idUsuario, LocalDate fechaInicio, LocalDate fechaFin, Long cantidadViajes,
                           Long tiempoTotalEnMinutos, Long kmTotales, Boolean incluyeUsuariosRelacionados) {
        this.idUsuario = idUsuario;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.cantidadViajes = cantidadViajes;
        this.tiempoTotalEnMinutos = tiempoTotalEnMinutos;
        this.kmTotales = kmTotales;
        this.incluyeUsuariosRelacionados = incluyeUsuariosRelacionados;
    }

    public Long getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Long idUsuario) {
        this.idUsuario = idUsuario;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
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

    public Boolean getIncluyeUsuariosRelacionados() {
        return incluyeUsuariosRelacionados;
    }

    public void setIncluyeUsuariosRelacionados(Boolean incluyeUsuariosRelacionados) {
        this.incluyeUsuariosRelacionados = incluyeUsuariosRelacionados;
    }
}
