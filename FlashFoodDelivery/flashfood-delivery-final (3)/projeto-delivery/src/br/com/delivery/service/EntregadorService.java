package br.com.delivery.service;

import br.com.delivery.model.Entregador;

/**
 * Service responsável pelas regras de negócio relacionadas ao Entregador.
 *
 * Operações disponíveis:
 * - Verificar disponibilidade
 * - Iniciar entrega
 * - Finalizar entrega
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class EntregadorService {

    /**
     * Verifica se o entregador está disponível para novas entregas.
     *
     * @param entregador Entregador a verificar
     * @return true se disponível, false caso contrário
     */
    public boolean estaDisponivel(Entregador entregador) {
        return entregador.getStatus().equalsIgnoreCase("DISPONÍVEL")
                || entregador.getStatus().equalsIgnoreCase("DISPONIVEL");
    }

    /**
     * Inicia a entrega, alterando o status do entregador para "EM_ENTREGA".
     *
     * @param entregador Entregador que iniciará a entrega
     * @throws RuntimeException se o entregador não estiver disponível
     */
    public void iniciarEntrega(Entregador entregador) {
        if (!estaDisponivel(entregador)) {
            throw new RuntimeException("Entregador '" + entregador.getNome()
                    + "' não está disponível. Status atual: " + entregador.getStatus());
        }
        entregador.setStatus("EM_ENTREGA");
    }

    /**
     * Finaliza a entrega, tornando o entregador disponível novamente.
     *
     * @param entregador Entregador que finalizou a entrega
     */
    public void finalizarEntrega(Entregador entregador) {
        entregador.setStatus("DISPONÍVEL");
    }
}
