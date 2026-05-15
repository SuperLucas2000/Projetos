package br.com.delivery.model;

/**
 * Representa um produto disponível para pedido no sistema de delivery.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class Produto {

    /** Identificador único do produto. */
    private int id;

    /** Nome do produto. */
    private String nome;

    /** Preço unitário do produto. */
    private double preco;

    /** Construtor padrão. */
    public Produto() {
    }

    /**
     * Construtor sem ID (usado ao criar novos produtos).
     *
     * @param nome  Nome do produto
     * @param preco Preço unitário
     */
    public Produto(String nome, double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    /**
     * Construtor completo.
     *
     * @param id    Identificador único
     * @param nome  Nome do produto
     * @param preco Preço unitário
     */
    public Produto(int id, String nome, double preco) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
    }

    // ==================== GETTERS E SETTERS ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public double getPreco() { return preco; }
    public void setPreco(double preco) { this.preco = preco; }

    @Override
    public String toString() {
        return String.format("Produto [ID=%d, Nome=%s, Preço=R$%.2f]", id, nome, preco);
    }
}
