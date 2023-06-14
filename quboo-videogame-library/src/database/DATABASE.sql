CREATE DATABASE quboo;

USE quboo;

CREATE TABLE Rangos (
    Id_rango INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_rango VARCHAR(255),
    Info_rango TEXT
);

CREATE TABLE Usuarios (
    Id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_usuario VARCHAR(255),
    Contrasena_usuario VARCHAR(255),
    Descripcion_usuario TEXT,
    Monedas_juego INT DEFAULT 0,
    Id_rango INT,
    FOREIGN KEY (Id_rango) REFERENCES Rangos(Id_rango)
);

CREATE TABLE Juegos (
    Id_juego INT AUTO_INCREMENT PRIMARY KEY,
    Nombre_juego VARCHAR(255),
    Puntos_juego INT,
    Comprado BIT,
    Id_usuario INT,
    FOREIGN KEY (Id_usuario) REFERENCES Usuarios(Id_usuario)
);

INSERT INTO Rangos (Nombre_rango, Info_rango) VALUES
('Papel', 'Este es el nivel mas bajo...'),
('Carton', 'Un nivel mas alto que papel.'),
('Madera', 'Un nivel mas fuerte que carton.'),
('Piedra', 'Un nivel mas resistente que madera.'),
('Omnipotencia', 'Ahora mismo eres dios.');

DELIMITER //
CREATE TRIGGER asignar_rango_bajo
BEFORE INSERT ON Usuarios
FOR EACH ROW
BEGIN
   SET NEW.Id_rango = (SELECT MIN(Id_rango) FROM Rangos);
END;//
DELIMITER ;

DELIMITER //
CREATE TRIGGER crear_juegos_usuario
AFTER INSERT ON Usuarios
FOR EACH ROW
BEGIN
   INSERT INTO Juegos(Nombre_juego, Puntos_juego, Comprado, Id_usuario) VALUES ('pong', 0, 0, NEW.Id_usuario);
   INSERT INTO Juegos(Nombre_juego, Puntos_juego, Comprado, Id_usuario) VALUES ('snake', 0, 0, NEW.Id_usuario);
   INSERT INTO Juegos(Nombre_juego, Puntos_juego, Comprado, Id_usuario) VALUES ('pacman', 0, 0, NEW.Id_usuario);
   INSERT INTO Juegos(Nombre_juego, Puntos_juego, Comprado, Id_usuario) VALUES ('jumpman', 0, 0, NEW.Id_usuario);
   INSERT INTO Juegos(Nombre_juego, Puntos_juego, Comprado, Id_usuario) VALUES ('typingtest', 0, 0, NEW.Id_usuario);
END;//
DELIMITER ;