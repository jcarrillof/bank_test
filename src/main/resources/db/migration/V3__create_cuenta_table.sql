CREATE TABLE IF NOT EXISTS cuenta (
    id SERIAL NOT NULL PRIMARY KEY,
    numero_cuenta VARCHAR NOT NULL,
    tipo_cuenta VARCHAR NOT NULL,
    saldo_inicial FLOAT NOT NULL,
    estado VARCHAR NOT NULL,
    cliente_id INTEGER NOT NULL REFERENCES cliente (id)
);