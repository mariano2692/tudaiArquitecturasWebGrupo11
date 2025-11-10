package com.example.demo.Entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Pausa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime pausa;
    public Pausa() {
    }
    @ManyToOne
    @JoinColumn(name = "viaje_id", nullable = false)
    private Viaje viaje;

    public Pausa(LocalDateTime pausa, Viaje viaje){
        this.pausa = pausa;
        this.viaje = viaje;
    }

    public LocalDateTime getPausa() {
        return pausa;
    }

    public void setPausa(LocalDateTime pausa) {
        this.pausa = pausa;
    }
}