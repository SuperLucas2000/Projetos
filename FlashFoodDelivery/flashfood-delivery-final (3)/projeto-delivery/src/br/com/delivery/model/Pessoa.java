package br.com.delivery.model;

/**
 * Superclasse ABSTRATA que representa uma Pessoa no sistema.
 *
 * Demonstra os conceitos de POO:
 * - Encapsulamento: atributos protected com getters/setters
 * - Abstração: classe abstrata com método abstrato
 * - Herança: base para Cliente e Entregador
 *
 * Por ser abstrata, não pode ser instanciada diretamente.
 * Toda subclasse DEVE implementar exibirPermissoes().
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public abstract class Pessoa {

    /** Identificador único (chave primária no banco de dados). */
    protected int id;

    /** Nome completo. */
    protected String nome;

    /** Endereço de e-mail. */
    protected String email;

    /** Número de telefone. */
    protected String telefone;

    /** Construtor padrão (sem parâmetros). */
    public Pessoa() {
    }

    /**
     * Construtor completo.
     *
     * @param id       Identificador único
     * @param nome     Nome completo
     * @param email    E-mail
     * @param telefone Telefone
     */
    public Pessoa(int id, String nome, String email, String telefone) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    /**
     * Construtor sem ID (usado ao criar novos registros).
     * O ID será gerado pelo banco de dados.
     *
     * @param nome     Nome completo
     * @param email    E-mail
     * @param telefone Telefone
     */
    public Pessoa(String nome, String email, String telefone) {
        this.nome = nome;
        this.email = email;
        this.telefone = telefone;
    }

    // ==================== GETTERS E SETTERS ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefone() { return telefone; }
    public void setTelefone(String telefone) { this.telefone = telefone; }

    // ==================== MÉTODOS CONCRETOS ====================

    /**
     * Exibe os dados básicos da Pessoa no console.
     * Pode ser sobrescrito pelas subclasses para adicionar informações extras.
     */
    public void exibirDetalhes() {
        System.out.println("========================================");
        System.out.println("         DETALHES DA PESSOA");
        System.out.println("========================================");
        System.out.println("ID:       " + id);
        System.out.println("Nome:     " + nome);
        System.out.println("Email:    " + email);
        System.out.println("Telefone: " + telefone);
    }

    @Override
    public String toString() {
        return String.format("Pessoa [ID=%d, Nome=%s, Email=%s, Telefone=%s]",
                id, nome, email, telefone);
    }

    // ==================== MÉTODO ABSTRATO ====================

    /**
     * Método ABSTRATO: cada subclasse define suas próprias permissões/papel.
     * Obrigatório implementar em Cliente e Entregador.
     */
    public abstract void exibirPermissoes();
}
