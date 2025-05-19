CREATE DATABASE IF NOT EXISTS MediatecaDB;
USE MediatecaDB;

-- Tabla para los tipos de material
CREATE TABLE TipoMaterial (
    id_tipo INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50) NOT NULL UNIQUE
);

-- Tabla principal de Material
CREATE TABLE Material (
    id_material CHAR(10) PRIMARY KEY, 
    titulo VARCHAR(255) NOT NULL,
    unidades_disponibles INT NOT NULL CHECK (unidades_disponibles >= 0),
    id_tipo INT NOT NULL,
    FOREIGN KEY (id_tipo) REFERENCES TipoMaterial(id_tipo) ON DELETE RESTRICT
);

-- Tabla para libros
CREATE TABLE Libro (
    id_material CHAR(10) PRIMARY KEY,
    autor VARCHAR(255) NOT NULL,
    numero_paginas INT NOT NULL CHECK (numero_paginas > 0),
    editorial VARCHAR(255) NOT NULL,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    anio_publicacion YEAR NOT NULL CHECK (anio_publicacion >= 1800),
    FOREIGN KEY (id_material) REFERENCES Material(id_material) ON DELETE CASCADE
);

-- Tabla para revistas
CREATE TABLE Revista (
    id_material CHAR(10) PRIMARY KEY,
    editorial VARCHAR(255) NOT NULL,
    periodicidad VARCHAR(50) NOT NULL,
    fecha_publicacion DATE NOT NULL,
    FOREIGN KEY (id_material) REFERENCES Material(id_material) ON DELETE CASCADE
);

-- Tabla para CDs de audio
CREATE TABLE CD_Audio (
    id_material CHAR(10) PRIMARY KEY,
    artista VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    duracion INT NOT NULL CHECK (duracion > 0),  -- Almacenado en segundos
    numero_canciones INT NOT NULL CHECK (numero_canciones > 0),
    FOREIGN KEY (id_material) REFERENCES Material(id_material) ON DELETE CASCADE
);

-- Tabla para DVDs
CREATE TABLE DVD (
    id_material CHAR(10) PRIMARY KEY,
    director VARCHAR(255) NOT NULL,
    genero VARCHAR(50) NOT NULL,
    duracion INT NOT NULL CHECK (duracion > 0),  -- Almacenado en segundos
    FOREIGN KEY (id_material) REFERENCES Material(id_material) ON DELETE CASCADE
);


-- Tabla Usuarios

CREATE TABLE Usuarios (
	id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    usuario VARCHAR(100) NOT NULL,
    contrasena VARCHAR(255) not null,
    rol ENUM('ADMIN','ALUMNO','PROFESOR') NOT NULL,
    estado BOOLEAN NOT NULL DEFAULT TRUE
);

-- Tabla Prestamos

CREATE TABLE Prestamo (
    id_prestamo INT PRIMARY KEY AUTO_INCREMENT,
    id_usuario INT NOT NULL,
    id_material CHAR(10) NOT NULL,
    fecha_prestamo DATE NOT NULL,
    fecha_devolucion_esperada DATE NOT NULL,
    fecha_devolucion_real DATE,
    dias_mora INT DEFAULT 0,
    mora_calculada DECIMAL(10,2) DEFAULT 0.00,
    devuelto BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_material) REFERENCES Material(id_material)
);

-- Tabla de configuracionSistema

CREATE TABLE ConfiguracionSistema (
	id INT PRIMARY KEY,
    ejemplares_maximos INT NOT NULL DEFAULT 3,
    mora_diaria DECIMAL (5,2) NOT NULL DEFAULT 0.50
);


DELIMITER $$

CREATE TRIGGER calcular_mora
AFTER UPDATE ON Prestamo
FOR EACH ROW
BEGIN
    DECLARE dias INT;
    DECLARE mora_total DECIMAL(10,2);
    DECLARE mora_diaria DECIMAL(5,2);

    SET mora_diaria = 0.50;

    -- Solo calcular si la devolución es posterior a la esperada
    IF NEW.fecha_devolucion_real IS NOT NULL AND NEW.fecha_devolucion_real > NEW.fecha_devolucion_esperada THEN
        SET dias = DATEDIFF(NEW.fecha_devolucion_real, NEW.fecha_devolucion_esperada);
        SET mora_total = dias * mora_diaria;

        UPDATE Prestamo
        SET dias_mora = dias,
            mora_calculada = mora_total
        WHERE id_prestamo = NEW.id_prestamo;
    END IF;
