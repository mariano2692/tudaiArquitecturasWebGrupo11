package com.monopatines.usuarios.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "cuentas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "La fecha de alta es obligatoria")
    @Column(nullable = false)
    private LocalDate fechaAlta;

    @NotNull(message = "El tipo de cuenta es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoCuenta tipo;

    @NotNull(message = "El saldo es obligatorio")
    @Column(nullable = false)
    private Double saldo;

    @NotBlank(message = "La cuenta de MercadoPago es obligatoria")
    @Column(nullable = false)
    private String cuentaMercadoPago;

    @Column(nullable = false)
    private Double cupoMensualUsado;

    @Column(nullable = false)
    private Boolean activa;

    @ManyToMany
    @JoinTable(
            name = "usuario_cuenta",
            joinColumns = @JoinColumn(name = "cuenta_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> usuarios = new HashSet<>();

    public Cuenta(LocalDate fechaAlta, TipoCuenta tipo, Double saldo, String cuentaMercadoPago) {
        this.fechaAlta = fechaAlta;
        this.tipo = tipo;
        this.saldo = saldo;
        this.cuentaMercadoPago = cuentaMercadoPago;
        this.cupoMensualUsado = 0.0;
        this.activa = true;
    }

    // Métodos de negocio
    public void anular() {
        this.activa = false;
    }

    public void activar() {
        this.activa = true;
    }

    public boolean esPremium() {
        return this.tipo == TipoCuenta.PREMIUM;
    }

    public void descontarSaldo(Double monto) {
        this.saldo -= monto;
    }

    public void cargarSaldo(Double monto) {
        this.saldo += monto;
    }

    public void resetearCupoMensual() {
        this.cupoMensualUsado = 0.0;
    }

    public void incrementarCupoUsado(Double kilometros) {
        this.cupoMensualUsado += kilometros;
    }

    public boolean superoCupoMensual() {
        return this.cupoMensualUsado > 100.0;
    }
}
