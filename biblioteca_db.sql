
-- ==========================================
-- BASE DE DATOS BIBLIOTECA
-- ==========================================

CREATE DATABASE IF NOT EXISTS biblioteca_db;
USE biblioteca_db;

-- TABLA LIBROS
CREATE TABLE IF NOT EXISTS libros (
    id CHAR(36) PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    autor VARCHAR(150) NOT NULL,
    genero VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- TABLA USUARIOS
CREATE TABLE IF NOT EXISTS usuarios (
    idSocio VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- TABLA ADMINISTRADORES
CREATE TABLE IF NOT EXISTS administradores (
    idAdmin VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- TABLA BIBLIOTECARIOS
CREATE TABLE IF NOT EXISTS bibliotecarios (
    legajo VARCHAR(50) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    dni VARCHAR(20) NOT NULL,
    direccion VARCHAR(200) NOT NULL,
    contacto VARCHAR(100) NOT NULL
) ENGINE=InnoDB;

-- TABLA EJEMPLARES
CREATE TABLE IF NOT EXISTS ejemplares (
    id CHAR(36) PRIMARY KEY,
    libro_id CHAR(36) NOT NULL,
    estado VARCHAR(30) NOT NULL,
    CONSTRAINT fk_ejemplar_libro
        FOREIGN KEY (libro_id)
        REFERENCES libros(id)
) ENGINE=InnoDB;

-- TABLA PRESTAMOS
CREATE TABLE IF NOT EXISTS prestamos (
    idPrestamo CHAR(36) PRIMARY KEY,
    ejemplar CHAR(36) NOT NULL,
    usuario VARCHAR(50) NOT NULL,
    fechaPrestamo DATETIME NOT NULL,
    fechaVencimiento DATETIME NOT NULL,
    fechaDevolucion DATETIME NULL,
    estadoPrestamo VARCHAR(30) NOT NULL,
    CONSTRAINT fk_prestamo_ejemplar
        FOREIGN KEY (ejemplar)
        REFERENCES ejemplares(id),
    CONSTRAINT fk_prestamo_usuario
        FOREIGN KEY (usuario)
        REFERENCES usuarios(idSocio)
) ENGINE=InnoDB;


-- ==========================================
-- DATOS DE PRUEBA: LIBROS
-- ==========================================

INSERT IGNORE INTO libros (id, nombre, autor, genero)
VALUES
('10000000-0000-4000-8000-000000000001',
 'El Principito', 'Antoine de Saint-Exupery', 'Novela'),

('10000000-0000-4000-8000-000000000002',
 '1984', 'George Orwell', 'Distopia'),

('10000000-0000-4000-8000-000000000003',
 'Don Quijote de la Mancha', 'Miguel de Cervantes', 'Novela'),

('10000000-0000-4000-8000-000000000004',
 'Harry Potter y la piedra filosofal', 'J. K. Rowling', 'Fantasia');


-- ==========================================
-- DATOS DE PRUEBA: USUARIOS
-- ==========================================

INSERT IGNORE INTO usuarios
(idSocio, nombre, dni, direccion, contacto)
VALUES
('SOC001', 'Juan Perez', '40111222',
 'Av. Colon 1500', '2235001001'),

('SOC002', 'Maria Gomez', '42222333',
 'San Martin 2300', '2235001002'),

('SOC003', 'Pedro Lopez', '43333444',
 'Independencia 900', '2235001003');


-- ==========================================
-- DATOS DE PRUEBA: ADMINISTRADORES
-- ==========================================

INSERT IGNORE INTO administradores
(idAdmin, nombre, dni, direccion, contacto)
VALUES
('ADM001', 'Carlos Fernandez', '30111222',
 'Guemes 1200', '2236001001'),

('ADM002', 'Laura Martinez', '32222333',
 'Alberti 1800', '2236001002');


-- ==========================================
-- DATOS DE PRUEBA: BIBLIOTECARIOS
-- ==========================================

INSERT IGNORE INTO bibliotecarios
(legajo, nombre, dni, direccion, contacto)
VALUES
('BIB001', 'Ana Rodriguez', '35111222',
 'Luro 3200', '2237001001'),

('BIB002', 'Martin Sanchez', '36222333',
 'Moreno 2100', '2237001002');


-- ==========================================
-- DATOS DE PRUEBA: EJEMPLARES
-- ==========================================

INSERT IGNORE INTO ejemplares (id, libro_id, estado)
VALUES
('20000000-0000-4000-8000-000000000001',
 '10000000-0000-4000-8000-000000000001',
 'DISPONIBLE'),

('20000000-0000-4000-8000-000000000002',
 '10000000-0000-4000-8000-000000000001',
 'PRESTADO'),

('20000000-0000-4000-8000-000000000003',
 '10000000-0000-4000-8000-000000000002',
 'DISPONIBLE'),

('20000000-0000-4000-8000-000000000004',
 '10000000-0000-4000-8000-000000000003',
 'PRESTADO'),

('20000000-0000-4000-8000-000000000005',
 '10000000-0000-4000-8000-000000000004',
 'DISPONIBLE');


-- ==========================================
-- DATOS DE PRUEBA: PRESTAMOS
-- ==========================================

INSERT IGNORE INTO prestamos
(idPrestamo, ejemplar, usuario, fechaPrestamo,
 fechaVencimiento, fechaDevolucion, estadoPrestamo)
VALUES

-- Prestamo activo de Juan
('30000000-0000-4000-8000-000000000001',
 '20000000-0000-4000-8000-000000000002',
 'SOC001',
 '2026-11-15 10:00:00',
 '2026-11-25 10:00:00',
 NULL,
 'ACTIVO'),

-- Prestamo activo de Maria
('30000000-0000-4000-8000-000000000002',
 '20000000-0000-4000-8000-000000000004',
 'SOC002',
 '2026-11-16 12:00:00',
 '2026-11-26 12:00:00',
 NULL,
 'ACTIVO'),

-- Prestamo devuelto por Pedro
('30000000-0000-4000-8000-000000000003',
 '20000000-0000-4000-8000-000000000003',
 'SOC003',
 '2026-11-01 09:00:00',
 '2026-11-11 09:00:00',
 '2026-11-09 15:00:00',
 'DEVUELTO');


-- ==========================================
-- CONSULTAS DE VERIFICACION
-- ==========================================

SHOW TABLES;

SELECT * FROM libros;
SELECT * FROM usuarios;
SELECT * FROM administradores;
SELECT * FROM bibliotecarios;
SELECT * FROM ejemplares;
SELECT * FROM prestamos;
