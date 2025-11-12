package com.viajes.entity;

import jakarta.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Ahora registramos inicio y fin de la pausa
    private LocalDateTime inicio;
    private LocalDateTime fin;

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    public Pausa() {}

    public Pausa(LocalDateTime inicio, LocalDateTime fin, Viaje viaje){
        this.inicio = inicio;
        this.fin = fin;
        this.viaje = viaje;
    }

    public Long getId() { return id; }

    public LocalDateTime getInicio() { return inicio; }
    public LocalDateTime getFin() { return fin; }
    public Viaje getViaje() { return viaje; }

    public void setInicio(LocalDateTime inicio) { this.inicio = inicio; }
    public void setFin(LocalDateTime fin) { this.fin = fin; }
    public void setViaje(Viaje viaje) { this.viaje = viaje; }

    public int getDuracionMinutos() {
        if (inicio != null && fin != null) {
            return (int) Duration.between(inicio, fin).toMinutes();
        }
        return 0;
    }
}
