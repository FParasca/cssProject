-- Create your tables here and add also some data for testing
DROP TABLE IF EXISTS consulta;
DROP TABLE IF EXISTS previsao;
DROP TABLE IF EXISTS localidade;
DROP TABLE IF EXISTS utilizador;


CREATE TABLE IF NOT EXISTS utilizador(
                                         id SERIAL PRIMARY KEY,
                                         nome VARCHAR(50)
    );

CREATE TABLE IF NOT EXISTS localidade (
                                          id SERIAL PRIMARY KEY,
                                          nome VARCHAR(50) UNIQUE NOT NULL
    );

CREATE TABLE IF NOT EXISTS previsao (
                                        id_localidade INT,
                                        data DATE NOT NULL,
                                        temperatura DOUBLE PRECISION,
                                        condicao VARCHAR(20),

    FOREIGN KEY (id_localidade) REFERENCES localidade(id)

    );

CREATE TABLE IF NOT EXISTS consulta (
                                        id SERIAL PRIMARY KEY,
                                        id_utilizador INT,
                                        id_localidade INT,
                                        timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                        FOREIGN KEY (id_utilizador) REFERENCES utilizador (id),
    FOREIGN KEY (id_localidade) REFERENCES localidade(id)
    );
