package br.com.delivery.model;

/**
 * Representa um item dentro de um Pedido.
 * Associa um Produto a uma quantidade solicitada.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class ItemPedido {

    /** Produto referenciado por este item. */
    private Produto produto;

    /** Quantidade do produto neste item. */
    private int quantidade;

    /** Construtor padrão. */
    public ItemPedido() {
    }

    /**
     * Construtor completo.
     *
     * @param produto    Produto do item
     * @param quantidade Quantidade solicitada
     */
    public ItemPedido(Produto produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    // ==================== GETTERS E SETTERS ====================

    public Produto getProduto() { return produto; }
    public void setProduto(Produto produto) { this.produto = produto; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    /**
     * Calcula o subtotal deste item (preço × quantidade).
     *
     * @return Subtotal do item
     */
    public double calcularSubtotalItem() {
        return produto.getPreco() * quantidade;
    }

    @Override
    public String toString() {
        return String.format("%s x%d = R$%.2f",
                produto.getNome(), quantidade, calcularSubtotalItem());
    }
}
