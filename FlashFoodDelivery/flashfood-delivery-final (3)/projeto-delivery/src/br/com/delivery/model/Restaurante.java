package br.com.delivery.model;

/**
 * Representa um Restaurante parceiro no sistema de delivery.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class Restaurante {

    /** Identificador único do restaurante. */
    private int id;

    /** Nome do restaurante. */
    private String nome;

    /** Endereço do restaurante. */
    private String endereco;

    /** Telefone de contato. */
    private String telefone;

    /** Construtor padrão. */
    public Restaurante() {
    }

    /**
     * Construtor sem ID.
     *
     * @param nome     Nome do restaurante
     * @param endereco Endereço
     * @param telefone Telefone
     */
    public Restaurante(String nome, String endereco, String telefone) {
        this.nome = nome;
        this.endereco = endereco;
        this.telefone = telefone;
    }

    // ==================== GETTERS E SETTERS ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEndereco() { return endereco; }
    public void setEndereco(String endereco) { this.endereco = endereco; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    /** Exibe os dados do restaurante no console. */
    public void exibirDetalhes() {
        System.out.println("========================================");
        System.out.println("           RESTAURANTE");
        System.out.println("========================================");
        System.out.println("ID:       " + id);
        System.out.println("Nome:     " + nome);
        System.out.println("Endereço: " + endereco);
        System.out.println("Telefone: " + telefone);
        System.out.println("========================================");
    }

    @Override
    public String toString() {
        return String.format("Restaurante [ID=%d, Nome=%s]", id, nome);
    }
}
