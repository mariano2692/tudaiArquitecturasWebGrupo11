package com.monopatines.DTO;

public class ReporteUsoMonopatinDTO {
    private int id;
    private String estado;
    private double kmRecorridos;
    private int tiempoUso;
    private Integer tiempoPausa;
    private Integer tiempoTotal;
    private String ubicacion;

    public ReporteUsoMonopatinDTO(int id, String estado, double kmRecorridos, int tiempoUso,
                                  Integer tiempoPausa, double latitud, double longitud) {
        this.id = id;
        this.estado = estado;
        this.kmRecorridos = kmRecorridos;
        this.tiempoUso = tiempoUso;
        this.tiempoPausa = tiempoPausa;
        this.tiempoTotal = tiempoPausa != null ? tiempoUso + tiempoPausa : null;
        this.ubicacion = "lat: " + latitud + ", lon: " + longitud;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getKmRecorridos() {
        return kmRecorridos;
    }

    public void setKmRecorridos(double kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public int getTiempoUso() {
        return tiempoUso;
    }

    public void setTiempoUso(int tiempoUso) {
        this.tiempoUso = tiempoUso;
    }

    public Integer getTiempoPausa() {
        return tiempoPausa;
    }

    public void setTiempoPausa(Integer tiempoPausa) {
        this.tiempoPausa = tiempoPausa;
    }

    public Integer getTiempoTotal() {
        return tiempoTotal;
    }

    public void setTiempoTotal(Integer tiempoTotal) {
        this.tiempoTotal = tiempoTotal;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
