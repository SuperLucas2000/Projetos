-- =====================================================
-- FlashFood — Script de criação do banco de dados
-- PostgreSQL
-- =====================================================

-- Cria o banco (executar separadamente se necessário)
-- CREATE DATABASE flashfood_db;

-- =====================================================
-- TABELAS
-- =====================================================

CREATE TABLE IF NOT EXISTS clientes (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telefone  VARCHAR(20),
    endereco  VARCHAR(200)
);

CREATE TABLE IF NOT EXISTS entregadores (
    id        SERIAL PRIMARY KEY,
    nome      VARCHAR(100) NOT NULL,
    email     VARCHAR(100) NOT NULL UNIQUE,
    telefone  VARCHAR(20),
    veiculo   VARCHAR(50),
    status    VARCHAR(20) DEFAULT 'DISPONÍVEL'
);

CREATE TABLE IF NOT EXISTS produtos (
    id     SERIAL PRIMARY KEY,
    nome   VARCHAR(100) NOT NULL,
    preco  NUMERIC(10, 2) NOT NULL CHECK (preco >= 0)
);

CREATE TABLE IF NOT EXISTS pedidos (
    id             SERIAL PRIMARY KEY,
    cliente_id     INTEGER NOT NULL REFERENCES clientes(id) ON DELETE RESTRICT,
    entregador_id  INTEGER REFERENCES entregadores(id) ON DELETE SET NULL,
    subtotal       NUMERIC(10, 2) DEFAULT 0,
    desconto       NUMERIC(10, 2) DEFAULT 0,
    taxa_entrega   NUMERIC(10, 2) DEFAULT 8.00,
    valor_total    NUMERIC(10, 2) DEFAULT 0,
    status         VARCHAR(20) DEFAULT 'ABERTO',
    criado_em      TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS itens_pedido (
    id             SERIAL PRIMARY KEY,
    pedido_id      INTEGER NOT NULL REFERENCES pedidos(id) ON DELETE CASCADE,
    produto_nome   VARCHAR(100) NOT NULL,
    produto_preco  NUMERIC(10, 2) NOT NULL,
    quantidade     INTEGER NOT NULL CHECK (quantidade > 0)
);

-- =====================================================
-- DADOS DE TESTE
-- =====================================================

INSERT INTO clientes (nome, email, telefone, endereco) VALUES
    ('Ana Silva',     'ana@email.com',     '(11) 91111-1111', 'Rua das Flores, 10 - SP'),
    ('Carlos Souza',  'carlos@email.com',  '(11) 92222-2222', 'Av. Paulista, 500 - SP'),
    ('Mariana Lima',  'mari@email.com',    '(11) 93333-3333', 'Rua do Pão, 22 - SP');

INSERT INTO entregadores (nome, email, telefone, veiculo, status) VALUES
    ('João Moto',     'joao@email.com',    '(11) 94444-4444', 'Moto Honda CB 300', 'DISPONÍVEL'),
    ('Pedro Bike',    'pedro@email.com',   '(11) 95555-5555', 'Bicicleta Elétrica', 'DISPONÍVEL'),
    ('Lucas Van',     'lucas@email.com',   '(11) 96666-6666', 'Van Fiat Ducato',    'EM_ENTREGA');

INSERT INTO produtos (nome, preco) VALUES
    ('X-Burguer',      25.90),
    ('Pizza Margherita', 45.00),
    ('Coca-Cola 2L',    12.50),
    ('Batata Frita G',  18.00),
    ('Combo Família',   89.90);
