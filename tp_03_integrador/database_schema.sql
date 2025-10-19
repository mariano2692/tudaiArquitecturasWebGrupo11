-- Script SQL para crear las tablas del Entregable 3
-- Base de datos: entregable3

-- Crear la base de datos si no existe
CREATE DATABASE IF NOT EXISTS entregable3;
USE entregable3;

-- Tabla CARRERA
CREATE TABLE carrera (
    id INT NOT NULL,
    nombre VARCHAR(255) NOT NULL,
    duracion INT NOT NULL,
    PRIMARY KEY (id)
);

-- Tabla ESTUDIANTE
CREATE TABLE estudiante (
    dni INT NOT NULL,
    nombres VARCHAR(255),
    apellido VARCHAR(255),
    edad INT,
    genero VARCHAR(50),
    ciudad_residencia VARCHAR(255),
    lu BIGINT,
    PRIMARY KEY (dni)
);

-- Tabla INSCRIPCION
CREATE TABLE inscripcion (
    id INT AUTO_INCREMENT,
    antiguedad INT,
    anio_inscripcion DATE NOT NULL,
    anio_egreso DATE,
    graduado BOOLEAN,
    id_carrera INT NOT NULL,
    id_estudiante INT NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (id_carrera) REFERENCES carrera(id),
    FOREIGN KEY (id_estudiante) REFERENCES estudiante(dni)
);

-- Índices para mejorar el rendimiento
CREATE INDEX idx_inscripcion_carrera ON inscripcion(id_carrera);
CREATE INDEX idx_inscripcion_estudiante ON inscripcion(id_estudiante);
CREATE INDEX idx_inscripcion_anio ON inscripcion(anio_inscripcion);
CREATE INDEX idx_estudiante_lu ON estudiante(lu);

-- Comentarios sobre las tablas
ALTER TABLE carrera COMMENT = 'Tabla que almacena información de las carreras';
ALTER TABLE estudiante COMMENT = 'Tabla que almacena información de los estudiantes';
ALTER TABLE inscripcion COMMENT = 'Tabla que relaciona estudiantes con carreras y almacena información de inscripciones';

-- Comentarios sobre las columnas clave
ALTER TABLE carrera MODIFY COLUMN id INT NOT NULL COMMENT 'Identificador único de la carrera';
ALTER TABLE carrera MODIFY COLUMN nombre VARCHAR(255) NOT NULL COMMENT 'Nombre de la carrera';
ALTER TABLE carrera MODIFY COLUMN duracion INT NOT NULL COMMENT 'Duración de la carrera en años';

ALTER TABLE estudiante MODIFY COLUMN dni INT NOT NULL COMMENT 'DNI del estudiante (clave primaria)';
ALTER TABLE estudiante MODIFY COLUMN nombres VARCHAR(255) COMMENT 'Nombres del estudiante';
ALTER TABLE estudiante MODIFY COLUMN apellido VARCHAR(255) COMMENT 'Apellido del estudiante';
ALTER TABLE estudiante MODIFY COLUMN edad INT COMMENT 'Edad del estudiante';
ALTER TABLE estudiante MODIFY COLUMN genero VARCHAR(50) COMMENT 'Género del estudiante';
ALTER TABLE estudiante MODIFY COLUMN ciudad_residencia VARCHAR(255) COMMENT 'Ciudad de residencia del estudiante';
ALTER TABLE estudiante MODIFY COLUMN lu BIGINT COMMENT 'Número de legajo universitario';

ALTER TABLE inscripcion MODIFY COLUMN id INT AUTO_INCREMENT COMMENT 'Identificador único de la inscripción';
ALTER TABLE inscripcion MODIFY COLUMN antiguedad INT COMMENT 'Antigüedad en años de la inscripción';
ALTER TABLE inscripcion MODIFY COLUMN anio_inscripcion DATE NOT NULL COMMENT 'Año de inscripción';
ALTER TABLE inscripcion MODIFY COLUMN anio_egreso DATE COMMENT 'Año de egreso (si aplica)';
ALTER TABLE inscripcion MODIFY COLUMN graduado BOOLEAN COMMENT 'Indica si el estudiante se graduó';
ALTER TABLE inscripcion MODIFY COLUMN id_carrera INT NOT NULL COMMENT 'ID de la carrera (FK)';
ALTER TABLE inscripcion MODIFY COLUMN id_estudiante INT NOT NULL COMMENT 'DNI del estudiante (FK)';

