-- Script de verificación para comprobar que las tablas coinciden con las entidades JPA
-- Ejecutar después de crear las tablas para verificar la estructura

USE entregable3;

-- Verificar estructura de la tabla CARRERA
DESCRIBE carrera;

-- Verificar estructura de la tabla ESTUDIANTE  
DESCRIBE estudiante;

-- Verificar estructura de la tabla INSCRIPCION
DESCRIBE inscripcion;

-- Verificar las claves foráneas
SELECT 
    TABLE_NAME,
    COLUMN_NAME,
    CONSTRAINT_NAME,
    REFERENCED_TABLE_NAME,
    REFERENCED_COLUMN_NAME
FROM 
    INFORMATION_SCHEMA.KEY_COLUMN_USAGE 
WHERE 
    REFERENCED_TABLE_SCHEMA = 'entregable3' 
    AND REFERENCED_TABLE_NAME IS NOT NULL;

-- Verificar índices
SHOW INDEX FROM carrera;
SHOW INDEX FROM estudiante;
SHOW INDEX FROM inscripcion;

-- Verificar que las tablas existen
SHOW TABLES;

-- Verificar el motor de base de datos
SELECT ENGINE FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'entregable3';

