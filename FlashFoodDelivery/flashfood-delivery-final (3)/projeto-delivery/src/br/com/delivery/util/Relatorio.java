package br.com.delivery.util;

/**
 * Interface que define o contrato de geração de relatórios.
 * Classes que implementam esta interface devem ser capazes de
 * gerar e exportar seus dados em formato de relatório.
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public interface Relatorio {

    /**
     * Exibe o relatório formatado no console.
     */
    void gerarRelatorio();

    /**
     * Exporta o relatório para um arquivo de texto.
     *
     * @param nomeArquivo Caminho/nome do arquivo de destino
     */
    void exportarParaArquivo(String nomeArquivo);
}
