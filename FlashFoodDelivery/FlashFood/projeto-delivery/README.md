# 🍔 FlashFood — Sistema de Delivery

**Projeto Integrado POO + BD**

## 👥 Integrantes do Grupo

| Nome | RA |
|------|----|
| Gustavo de Faria | 41328779 |
| Julia Rodrigues de Lima | 45574332 |
| Arthur Rodrigues dos Santos Vilas Boas | 45968993 |
| João Victor de Lemos Monteiro | 44896140 |
| Lucas Matteus Baptista de Godoy | 45133239 |
| Bruno Santos de Godoy | 45737719 |

## 📋 Tema Escolhido
**Delivery** — Sistema de gerenciamento de pedidos de comida

## 🎯 Objetivo do Sistema

O FlashFood centraliza e organiza o fluxo de pedidos realizados por clientes em restaurantes parceiros, garantindo que todas as etapas — do cadastro de produtos até a entrega final — sejam executadas de forma eficiente.

O sistema oferece controle completo de clientes, entregadores e pedidos em um único ambiente integrado ao banco de dados PostgreSQL, com persistência real de dados e cálculo automático de descontos e taxas.

---

## 📦 Funcionalidades Implementadas

1. Criação e gerenciamento de pedidos de delivery
2. Adição de produtos aos pedidos com controle de quantidade (3 sobrecargas)
3. Cadastro e persistência de clientes no banco de dados
4. Atribuição de entregadores disponíveis aos pedidos
5. Cálculo automático de desconto progressivo + taxa de entrega
6. Histórico auditado de ações do pedido (com timestamp)
7. CRUD completo: clientes, entregadores, produtos e pedidos
8. Exportação de relatório do pedido para arquivo de texto
9. Demonstração de polimorfismo com `ArrayList<Pessoa>`

---

## 🏗️ Estrutura de Pacotes

```
src/
└── br/com/delivery/
    ├── model/                    (Entidades do domínio)
    │   ├── Pessoa.java           ← Superclasse ABSTRATA
    │   ├── Cliente.java          ← extends Pessoa
    │   ├── Entregador.java       ← extends Pessoa
    │   ├── Produto.java
    │   ├── ItemPedido.java
    │   ├── Pedido.java           ← implements Auditavel, Calculavel
    │   └── Restaurante.java
    ├── dao/                      (Acesso ao banco de dados — padrão DAO)
    │   ├── ConexaoBD.java        ← Singleton JDBC PostgreSQL
    │   ├── ClienteDAO.java       ← CRUD completo
    │   ├── EntregadorDAO.java    ← CRUD completo
    │   ├── ProdutoDAO.java       ← CRUD completo
    │   └── PedidoDAO.java        ← CRUD completo + transação
    ├── service/                  (Regras de negócio)
    │   ├── ClienteService.java   ← com sobrecarga de métodos
    │   ├── EntregadorService.java
    │   └── PedidoService.java    ← implements Relatorio
    ├── util/                     (Interfaces e utilitários)
    │   ├── Auditavel.java        ← Interface
    │   ├── Calculavel.java       ← Interface
    │   ├── Relatorio.java        ← Interface
    │   └── MenuUtil.java
    └── Main.java                 (Ponto de entrada)

sql/
└── criar_banco.sql               (Script DDL + dados de teste)
```

---

## 🧩 Conceitos de POO Aplicados

### CP2 — Herança e Polimorfismo

**Hierarquia de classes:**
```
Pessoa (abstrata)
├── Cliente
└── Entregador
```

**@Override e super:**
- `exibirDetalhes()` sobrescrito em `Cliente` e `Entregador`, chamando `super.exibirDetalhes()`
- `exibirPermissoes()` método abstrato implementado em cada subclasse
- `toString()` sobrescrito em todas as entidades

**ArrayList polimórfico** (em `Main.demonstrarPolimorfismo()`):
```java
List<Pessoa> pessoas = new ArrayList<>();
pessoas.add(new Cliente(...));
pessoas.add(new Entregador(...));
for (Pessoa p : pessoas) {
    p.exibirDetalhes();    // chama versão de Cliente ou Entregador
    p.exibirPermissoes();  // método abstrato de cada subclasse
}
```

