package com.monopatines.DTO;

public class ReporteUsoMonopatinDTO {
    private int idMonopatin;
    private double kmRecorridos;
    private int tiempoUso;    // en minutos
    private int tiempoPausa;  // en minutos

    public ReporteUsoMonopatinDTO() {}

    public ReporteUsoMonopatinDTO(int idMonopatin, double kmRecorridos, int tiempoUso, int tiempoPausa) {
        this.idMonopatin = idMonopatin;
        this.kmRecorridos = kmRecorridos;
        this.tiempoUso = tiempoUso;
        this.tiempoPausa = tiempoPausa;
    }

    public int getIdMonopatin() { return idMonopatin; }
    public void setIdMonopatin(int idMonopatin) { this.idMonopatin = idMonopatin; }

    public double getKmRecorridos() { return kmRecorridos; }
    public void setKmRecorridos(double kmRecorridos) { this.kmRecorridos = kmRecorridos; }

    public int getTiempoUso() { return tiempoUso; }
    public void setTiempoUso(int tiempoUso) { this.tiempoUso = tiempoUso; }

    public int getTiempoPausa() { return tiempoPausa; }
    public void setTiempoPausa(int tiempoPausa) { this.tiempoPausa = tiempoPausa; }
}
