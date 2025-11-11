package entity;

import jakarta.persistence.Entity;
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

    @OneToMany(mappedBy = "viaje", cascade = CascadeType.ALL)
    private List<Pausa> inicioPausasFinal;

    public Long getId() {
        return id;
    }

    public Long getIdMonopatin() {
        return idMonopatin;
    }

    public Long getIdCuenta() {
        return idCuenta;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public LocalDateTime getFechaHoraFin() {
        return fechaHoraFin;
    }

    public Long getKmRecorridos() {
        return kmRecorridos;
    }

    public Double getValorTotal() {
        return valorTotal;
    }

    public List<Pausa> getInicioPausasFinal() {
        return inicioPausasFinal;
    }

    public void setIdMonopatin(Long idMonopatin) {
        this.idMonopatin = idMonopatin;
    }

    public void setIdCuenta(Long idCuenta) {
        this.idCuenta = idCuenta;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public void setFechaHoraFin(LocalDateTime fechaHoraFin) {
        this.fechaHoraFin = fechaHoraFin;
    }

    public void setKmRecorridos(Long kmRecorridos) {
        this.kmRecorridos = kmRecorridos;
    }

    public void setValorTotal(Double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public void setInicioPausasFinal(List<Pausa> inicioPausasFinal) {
        this.inicioPausasFinal = inicioPausasFinal;
    }
}
