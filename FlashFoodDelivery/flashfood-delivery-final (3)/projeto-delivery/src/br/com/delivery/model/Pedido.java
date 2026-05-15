package br.com.delivery.model;

import br.com.delivery.util.Auditavel;
import br.com.delivery.util.Calculavel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa um Pedido no sistema de delivery.
 *
 * Implementa as interfaces:
 * - {@link Auditavel}: para registrar o histórico de ações do pedido
 * - {@link Calculavel}: para calcular subtotal, desconto e valor final
 *
 * Regras de negócio (desconto progressivo):
 * - Subtotal > R$300 → 15% de desconto
 * - Subtotal > R$200 → 10% de desconto
 * - Subtotal > R$100 → 5% de desconto
 * - Subtotal <= R$100 → sem desconto
 * Taxa de entrega fixa: R$8,00
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class Pedido implements Auditavel, Calculavel {

    private static final double TAXA_ENTREGA = 8.0;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    /** Identificador único do pedido. */
    private int id;

    /** Cliente que realizou o pedido. */
    private Cliente cliente;

    /** Entregador responsável pelo pedido. */
    private Entregador entregador;

    /** Lista de itens do pedido. */
    private List<ItemPedido> itens;

    /** Subtotal bruto (sem desconto e sem taxa). */
    private double subtotal;

    /** Valor do desconto aplicado. */
    private double desconto;

    /** Taxa de entrega. */
    private double taxaEntrega;

    /** Valor total final a pagar. */
    private double valorTotal;

    /**
     * Status atual do pedido.
     * Valores: "ABERTO", "EM_PREPARO", "EM_ENTREGA", "FINALIZADO", "CANCELADO"
     */
    private String status;

    /** Histórico de ações auditadas neste pedido. */
    private List<String> historico;

    /** Construtor padrão. Inicializa listas e status. */
    public Pedido() {
        this.itens = new ArrayList<>();
        this.historico = new ArrayList<>();
        this.status = "ABERTO";
        this.taxaEntrega = TAXA_ENTREGA;
    }

    // ==================== IMPLEMENTAÇÃO DE Auditavel ====================

    /**
     * @Override Registra uma ação no histórico do pedido com timestamp.
     *
     * @param acao Descrição da ação realizada
     */
    @Override
    public void registrarLog(String acao) {
        String log = LocalDateTime.now().format(FORMATTER) + " - " + acao;
        historico.add(log);
        System.out.println("📋 Log registrado: " + log);
    }

    /**
     * @Override Retorna o histórico completo de ações do pedido.
     *
     * @return String formatada com todo o histórico
     */
    @Override
    public String obterHistorico() {
        StringBuilder sb = new StringBuilder("=== HISTÓRICO DO PEDIDO ===\n");
        if (historico.isEmpty()) {
            sb.append("Nenhuma ação registrada.\n");
        } else {
            for (String log : historico) {
                sb.append("  • ").append(log).append("\n");
            }
        }
        return sb.toString();
    }

    // ==================== IMPLEMENTAÇÃO DE Calculavel ====================

    /**
     * @Override Calcula o subtotal somando (preço × quantidade) de todos os itens.
     *
     * @return Subtotal bruto
     */
    @Override
    public double calcularTotal() {
        double total = 0;
        for (ItemPedido item : itens) {
            total += item.calcularSubtotalItem();
        }
        this.subtotal = total;
        return subtotal;
    }

    /**
     * @Override Calcula o desconto com base no subtotal (regra de negócio progressiva).
     *
     * - Subtotal > R$300 → 15%
     * - Subtotal > R$200 → 10%
     * - Subtotal > R$100 → 5%
     * - Subtotal <= R$100 → 0%
     *
     * @return Valor do desconto
     */
    @Override
    public double calcularDesconto() {
        double sub = calcularTotal();
        if (sub > 300) {
            this.desconto = sub * 0.15;
        } else if (sub > 200) {
            this.desconto = sub * 0.10;
        } else if (sub > 100) {
            this.desconto = sub * 0.05;
        } else {
            this.desconto = 0;
        }
        return desconto;
    }

    /**
     * @Override Calcula o valor final: subtotal - desconto + taxaEntrega.
     *
     * @return Valor final a pagar
     */
    @Override
    public double calcularValorFinal() {
        double sub = calcularTotal();
        double desc = calcularDesconto();
        this.valorTotal = sub - desc + TAXA_ENTREGA;
        return valorTotal;
    }

    // ==================== GETTERS E SETTERS ====================

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Entregador getEntregador() { return entregador; }
    public void setEntregador(Entregador entregador) { this.entregador = entregador; }

    public List<ItemPedido> getItens() { return itens; }
    public void setItens(List<ItemPedido> itens) { this.itens = itens; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public double getDesconto() { return desconto; }
    public void setDesconto(double desconto) { this.desconto = desconto; }

    public double getTaxaEntrega() { return taxaEntrega; }
    public void setTaxaEntrega(double taxaEntrega) { this.taxaEntrega = taxaEntrega; }

    public double getValorTotal() { return valorTotal; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<String> getHistoricoLista() { return historico; }

    @Override
    public String toString() {
        return String.format("Pedido [ID=%d, Cliente=%s, Status=%s, Total=R$%.2f]",
                id, cliente != null ? cliente.getNome() : "N/A", status, valorTotal);
    }
}
