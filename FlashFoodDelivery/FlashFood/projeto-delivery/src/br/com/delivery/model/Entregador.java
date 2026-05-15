package br.com.delivery.model;

/**
 * Subclasse Entregador que herda de Pessoa (abstrata).
 *
 * Representa um entregador do sistema de delivery.
 * Demonstra Herança, Polimorfismo (@Override) e implementação
 * de método abstrato (exibirPermissoes).
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class Entregador extends Pessoa {

    /** Veículo utilizado para as entregas. */
    private String veiculo;

    /**
     * Status atual do entregador.
     * Valores possíveis: "DISPONÍVEL", "EM_ENTREGA"
     */
    private String status;

    /** Construtor padrão. Define status inicial como DISPONÍVEL. */
    public Entregador() {
        super();
        this.status = "DISPONÍVEL";
    }

    /**
     * Construtor completo.
     *
     * @param nome     Nome do entregador
     * @param email    E-mail do entregador
     * @param telefone Telefone do entregador
     * @param veiculo  Veículo do entregador
     */
    public Entregador(String nome, String email, String telefone, String veiculo) {
        super(nome, email, telefone);
        this.veiculo = veiculo;
        this.status = "DISPONÍVEL";
    }

    // ==================== GETTERS E SETTERS ====================

    public String getVeiculo() { return veiculo; }
    public void setVeiculo(String veiculo) { this.veiculo = veiculo; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    // ==================== MÉTODOS ====================

    /**
     * @Override Exibe os detalhes do entregador, incluindo veículo e status.
     * Chama super.exibirDetalhes() e adiciona informações específicas.
     */
    @Override
    public void exibirDetalhes() {
        super.exibirDetalhes();
        System.out.println("Veículo:  " + veiculo);
        System.out.println("Status:   " + status);
        System.out.println("Papel:    Entregador");
        System.out.println("========================================");
    }

    /**
     * @Override Implementação obrigatória do método abstrato de Pessoa.
     * Define o papel e as permissões do entregador no sistema.
     */
    @Override
    public void exibirPermissoes() {
        System.out.println("========================================");
        System.out.println("  PERMISSÕES — ENTREGADOR");
        System.out.println("========================================");
        System.out.println("✓ Aceitar entregas atribuídas");
        System.out.println("✓ Atualizar status da entrega");
        System.out.println("✓ Visualizar endereço de entrega");
        System.out.println("✗ Realizar pedidos");
        System.out.println("✗ Acessar painel administrativo");
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format("Entregador [ID=%d, Nome=%s, Veículo=%s, Status=%s]",
                getId(), getNome(), veiculo, status);
    }
}
