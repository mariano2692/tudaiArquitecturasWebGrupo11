-- Datos de prueba para el Microservicio de Usuarios y Cuentas
-- Este archivo se ejecuta automáticamente si se configura spring.jpa.defer-datasource-initialization=true

-- Insertar usuarios de prueba
INSERT INTO usuarios (nombre, apellido, email, celular) VALUES
('Juan', 'Perez', 'juan.perez@example.com', '2494123456'),
('Maria', 'Gonzalez', 'maria.gonzalez@example.com', '2494234567'),
('Carlos', 'Lopez', 'carlos.lopez@example.com', '2494345678'),
('Ana', 'Martinez', 'ana.martinez@example.com', '2494456789');

-- Insertar cuentas de prueba
INSERT INTO cuentas (fecha_alta, tipo, saldo, cuenta_mercado_pago, cupo_mensual_usado, activa) VALUES
('2024-01-15', 'BASICA', 5000.00, 'MP001', 0.0, TRUE),
('2024-02-20', 'PREMIUM', 10000.00, 'MP002', 45.5, TRUE),
('2024-03-10', 'BASICA', 3000.00, 'MP003', 0.0, TRUE),
('2024-04-05', 'PREMIUM', 15000.00, 'MP004', 120.0, TRUE),
('2024-05-01', 'BASICA', 2000.00, 'MP005', 0.0, FALSE);

-- Asociar usuarios a cuentas (relación muchos a muchos)
-- Usuario 1 (Juan) tiene cuentas 1 y 2
INSERT INTO usuario_cuenta (usuario_id, cuenta_id) VALUES (1, 1), (1, 2);
-- Usuario 2 (Maria) tiene cuenta 2 y 3 (comparte la cuenta 2 con Juan)
INSERT INTO usuario_cuenta (usuario_id, cuenta_id) VALUES (2, 2), (2, 3);
-- Usuario 3 (Carlos) tiene cuenta 4
INSERT INTO usuario_cuenta (usuario_id, cuenta_id) VALUES (3, 4);
-- Usuario 4 (Ana) tiene cuenta 5 (inactiva)
INSERT INTO usuario_cuenta (usuario_id, cuenta_id) VALUES (4, 5);
