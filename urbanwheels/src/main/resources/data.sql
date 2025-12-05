-- =================================================================================
-- 1. CRIAR ESTAÇÕES
-- Responde a: "Quais são as estações com mais bicicletas disponíveis?"
-- =================================================================================
INSERT INTO station (name, latitude, longitude, capacity) VALUES ('Estação Rossio', 38.7139, -9.1394, 10);       -- ID 1
INSERT INTO station (name, latitude, longitude, capacity) VALUES ('Estação Marquês', 38.7256, -9.1499, 15);      -- ID 2
INSERT INTO station (name, latitude, longitude, capacity) VALUES ('Estação Cais do Sodré', 38.7057, -9.1438, 20); -- ID 3

-- =================================================================================
-- 2. CRIAR BICICLETAS
-- Responde a: "Quais são as estações com mais bicicletas disponíveis?"
-- =================================================================================

-- Estação Rossio (ID 1) terá 3 bicicletas (Vai ganhar no ranking)
INSERT INTO bike (modelo, status, current_station_id) VALUES ('Mountain Bike X1', 'AVAILABLE', 1); -- ID 1
INSERT INTO bike (modelo, status, current_station_id) VALUES ('City Cruiser A', 'AVAILABLE', 1);   -- ID 2
INSERT INTO bike (modelo, status, current_station_id) VALUES ('Speedster Z', 'AVAILABLE', 1);      -- ID 3

-- Estação Marquês (ID 2) terá 1 bicicleta
INSERT INTO bike (modelo, status, current_station_id) VALUES ('Electric Bolt', 'AVAILABLE', 2);    -- ID 4

-- Bicicleta em Manutenção (sem estação) - Para a pergunta da manutenção
INSERT INTO bike (modelo, status, current_station_id) VALUES ('Broken Bike', 'MAINTENANCE', NULL); -- ID 5

-- Bicicleta em Uso (sem estação)
INSERT INTO bike (modelo, status, current_station_id) VALUES ('Rented Bike', 'BEING_USED', NULL);  -- ID 6

-- =================================================================================
-- 3. CRIAR UTILIZADORES (Herança JOINED: Primeiro na tabela pai, depois na filha)
-- =================================================================================

-- CLIENTE 1: João Silva (O "Viajante")
INSERT INTO user_abs_class (name, email) VALUES ('João Silva', 'joao.silva@email.com'); -- ID 1 (Gerado auto)
-- Subscription 1 = ANUAL (Ordinal)
INSERT INTO client (id, subscription) VALUES ((SELECT id FROM user_abs_class WHERE email='joao.silva@email.com'), 1);

-- CLIENTE 2: Maria Santos (Poucas viagens)
INSERT INTO user_abs_class (name, email) VALUES ('Maria Santos', 'maria.santos@email.com'); -- ID 2
-- Subscription 0 = OCASIONAL
INSERT INTO client (id, subscription) VALUES ((SELECT id FROM user_abs_class WHERE email='maria.santos@email.com'), 0);

-- ADMIN 1: Admin Chefe (Muitas manutenções)
INSERT INTO user_abs_class (name, email) VALUES ('Admin Chefe', 'admin@urbanwheels.com'); -- ID 3
INSERT INTO admin (id) VALUES ((SELECT id FROM user_abs_class WHERE email='admin@urbanwheels.com'));

-- ADMIN 2: Admin Estagiário
INSERT INTO user_abs_class (name, email) VALUES ('Admin Junior', 'junior@urbanwheels.com'); -- ID 4
INSERT INTO admin (id) VALUES ((SELECT id FROM user_abs_class WHERE email='junior@urbanwheels.com'));

-- =================================================================================
-- 4. CRIAR VIAGENS (TRIPS)
-- Responde a:
-- "Utilizadores com mais viagens no último mês?" (João deve ganhar)
-- "Estação mais utilizada?" (Rossio deve ganhar para o João)
-- "Viagens entre estações específicas?" (Rossio -> Marquês)
-- =================================================================================

-- Viagem 1: João, do Rossio para o Marquês (Ontem)
INSERT INTO trip (start_time, end_time, bike_id, client_id, start_station_id, end_station_id)
VALUES (NOW() - INTERVAL '1 day', NOW() - INTERVAL '23 hours', 1, 1, 1, 2);

-- Viagem 2: João, do Rossio para o Cais do Sodré (2 dias atrás)
INSERT INTO trip (start_time, end_time, bike_id, client_id, start_station_id, end_station_id)
VALUES (NOW() - INTERVAL '2 days', NOW() - INTERVAL '1 day', 2, 1, 1, 3);

-- Viagem 3: João, do Marquês para o Rossio (Hoje)
INSERT INTO trip (start_time, end_time, bike_id, client_id, start_station_id, end_station_id)
VALUES (NOW() - INTERVAL '5 hours', NOW() - INTERVAL '4 hours', 4, 1, 2, 1);

-- Viagem 4: Maria, do Cais do Sodré para o Rossio (Hoje)
INSERT INTO trip (start_time, end_time, bike_id, client_id, start_station_id, end_station_id)
VALUES (NOW() - INTERVAL '2 hours', NOW() - INTERVAL '1 hour', 3, 2, 3, 1);

-- Viagem 5: EM ABERTO (Para testar Caso de Uso L - Devolver Bicicleta)
-- Bike ID 6, Cliente João.
INSERT INTO trip (start_time, end_time, bike_id, client_id, start_station_id, end_station_id)
VALUES (NOW(), NULL, 6, 1, 2, NULL);

-- =================================================================================
-- 5. CRIAR MANUTENÇÕES
-- Responde a:
-- "Bicicleta com mais tempo em manutenção no último ano?" (Bike 5 deve ganhar)
-- "Admins com mais operações no último mês?" (Admin Chefe deve ganhar)
-- =================================================================================

-- Manutenção 1: Bike 5, feita pelo Admin Chefe (100 horas) - Mês passado
INSERT INTO maintenance (date, descricao, custo, time_in_maintenance, admin_id, bike_id)
VALUES (NOW() - INTERVAL '10 days', 'Troca de Pneus', 50.0, 100, 3, 5);

-- Manutenção 2: Bike 5, feita pelo Admin Chefe (200 horas) - Semana passada
INSERT INTO maintenance (date, descricao, custo, time_in_maintenance, admin_id, bike_id)
VALUES (NOW() - INTERVAL '5 days', 'Motor Avariado', 150.0, 200, 3, 5);

-- Manutenção 3: Bike 1, feita pelo Admin Junior (10 horas)
INSERT INTO maintenance (date, descricao, custo, time_in_maintenance, admin_id, bike_id)
VALUES (NOW() - INTERVAL '2 days', 'Lubrificação', 10.0, 10, 4, 1);