package br.com.delivery;

import br.com.delivery.model.Cliente;
import br.com.delivery.model.Entregador;
import br.com.delivery.model.Pedido;
import br.com.delivery.model.Pessoa;
import br.com.delivery.model.Produto;
import br.com.delivery.service.ClienteService;
import br.com.delivery.service.EntregadorService;
import br.com.delivery.service.PedidoService;
import br.com.delivery.util.MenuUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Ponto de entrada do Sistema FlashFood — Delivery.
 *
 * Demonstra os conceitos de POO aplicados:
 * - Herança e polimorfismo (Pessoa → Cliente, Entregador)
 * - Classe abstrata com método abstrato (exibirPermissoes)
 * - Interfaces (Auditavel, Calculavel, Relatorio)
 * - Sobrecarga de métodos (adicionarItem)
 * - ArrayList polimórfico (List<Pessoa>)
 *
 * Todos os dados são mantidos em memória durante a execução.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class Main {

    private static final ClienteService    clienteService    = new ClienteService();
    private static final PedidoService     pedidoService     = new PedidoService();
    private static final EntregadorService entregadorService = new EntregadorService();

    // Listas em memória
    private static final List<Cliente>    clientes    = new ArrayList<>();
    private static final List<Entregador> entregadores = new ArrayList<>();
    private static final List<Pedido>     pedidos      = new ArrayList<>();

    // Contradores de ID em memória
    private static int proximoIdCliente    = 1;
    private static int proximoIdEntregador = 1;
    private static int proximoIdPedido     = 1;

    // Pedido em aberto na sessão atual
    private static Pedido pedidoAtual = null;

    public static void main(String[] args) {
        MenuUtil.exibirCabecalho();

        boolean continuar = true;
        while (continuar) {
            MenuUtil.exibirMenuPrincipal();

            int opcao = MenuUtil.lerOpcao("Escolha uma opção: ");

            switch (opcao) {
                case 1:  criarPedido();          break;
                case 2:  adicionarItem();         break;
                case 3:  atribuirEntregador();    break;
                case 4:  finalizarPedido();       break;
                case 5:  exibirResumo();          break;
                case 6:  exibirHistorico();       break;
                case 7:  gerenciarClientes();     break;
                case 8:  gerenciarEntregadores(); break;
                case 9:  listarPedidos();         break;
                case 0:  continuar = false; encerrar(); break;
                default: MenuUtil.exibirAviso("Opção inválida!"); MenuUtil.pausar();
            }
        }
    }

    // ==================== AÇÕES DO MENU ====================

    private static void criarPedido() {
        MenuUtil.exibirTitulo("Novo Pedido");

        Cliente cliente = new Cliente();
        cliente.setId(proximoIdCliente++);
        cliente.setNome(MenuUtil.lerStringNaoVazia("Nome do cliente: "));
        cliente.setEmail(MenuUtil.lerStringNaoVazia("E-mail: "));
        cliente.setTelefone(MenuUtil.lerStringNaoVazia("Telefone: "));
        cliente.setEndereco(MenuUtil.lerStringNaoVazia("Endereço de entrega: "));

        clientes.add(cliente);

        pedidoAtual = clienteService.criarPedido(cliente);
        pedidoAtual.setId(proximoIdPedido++);
        pedidoService.setPedidoAtual(pedidoAtual);

        MenuUtil.exibirSucesso("Pedido criado! Cliente registrado com ID: " + cliente.getId());
        cliente.exibirDetalhes();
        MenuUtil.pausar();
    }

    private static void adicionarItem() {
        MenuUtil.exibirTitulo("Adicionar Item");

        if (pedidoAtual == null) {
            MenuUtil.exibirAviso("Nenhum pedido aberto. Crie um pedido primeiro (opção 1).");
            MenuUtil.pausar();
            return;
        }

        String nomeProduto = MenuUtil.lerStringNaoVazia("Nome do produto: ");
        double preco       = MenuUtil.lerDoublePositivo("Preço unitário (R$): ");
        int    quantidade  = MenuUtil.lerIntPositivo("Quantidade: ");

        try {
            // SOBRECARGA 2: adicionarItem(Pedido, Produto, int)
            clienteService.adicionarItem(pedidoAtual, new Produto(nomeProduto, preco), quantidade);
            MenuUtil.exibirSucesso("Item adicionado: " + nomeProduto
                    + " x" + quantidade + " = R$" + String.format("%.2f", preco * quantidade));
        } catch (IllegalArgumentException e) {
            MenuUtil.exibirErro(e.getMessage());
        }

        MenuUtil.pausar();
    }

    private static void atribuirEntregador() {
        MenuUtil.exibirTitulo("Atribuir Entregador");

        if (pedidoAtual == null) {
            MenuUtil.exibirAviso("Nenhum pedido aberto. Crie um pedido primeiro (opção 1).");
            MenuUtil.pausar();
            return;
        }

        Entregador entregador = new Entregador();
        entregador.setId(proximoIdEntregador++);
        entregador.setNome(MenuUtil.lerStringNaoVazia("Nome do entregador: "));
        entregador.setEmail(MenuUtil.lerStringNaoVazia("E-mail: "));
        entregador.setTelefone(MenuUtil.lerStringNaoVazia("Telefone: "));
        entregador.setVeiculo(MenuUtil.lerStringNaoVazia("Veículo: "));
        entregador.setStatus("DISPONÍVEL");

        entregadores.add(entregador);

        try {
            pedidoService.atribuirEntregador(pedidoAtual, entregador);
            MenuUtil.exibirSucesso("Entregador atribuído com sucesso!");
            entregador.exibirDetalhes();
        } catch (RuntimeException e) {
            MenuUtil.exibirErro(e.getMessage());
        }

        MenuUtil.pausar();
    }

    private static void finalizarPedido() {
        MenuUtil.exibirTitulo("Finalizar Pedido");

        if (pedidoAtual == null) {
            MenuUtil.exibirAviso("Nenhum pedido aberto. Crie um pedido primeiro (opção 1).");
            MenuUtil.pausar();
            return;
        }
        if (pedidoAtual.getItens().isEmpty()) {
            MenuUtil.exibirAviso("O pedido não possui itens. Adicione itens antes de finalizar.");
            MenuUtil.pausar();
            return;
        }
        if (!MenuUtil.confirmar("Confirma a finalização do pedido?")) {
            MenuUtil.exibirAviso("Finalização cancelada.");
            MenuUtil.pausar();
            return;
        }

        pedidoService.finalizarPedido(pedidoAtual);
        pedidos.add(pedidoAtual);

        MenuUtil.exibirSucesso("Pedido #" + pedidoAtual.getId() + " finalizado!");
        pedidoService.exibirResumo(pedidoAtual);

        pedidoAtual = null; // libera para novo pedido
        MenuUtil.pausar();
    }

    private static void exibirResumo() {
        MenuUtil.exibirTitulo("Resumo do Pedido");

        if (pedidoAtual == null) {
            MenuUtil.exibirAviso("Nenhum pedido em aberto.");
        } else {
            pedidoAtual.setSubtotal(pedidoAtual.calcularTotal());
            pedidoAtual.setDesconto(pedidoAtual.calcularDesconto());
            pedidoAtual.setValorTotal(pedidoAtual.calcularValorFinal());
            pedidoService.exibirResumo(pedidoAtual);
        }

        MenuUtil.pausar();
    }

    private static void exibirHistorico() {
        MenuUtil.exibirTitulo("Histórico do Pedido");

        if (pedidoAtual == null) {
            MenuUtil.exibirAviso("Nenhum pedido em aberto.");
        } else {
            System.out.println(pedidoAtual.obterHistorico());
        }

        MenuUtil.pausar();
    }

    // ==================== GESTÃO EM MEMÓRIA ====================

    private static void gerenciarClientes() {
        MenuUtil.exibirTitulo("Clientes");
        System.out.println("1. Listar clientes cadastrados");
        System.out.println("2. Buscar cliente por ID");
        System.out.println("3. Remover cliente");
        System.out.println("4. Demonstrar polimorfismo");
        System.out.println("0. Voltar");

        int op = MenuUtil.lerOpcao("Opção: ");
        switch (op) {
            case 1:
                if (clientes.isEmpty()) {
                    MenuUtil.exibirAviso("Nenhum cliente cadastrado ainda.");
                } else {
                    System.out.printf("%-5s %-25s %-30s%n", "ID", "Nome", "Email");
                    MenuUtil.exibirSeparador();
                    for (Cliente c : clientes) {
                        System.out.printf("%-5d %-25s %-30s%n",
                                c.getId(), c.getNome(), c.getEmail());
                    }
                }
                break;
            case 2:
                int idBusca = MenuUtil.lerIntPositivo("ID do cliente: ");
                Cliente encontrado = clientes.stream()
                        .filter(c -> c.getId() == idBusca)
                        .findFirst().orElse(null);
                if (encontrado != null) encontrado.exibirDetalhes();
                else MenuUtil.exibirAviso("Cliente não encontrado.");
                break;
            case 3:
                int idRemover = MenuUtil.lerIntPositivo("ID do cliente a remover: ");
                boolean removido = clientes.removeIf(c -> c.getId() == idRemover);
                if (removido) MenuUtil.exibirSucesso("Cliente removido.");
                else MenuUtil.exibirAviso("Cliente não encontrado.");
                break;
            case 4:
                demonstrarPolimorfismo();
                break;
        }
        MenuUtil.pausar();
    }

    private static void gerenciarEntregadores() {
        MenuUtil.exibirTitulo("Entregadores");
        System.out.println("1. Listar entregadores cadastrados");
        System.out.println("2. Buscar entregador por ID");
        System.out.println("3. Remover entregador");
        System.out.println("0. Voltar");

        int op = MenuUtil.lerOpcao("Opção: ");
        switch (op) {
            case 1:
                if (entregadores.isEmpty()) {
                    MenuUtil.exibirAviso("Nenhum entregador cadastrado ainda.");
                } else {
                    System.out.printf("%-5s %-25s %-20s %-12s%n", "ID", "Nome", "Veículo", "Status");
                    MenuUtil.exibirSeparador();
                    for (Entregador e : entregadores) {
                        System.out.printf("%-5d %-25s %-20s %-12s%n",
                                e.getId(), e.getNome(), e.getVeiculo(), e.getStatus());
                    }
                }
                break;
            case 2:
                int idBusca = MenuUtil.lerIntPositivo("ID do entregador: ");
                Entregador encontrado = entregadores.stream()
                        .filter(e -> e.getId() == idBusca)
                        .findFirst().orElse(null);
                if (encontrado != null) encontrado.exibirDetalhes();
                else MenuUtil.exibirAviso("Entregador não encontrado.");
                break;
            case 3:
                int idRemover = MenuUtil.lerIntPositivo("ID do entregador a remover: ");
                boolean removido = entregadores.removeIf(e -> e.getId() == idRemover);
                if (removido) MenuUtil.exibirSucesso("Entregador removido.");
                else MenuUtil.exibirAviso("Entregador não encontrado.");
                break;
        }
        MenuUtil.pausar();
    }

    private static void listarPedidos() {
        MenuUtil.exibirTitulo("Pedidos da Sessão");

        if (pedidos.isEmpty()) {
            MenuUtil.exibirAviso("Nenhum pedido finalizado nesta sessão.");
        } else {
            System.out.printf("%-5s %-20s %-12s %-12s%n", "ID", "Cliente", "Status", "Total");
            MenuUtil.exibirSeparador();
            for (Pedido p : pedidos) {
                System.out.printf("%-5d %-20s %-12s R$%-10.2f%n",
                        p.getId(),
                        p.getCliente().getNome(),
                        p.getStatus(),
                        p.getValorTotal());
            }
        }
        MenuUtil.pausar();
    }

    // ==================== POLIMORFISMO ====================

    /**
     * Demonstra ArrayList polimórfico: uma lista do tipo Pessoa
     * armazena objetos de Cliente e Entregador, e o método
     * exibirDetalhes() é chamado de forma polimórfica para cada um.
     */
    private static void demonstrarPolimorfismo() {
        System.out.println("========================================");
        System.out.println("  DEMONSTRAÇÃO — POLIMORFISMO");
        System.out.println("  ArrayList<Pessoa> com Cliente e Entregador");
        System.out.println("========================================");

        // ArrayList polimórfico: tipo Pessoa, objetos de subclasses diferentes
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Cliente("Ana Silva", "ana@email.com", "(11) 91111-1111", "Rua das Flores, 10"));
        pessoas.add(new Entregador("João Moto", "joao@email.com", "(11) 92222-2222", "Moto Honda CB 300"));
        pessoas.add(new Cliente("Carlos Souza", "carlos@email.com", "(11) 93333-3333", "Av. Paulista, 500"));
        pessoas.add(new Entregador("Pedro Bike", "pedro@email.com", "(11) 94444-4444", "Bicicleta Elétrica"));

        // Polimorfismo: cada objeto chama sua versão de exibirDetalhes() e exibirPermissoes()
        for (Pessoa p : pessoas) {
            p.exibirDetalhes();
            p.exibirPermissoes();
            System.out.println();
        }

        System.out.println("Total de pessoas: " + pessoas.size());
        System.out.println("Clientes:         " + pessoas.stream().filter(p -> p instanceof Cliente).count());
        System.out.println("Entregadores:     " + pessoas.stream().filter(p -> p instanceof Entregador).count());
    }

    // ==================== ENCERRAR ====================

    private static void encerrar() {
        MenuUtil.exibirTitulo("Encerrando Sistema");
        MenuUtil.fecharScanner();
        System.out.println("Sistema encerrado. Até logo! 👋");
    }
}
