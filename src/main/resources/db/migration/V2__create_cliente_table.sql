CREATE TABLE IF NOT EXISTS cliente (
    id SERIAL NOT NULL PRIMARY KEY,
    cliente_id VARCHAR NOT NULL,
    contrasena VARCHAR NOT NULL,
    estado VARCHAR NOT NULL,
    persona_id INTEGER NOT NULL REFERENCES persona (id)
);