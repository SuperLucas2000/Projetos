package br.com.delivery.service;

import br.com.delivery.model.Entregador;
import br.com.delivery.model.ItemPedido;
import br.com.delivery.model.Pedido;
import br.com.delivery.util.Relatorio;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Service responsável pelas regras de negócio relacionadas ao Pedido.
 * Implementa {@link Relatorio} para geração e exportação de resumos.
 *
 * Regras de negócio:
 * - Atribuição de entregador: só permite entregadores DISPONÍVEIS
 * - Cálculo delegado ao próprio Pedido (interface Calculavel)
 * - Finalização: calcula valores e muda status para "FINALIZADO"
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class PedidoService implements Relatorio {

    /** Pedido atual gerenciado por este service. */
    private Pedido pedidoAtual;

    /**
     * Define o pedido atual para geração de relatório.
     *
     * @param pedido Pedido a ser gerenciado
     */
    public void setPedidoAtual(Pedido pedido) {
        this.pedidoAtual = pedido;
    }

    /**
     * Atribui um entregador disponível ao pedido.
     * Altera o status do entregador para "EM_ENTREGA".
     *
     * @param pedido     Pedido que receberá o entregador
     * @param entregador Entregador a ser atribuído
     * @throws RuntimeException se o entregador não estiver disponível
     */
    public void atribuirEntregador(Pedido pedido, Entregador entregador) {
        boolean disponivel = entregador.getStatus().equalsIgnoreCase("DISPONÍVEL")
                || entregador.getStatus().equalsIgnoreCase("DISPONIVEL");

        if (!disponivel) {
            throw new RuntimeException("Entregador '" + entregador.getNome()
                    + "' está indisponível. Status: " + entregador.getStatus());
        }

        entregador.setStatus("EM_ENTREGA");
        pedido.setEntregador(entregador);
        pedido.setStatus("EM_ENTREGA");

        // Auditoria
        pedido.registrarLog("Entregador atribuído: " + entregador.getNome()
                + " | Veículo: " + entregador.getVeiculo());
    }

    /**
     * Finaliza o pedido: calcula os valores e muda o status para "FINALIZADO".
     *
     * @param pedido Pedido a ser finalizado
     */
    public void finalizarPedido(Pedido pedido) {
        double valorFinal = pedido.calcularValorFinal(); // usa interface Calculavel
        pedido.setValorTotal(valorFinal);
        pedido.setSubtotal(pedido.calcularTotal());
        pedido.setDesconto(pedido.calcularDesconto());
        pedido.setStatus("FINALIZADO");

        // Auditoria
        pedido.registrarLog("Pedido finalizado. Valor total: R$"
                + String.format("%.2f", valorFinal));
    }

    /**
     * Exibe o resumo financeiro de um pedido no console.
     *
     * @param pedido Pedido a exibir
     */
    public void exibirResumo(Pedido pedido) {
        System.out.println("========================================");
        System.out.println("         RESUMO DO PEDIDO");
        System.out.println("========================================");
        System.out.println("Cliente:       " + (pedido.getCliente() != null
                ? pedido.getCliente().getNome() : "N/A"));
        System.out.println("Status:        " + pedido.getStatus());
        System.out.println("----------------------------------------");
        System.out.println("Itens do pedido:");

        for (ItemPedido item : pedido.getItens()) {
            System.out.println("  • " + item);
        }

        System.out.println("----------------------------------------");
        System.out.printf("Subtotal:      R$ %.2f%n", pedido.getSubtotal());
        System.out.printf("Desconto:      R$ %.2f%n", pedido.getDesconto());
        System.out.printf("Taxa entrega:  R$ %.2f%n", pedido.getTaxaEntrega());
        System.out.println("----------------------------------------");
        System.out.printf("TOTAL FINAL:   R$ %.2f%n", pedido.getValorTotal());
        System.out.println("========================================");
    }

    // ==================== IMPLEMENTAÇÃO DE Relatorio ====================

    /**
     * @Override Gera e exibe o relatório do pedido atual no console.
     */
    @Override
    public void gerarRelatorio() {
        if (pedidoAtual == null) {
            System.out.println("⚠ Nenhum pedido definido para o relatório.");
            return;
        }
        exibirResumo(pedidoAtual);
        System.out.println();
        System.out.println(pedidoAtual.obterHistorico());
    }

    /**
     * @Override Exporta o relatório do pedido atual para um arquivo de texto.
     *
     * @param nomeArquivo Caminho/nome do arquivo de destino
     */
    @Override
    public void exportarParaArquivo(String nomeArquivo) {
        if (pedidoAtual == null) {
            System.out.println("⚠ Nenhum pedido definido para exportar.");
            return;
        }

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));

        StringBuilder sb = new StringBuilder();
        sb.append("========================================\n");
        sb.append("         RELATÓRIO DO PEDIDO\n");
        sb.append("Gerado em: ").append(timestamp).append("\n");
        sb.append("========================================\n");
        sb.append("Cliente:      ").append(pedidoAtual.getCliente() != null
                ? pedidoAtual.getCliente().getNome() : "N/A").append("\n");
        sb.append("Status:       ").append(pedidoAtual.getStatus()).append("\n");
        sb.append("----------------------------------------\n");
        sb.append("Itens:\n");

        for (ItemPedido item : pedidoAtual.getItens()) {
            sb.append("  • ").append(item).append("\n");
        }

        sb.append("----------------------------------------\n");
        sb.append(String.format("Subtotal:     R$ %.2f%n", pedidoAtual.getSubtotal()));
        sb.append(String.format("Desconto:     R$ %.2f%n", pedidoAtual.getDesconto()));
        sb.append(String.format("Taxa entrega: R$ %.2f%n", pedidoAtual.getTaxaEntrega()));
        sb.append(String.format("TOTAL FINAL:  R$ %.2f%n", pedidoAtual.getValorTotal()));
        sb.append("========================================\n");
        sb.append(pedidoAtual.obterHistorico());

        try (FileWriter fw = new FileWriter(nomeArquivo)) {
            fw.write(sb.toString());
            System.out.println("✓ Relatório exportado para: " + nomeArquivo);
        } catch (IOException e) {
            System.out.println("✗ Erro ao exportar relatório: " + e.getMessage());
        }
    }
}
