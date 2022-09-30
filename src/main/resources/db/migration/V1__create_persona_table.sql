CREATE TABLE IF NOT EXISTS persona (
    id SERIAL NOT NULL PRIMARY KEY,
    nombre VARCHAR NOT NULL,
    genero VARCHAR NOT NULL,
    edad INT NOT NULL,
    identificacion VARCHAR NOT NULL,
    direccion TEXT NOT NULL,
    telefono VARCHAR NOT NULL
);