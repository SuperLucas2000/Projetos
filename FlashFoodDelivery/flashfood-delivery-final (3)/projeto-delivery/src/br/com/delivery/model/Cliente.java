package br.com.delivery.model;

/**
 * Subclasse Cliente que herda de Pessoa (abstrata).
 *
 * Representa um cliente do sistema de delivery.
 * Demonstra Herança, Polimorfismo (@Override) e implementação
 * de método abstrato (exibirPermissoes).
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class Cliente extends Pessoa {

    /** Endereço de entrega do cliente. */
    private String endereco;

    /** Construtor padrão. */
    public Cliente() {
        super();
    }

    /**
     * Construtor completo.
     *
     * @param nome     Nome do cliente
     * @param email    E-mail do cliente
     * @param telefone Telefone do cliente
     * @param endereco Endereço de entrega
     */
    public Cliente(String nome, String email, String telefone, String endereco) {
        super(nome, email, telefone);
        this.endereco = endereco;
    }

    // ==================== GETTERS E SETTERS ====================

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    // ==================== MÉTODOS ====================

    /**
     * @Override Exibe os detalhes do cliente, incluindo endereço de entrega.
     * Chama super.exibirDetalhes() e adiciona informações específicas.
     */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Endereço:  " + endereco);
        System.out.println("Papel:     Cliente");
        System.out.println("========================================");
    }

    /**
     * @Override Implementação obrigatória do método abstrato de Pessoa.
     * Define o papel e as permissões do cliente no sistema.
     */
    @Override
    public void exibirPermissoes() {
        System.out.println("========================================");
        System.out.println("  PERMISSÕES — CLIENTE");
        System.out.println("========================================");
        System.out.println("✓ Realizar pedidos");
        System.out.println("✓ Acompanhar status do pedido");
        System.out.println("✓ Visualizar histórico de pedidos");
        System.out.println("✗ Gerenciar entregadores");
        System.out.println("✗ Acessar painel administrativo");
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format("Cliente [ID=%d, Nome=%s, Endereço=%s]",
                getId(), getNome(), endereco);
    }
}
