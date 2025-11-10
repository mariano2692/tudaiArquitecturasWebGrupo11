package com.example.demo.Entity;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Viaje {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idMonopatin;
    private Long idCuenta;
    private LocalDateTime fechaHoraInicio;
    private LocalDateTime fechaHoraFin;
    private Long kmRecorridos;
    private Double valorTotal;

    @OneToMany()
    private List<Pausa> inicioPausas;

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public Long getKmRecorridos() {
        return kmRecorridos;
    }

    public List<Pausa> getInicioPausas() {
        return inicioPausas;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setKmRecorridos(Long kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public void setInicioPausas(List<Pausa> inicioPausas) {
        this.inicioPausas = inicioPausas;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setIdMonopatin(Long idMonopatin) {
        this.idMonopatin = idMonopatin;
    }
}
