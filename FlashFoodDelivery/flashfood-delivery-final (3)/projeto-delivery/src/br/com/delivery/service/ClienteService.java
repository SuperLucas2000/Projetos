package br.com.delivery.service;

import br.com.delivery.model.Cliente;
import br.com.delivery.model.ItemPedido;
import br.com.delivery.model.Pedido;
import br.com.delivery.model.Produto;

/**
 * Service responsável pelas regras de negócio relacionadas ao Cliente.
 *
 * Demonstra SOBRECARGA DE MÉTODOS: adicionarItem() possui três assinaturas
 * diferentes (mesmo nome, parâmetros distintos).
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class ClienteService {

    /**
     * Cria um novo Pedido aberto para o cliente informado.
     *
     * @param cliente Cliente que está realizando o pedido
     * @return Pedido criado com status "ABERTO"
     */
    public Pedido criarPedido(Cliente cliente) {
        Pedido pedido = new Pedido();
        pedido.setCliente(cliente);
        pedido.setStatus("ABERTO");
        pedido.registrarLog("Pedido criado para o cliente: " + cliente.getNome());
        return pedido;
    }

    // ==================== SOBRECARGA: adicionarItem ====================

    /**
     * SOBRECARGA 1: Adiciona 1 unidade de um produto ao pedido.
     *
     * @param pedido  Pedido que receberá o item
     * @param produto Produto a ser adicionado (quantidade = 1)
     */
    public void adicionarItem(Pedido pedido, Produto produto) {
        adicionarItem(pedido, produto, 1);
    }

    /**
     * SOBRECARGA 2: Adiciona um produto com quantidade definida ao pedido.
     *
     * @param pedido     Pedido que receberá o item
     * @param produto    Produto a ser adicionado
     * @param quantidade Quantidade do produto
     * @throws IllegalArgumentException se a quantidade for menor ou igual a zero
     */
    public void adicionarItem(Pedido pedido, Produto produto, int quantidade) {
        if (quantidade <= 0) {
            throw new IllegalArgumentException("A quantidade deve ser maior que zero.");
        }
        ItemPedido item = new ItemPedido(produto, quantidade);
        pedido.getItens().add(item);
        pedido.registrarLog("Item adicionado: " + produto.getNome()
                + " x" + quantidade
                + " (R$" + String.format("%.2f", produto.getPreco()) + " un.)");
    }

    /**
     * SOBRECARGA 3: Adiciona um produto pelo nome e preço, criando-o na hora.
     *
     * @param pedido     Pedido que receberá o item
     * @param nomeProduto Nome do produto
     * @param preco       Preço unitário do produto
     * @param quantidade  Quantidade do produto
     */
    public void adicionarItem(Pedido pedido, String nomeProduto, double preco, int quantidade) {
        Produto produto = new Produto(nomeProduto, preco);
        adicionarItem(pedido, produto, quantidade);
    }
}
