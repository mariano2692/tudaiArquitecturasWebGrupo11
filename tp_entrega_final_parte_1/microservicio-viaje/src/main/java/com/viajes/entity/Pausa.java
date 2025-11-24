package com.viajes.entity;

import jakarta.persistence.*;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

@Entity
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime pausa;

    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    public Pausa(LocalDateTime pausa, Viaje viaje){
        this.pausa = pausa;
        this.viaje = viaje;
    }

    public Pausa() {

    }

    public LocalDateTime getPausa() {
        return pausa;
    }

    public Viaje getViaje() {
        return viaje;
    }

    public Long getId() {
        return id;
    }

    public void setPausa(LocalDateTime pausa) {
        this.pausa = pausa;
    }

    public void setViaje(Viaje viaje) {
        this.viaje = viaje;
    }
}