**Sobrecarga de métodos** em `ClienteService`:
```java
adicionarItem(Pedido, Produto)              // quantidade padrão = 1
adicionarItem(Pedido, Produto, int)         // com quantidade
adicionarItem(Pedido, String, double, int)  // cria produto na hora
```

---

### CP3 — Classes Abstratas + DAO + CRUD

**Classe abstrata `Pessoa`:**
- Atributos `protected` (id, nome, email, telefone)
- Método concreto: `exibirDetalhes()`
- Método abstrato: `exibirPermissoes()` — obrigatório nas subclasses

**Padrão DAO com JDBC:**
- `ConexaoBD`: Singleton com `DriverManager.getConnection()` para PostgreSQL
- `ClienteDAO`, `EntregadorDAO`, `ProdutoDAO`, `PedidoDAO`: CRUD completo com `PreparedStatement`
- `PedidoDAO` usa transação (`setAutoCommit(false)`) para garantir consistência ao salvar pedido + itens

**CRUD completo em cada DAO:**
| Operação | Método |
|----------|--------|
| Create | `inserir(objeto)` |
| Read | `listarTodos()` e `buscarPorId(int)` |
| Update | `atualizar(objeto)` |
| Delete | `excluir(int)` |

---

### CP4 — Interfaces + Entrega Final

**3 interfaces implementadas:**

| Interface | Implementada por | Métodos |
|-----------|-----------------|---------|
| `Auditavel` | `Pedido` | `registrarLog()`, `obterHistorico()` |
| `Calculavel` | `Pedido` | `calcularTotal()`, `calcularDesconto()`, `calcularValorFinal()` |
| `Relatorio` | `PedidoService` | `gerarRelatorio()`, `exportarParaArquivo()` |

---

## 🔄 Regra de Negócio Complexa — Desconto Progressivo

Implementada em `Pedido.calcularDesconto()`:

| Subtotal | Desconto |
|----------|----------|
| Acima de R$ 300,00 | 15% |
| Acima de R$ 200,00 | 10% |
| Acima de R$ 100,00 | 5% |
| Até R$ 100,00 | 0% |

**Taxa de entrega fixa:** R$ 8,00

**Fórmula:** `Valor Final = Subtotal − Desconto + Taxa de Entrega`

---

## 🗄️ Banco de Dados

**SGBD:** PostgreSQL  
**Banco:** `flashfood_db`

**Tabelas:**
- `clientes` — dados dos clientes
- `entregadores` — dados e status dos entregadores
- `produtos` — cardápio com preços
- `pedidos` — pedidos com valores calculados
- `itens_pedido` — itens de cada pedido (ON DELETE CASCADE)

**Como configurar:**
1. Instale o PostgreSQL
2. Crie o banco: `CREATE DATABASE flashfood_db;`
3. Execute o script: `psql -U postgres -d flashfood_db -f sql/criar_banco.sql`
4. Ajuste usuário/senha em `ConexaoBD.java` se necessário (padrão: `postgres`/`postgres`)
5. Adicione o JAR do driver JDBC ao classpath do projeto: [postgresql-42.x.x.jar](https://jdbc.postgresql.org/download/)

---

## ▶️ Como Executar

1. Configure o banco de dados (passos acima)
2. Abra o projeto na sua IDE (IntelliJ, Eclipse)
3. Execute `br.com.delivery.Main`

### Fluxo recomendado no menu:
| Opção | Ação |
|-------|------|
| 1 | Criar pedido (cadastra cliente no banco) |
| 2 | Adicionar itens (repita quantas vezes quiser) |
| 3 | Atribuir entregador (cadastra entregador no banco) |
| 4 | Finalizar pedido (calcula e salva no banco) |
| 5 | Ver resumo financeiro |
| 6 | Ver histórico auditado |
| 7 | CRUD de clientes |
| 8 | CRUD de entregadores |
| 9 | Listar pedidos salvos |

---

## 🎬 Vídeo Demonstrativo
[https://www.youtube.com/watch?v=d1BgFR6TD1Q]
