package br.com.delivery.util;

/**
 * Interface que define o contrato de auditoria para entidades do sistema.
 * Qualquer classe que implemente esta interface deve ser capaz de
 * registrar logs de ações e fornecer seu histórico.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public interface Auditavel {

    /**
     * Registra uma ação no histórico da entidade.
     *
     * @param acao Descrição da ação realizada
     */
    void registrarLog(String acao);

    /**
     * Retorna o histórico completo de ações da entidade.
     *
     * @return String formatada com todas as ações registradas
     */
    String obterHistorico();
}
