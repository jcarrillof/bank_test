CREATE TABLE IF NOT EXISTS movimiento (
    id SERIAL NOT NULL PRIMARY KEY,
    fecha TIMESTAMP NOT NULL,
    tipo_movimiento VARCHAR NOT NULL,
    valor FLOAT NOT NULL,
    saldo FLOAT NOT NULL,
    cuenta_id INTEGER NOT NULL REFERENCES cuenta (id)
);