-- BIBLIOTECA - esquema con identificadores INT generados en Java
-- ATENCION: recrea las seis tablas y BORRA sus datos actuales.
-- Realizar una copia de seguridad antes de ejecutarlo.

CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- Eliminar primero las tablas hijas por sus claves foraneas
DROP TABLE IF EXISTS prestamos;
DROP TABLE IF EXISTS ejemplares;
DROP TABLE IF EXISTS bibliotecarios;
DROP TABLE IF EXISTS administradores;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS libros;

CREATE TABLE libros (
    id INT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    genero VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE usuarios (
    idSocio INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE administradores (
    idAdmin INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE bibliotecarios (
    legajo INT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE ejemplares (
    id INT PRIMARY KEY,
    libro_id INT NOT NULL,
    estado VARCHAR(30) NOT NULL,
    CONSTRAINT fk_ejemplar_libro
        FOREIGN KEY (libro_id) REFERENCES libros(id)
) ENGINE=InnoDB;

CREATE TABLE prestamos (
    idPrestamo INT PRIMARY KEY,
    ejemplar INT NOT NULL,
    usuario INT NOT NULL,
    fechaPrestamo DATETIME NOT NULL,
    fechaVencimiento DATETIME NOT NULL,
    fechaDevolucion DATETIME NULL,
    estadoPrestamo VARCHAR(30) NOT NULL,
    CONSTRAINT fk_prestamo_ejemplar
        FOREIGN KEY (ejemplar) REFERENCES ejemplares(id),
    CONSTRAINT fk_prestamo_usuario
        FOREIGN KEY (usuario) REFERENCES usuarios(idSocio)
) ENGINE=InnoDB;

-- DATOS DE PRUEBA (IDs NUMERICOS)
INSERT INTO libros (id, nombre, autor, genero) VALUES
(1, 'El Principito', 'Antoine de Saint-Exupery', 'Novela'),
(2, '1984', 'George Orwell', 'Distopia'),
(3, 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela'),
(4, 'Harry Potter y la piedra filosofal', 'J. K. Rowling', 'Fantasia');

INSERT INTO usuarios (idSocio, nombre, dni, direccion, contacto) VALUES
(1, 'Juan Perez', '40111222', 'Av. Colon 1500', '2235001001'),
(2, 'Maria Gomez', '42222333', 'San Martin 2300', '2235001002'),
(3, 'Pedro Lopez', '43333444', 'Independencia 900', '2235001003');

INSERT INTO administradores (idAdmin, nombre, dni, direccion, contacto) VALUES
(1, 'Carlos Fernandez', '30111222', 'Guemes 1200', '2236001001'),
(2, 'Laura Martinez', '32222333', 'Alberti 1800', '2236001002');

INSERT INTO bibliotecarios (legajo, nombre, dni, direccion, contacto) VALUES
(1, 'Ana Rodriguez', '35111222', 'Luro 3200', '2237001001'),
(2, 'Martin Sanchez', '36222333', 'Moreno 2100', '2237001002');

INSERT INTO ejemplares (id, libro_id, estado) VALUES
(1, 1, 'DISPONIBLE'),
(2, 1, 'PRESTADO'),
(3, 2, 'DISPONIBLE'),
(4, 3, 'PRESTADO'),
(5, 4, 'DISPONIBLE');

INSERT INTO prestamos
(idPrestamo, ejemplar, usuario, fechaPrestamo, fechaVencimiento, fechaDevolucion, estadoPrestamo)
VALUES
(1, 2, 1, '2026-11-15 10:00:00', '2026-11-25 10:00:00', NULL, 'ACTIVO'),
(2, 4, 2, '2026-11-16 12:00:00', '2026-11-26 12:00:00', NULL, 'ACTIVO'),
(3, 3, 3, '2026-11-01 09:00:00', '2026-11-11 09:00:00', '2026-11-09 15:00:00', 'DEVUELTO');

-- VERIFICACION
SHOW TABLES;
SELECT * FROM libros;
SELECT * FROM usuarios;
SELECT * FROM administradores;
SELECT * FROM bibliotecarios;
SELECT * FROM ejemplares;
SELECT * FROM prestamos;
