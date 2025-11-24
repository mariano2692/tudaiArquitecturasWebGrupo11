-- Schema para Microservicio de Usuarios y Cuentas
-- Este archivo es solo documentación. Hibernate genera las tablas automáticamente con ddl-auto: update

CREATE DATABASE IF NOT EXISTS db_usuarios;
USE db_usuarios;

-- Tabla de Usuarios
CREATE TABLE IF NOT EXISTS usuarios (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    celular VARCHAR(255) NOT NULL,
    INDEX idx_email (email)
);

-- Tabla de Cuentas
CREATE TABLE IF NOT EXISTS cuentas (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    fecha_alta DATE NOT NULL,
    tipo VARCHAR(50) NOT NULL, -- BASICA o PREMIUM
    saldo DOUBLE NOT NULL,
    cuenta_mercado_pago VARCHAR(255) NOT NULL,
    cupo_mensual_usado DOUBLE NOT NULL DEFAULT 0.0,
    activa BOOLEAN NOT NULL DEFAULT TRUE,
    INDEX idx_tipo (tipo),
    INDEX idx_activa (activa)
);

-- Tabla intermedia Usuario-Cuenta (relación muchos a muchos)
CREATE TABLE IF NOT EXISTS usuario_cuenta (
    usuario_id BIGINT NOT NULL,
    cuenta_id BIGINT NOT NULL,
    PRIMARY KEY (usuario_id, cuenta_id),
    FOREIGN KEY (usuario_id) REFERENCES usuarios(id) ON DELETE CASCADE,
    FOREIGN KEY (cuenta_id) REFERENCES cuentas(id) ON DELETE CASCADE
);