END$$

DELIMITER ;


-- Insertar datos 
INSERT INTO TipoMaterial (nombre) VALUES 
('LIBRO'), 
('REVISTA'), 
('CDA'), 
('DVD');


INSERT INTO Material (id_material, titulo, unidades_disponibles, id_tipo) VALUES 
('LIB00001', 'Cien años de soledad', 5, 1), 
('LIB00002', '1984', 3, 1), 
('REV00001', 'National Geographic - Edición especial', 2, 2), 
('REV00002', 'Science Magazine', 4, 2), 
('CDA00001', 'Thriller - Michael Jackson', 6, 3), 
('CDA00002', 'The Beatles - Abbey Road', 3, 3), 
('DVD00001', 'Interstellar', 7, 4), 
('DVD00002', 'Inception', 5, 4);


INSERT INTO Libro (id_material, autor, numero_paginas, editorial, isbn, anio_publicacion) VALUES 
('LIB00001', 'Gabriel García Márquez', 417, 'Editorial Sudamericana', '978-0307474728', 1967),
('LIB00002', 'George Orwell', 328, 'Secker & Warburg', '978-0451524935', 1949);

INSERT INTO Revista (id_material, editorial, periodicidad, fecha_publicacion) VALUES 
('REV00001', 'National Geographic Society', 'Mensual', '2024-02-01'),
('REV00002', 'American Association for the Advancement of Science', 'Semanal', '2024-03-15');


INSERT INTO CD_Audio (id_material, artista, genero, duracion, numero_canciones) VALUES 
('CDA00001', 'Michael Jackson', 'Pop', 2520, 9),  -- 42 minutos
('CDA00002', 'The Beatles', 'Rock', 2652, 17);   -- 44 minutos

INSERT INTO DVD (id_material, director, genero, duracion) VALUES 
('DVD00001', 'Christopher Nolan', 'Ciencia Ficción', 10140),  -- 169 minutos
('DVD00002', 'Christopher Nolan', 'Acción', 8880);           -- 148 minutos

INSERT INTO ConfiguracionSistema (id, ejemplares_maximos, mora_diaria)
VALUES (1, 3, 0.50);

INSERT INTO Usuarios (usuario, contrasena, rol) VALUES
('admin01', 'adminpass', 'ADMIN'),
('profe_julia', 'profe123', 'PROFESOR'),
('alumno_carlos', 'alumno456', 'ALUMNO');


INSERT INTO Usuarios (usuario, contrasena, rol) VALUES
('root', 'root', 'ADMIN');


-- El alumno hace un préstamo
INSERT INTO Prestamo (id_usuario, fecha_prestamo, fecha_devolucion_esperada)
VALUES (3, '2025-05-01', '2025-05-08');

-- Agrega un libro al detalle
INSERT INTO PrestamoDetalle (id_prestamo, id_material, cantidad)
VALUES (1, 'LIB00001', 1);

-- Simula una devolución A TIEMPO
UPDATE Prestamo
SET fecha_devolucion_real = '2025-05-08'
WHERE id_prestamo = 1;

-- El profesor hace un préstamo
INSERT INTO Prestamo (id_usuario, fecha_prestamo, fecha_devolucion_esperada)
VALUES (2, '2025-05-01', '2025-05-08');

-- Agrega un DVD
INSERT INTO PrestamoDetalle (id_prestamo, id_material, cantidad)
VALUES (2, 'DVD00001', 1);

-- Simula devolución tarde (3 días después)
UPDATE Prestamo
SET fecha_devolucion_real = '2025-05-11'
WHERE id_prestamo = 2;
INSERT INTO usuarios (usuario, contrasena, rol, estado) 
VALUES ('prueba_user', '1234', 'ALUMNO', true);


INSERT INTO material (id_material, titulo, unidades_disponibles, id_tipo)
VALUES ('LIB00001', 'Cien años de soledad', 3, 1);  



SELECT * FROM Usuarios;