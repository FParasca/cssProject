INSERT INTO LOCALIDADE (nome_localidade) VALUES
                                             ('Lisboa'),
                                             ('Porto'),
                                             ('Braga'),
                                             ('Faro'),
                                             ('Coimbra');


INSERT INTO utilizador (nome) VALUES
                                  ('Ana Silva'),
                                  ('Bruno Costa'),
                                  ('Carla Dias');

INSERT INTO previsao (id_localidade, data, temperatura, condicao) VALUES
                                                                      (1, '2025-11-03', 22.5, 'Sol'),
                                                                      (1, '2025-11-02', 21.0, 'Nuvens'),
                                                                      (4, '2025-11-01', 20.0, 'Sol'),
                                                                      (2, '2025-11-03', 18.0, 'Chuva'),
                                                                      (2, '2025-11-02', 17.5, 'Nuvens'),
                                                                      (4, '2025-11-03', 25.0, 'Sol');

INSERT INTO consulta (id_utilizador, id_localidade, timestamp) VALUES
                                                                   (1, 1, '2025-11-03 08:30:00'),
                                                                   (1, 2, '2025-11-03 09:15:00'),
                                                                   (2, 1, '2025-11-03 10:00:00'),
                                                                   (1, 1, '2025-11-03 10:10:00');