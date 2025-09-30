CREATE DATABASE RetoHelloWorld;
USE RetoHelloWorld;

CREATE TABLE Usuario (
    IdUser INT PRIMARY KEY,
    nameUser VARCHAR(100) NOT NULL,
    PasswordUser VARCHAR(100) NOT NULL,
    direction VARCHAR(255),
    email VARCHAR(100) UNIQUE
);

INSERT INTO Usuario () VALUES
(1, 'AnaGomez', 'ana1234', 'Calle Mayor 12, Madrid', 'ana.gomez@example.com'),
(2, 'CarlosRuiz', 'carlos5678', 'Av. Libertad 45, Sevilla', 'carlos.ruiz@example.com'),
(3, 'LuciaMartinez', 'lucia2020', 'Paseo del Prado 8, Valencia', 'lucia.martinez@example.com'),
(4, 'JavierLopez', 'javilopez99', 'Calle Real 33, Bilbao', 'javier.lopez@example.com'),
(5, 'MariaFernandez', 'mariaPass!', 'Camino Verde 21, Zaragoza', 'maria.fernandez@example.com');
