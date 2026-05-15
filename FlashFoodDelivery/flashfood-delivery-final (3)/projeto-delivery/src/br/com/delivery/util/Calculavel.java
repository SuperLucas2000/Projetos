package br.com.delivery.util;

/**
 * Interface que define o contrato de cálculo financeiro para entidades do sistema.
 * Classes que implementam esta interface devem fornecer lógica de cálculo
 * de totais, descontos e valor final.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public interface Calculavel {

    /**
     * Calcula o valor total bruto (sem descontos).
     *
     * @return Valor total bruto
     */
    double calcularTotal();

    /**
     * Calcula o desconto aplicável ao total.
     *
     * @return Valor do desconto
     */
    double calcularDesconto();

    /**
     * Calcula o valor final (total - desconto).
     *
     * @return Valor final a pagar
     */
    double calcularValorFinal();
}
