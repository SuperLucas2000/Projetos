package br.com.delivery.util;

import java.util.Scanner;

/**
 * Classe utilitária para operações relacionadas ao menu e entrada de dados.
 *
 * Centraliza métodos auxiliares para:
 * - Exibição de menus formatados
 * - Leitura e validação de entrada do usuário
 * - Formatação de saída no console
 *
 * Conceitos aplicados:
 * - Utility Class: Métodos estáticos para funcionalidades comuns
 * - Validação de Entrada: Garante que dados inseridos sejam válidos
 * - Reutilização de Código: Evita duplicação em várias partes do sistema
 *
 * @author Projeto Integrado POO + BD
 * @version 2.0
 */
public class MenuUtil {

    private static final Scanner scanner = new Scanner(System.in);

    /** Construtor privado — classe utilitária, não deve ser instanciada. */
    private MenuUtil() {
    }

    // ==================== CABEÇALHO E MENUS ====================

    /** Exibe o cabeçalho principal do sistema. */
    public static void exibirCabecalho() {
        System.out.println("========================================");
        System.out.println("         FLASHFOOD  DELIVERY");
        System.out.println("========================================");
        System.out.println();
    }

    public static void exibirMenuPrincipal() {
        System.out.println("========================================");
        System.out.println("         FLASHFOOD  DELIVERY");
        System.out.println("========================================");
        System.out.println("1. Novo pedido");
        System.out.println("2. Adicionar item ao pedido");
        System.out.println("3. Atribuir entregador");
        System.out.println("4. Finalizar pedido");
        System.out.println("5. Resumo do pedido");
        System.out.println("6. Histórico do pedido");
        System.out.println("7. Clientes");
        System.out.println("8. Entregadores");
        System.out.println("9. Pedidos salvos");
        System.out.println("0. Sair");
        System.out.println("========================================");
    }

    // ==================== LEITURA DE DADOS ====================

    /**
     * Lê uma opção inteira com validação.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return Número inteiro digitado
     */
    public static int lerOpcao(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrada inválida! Digite um número inteiro.");
            }
        }
    }

    /**
     * Lê uma String do usuário.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return String digitada
     */
    public static String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    /**
     * Lê uma String não vazia, repetindo até o usuário digitar algo.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return String não vazia
     */
    public static String lerStringNaoVazia(String mensagem) {
        String valor;
        do {
            valor = lerString(mensagem);
            if (valor.isEmpty()) {
                System.out.println("⚠ Este campo não pode estar vazio!");
            }
        } while (valor.isEmpty());
        return valor;
    }

    /**
     * Lê um número inteiro positivo com validação.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return Inteiro positivo digitado
     */
    public static int lerIntPositivo(String mensagem) {
        int valor;
        do {
            valor = lerOpcao(mensagem);
            if (valor < 0) {
                System.out.println("⚠ O valor deve ser positivo!");
            }
        } while (valor < 0);
        return valor;
    }

    /**
     * Lê um número double com validação.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return Número double digitado
     */
    public static double lerDouble(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                return Double.parseDouble(scanner.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.println("⚠ Entrada inválida! Digite um número decimal.");
            }
        }
    }

    /**
     * Lê um double positivo com validação.
     *
     * @param mensagem Mensagem exibida ao usuário
     * @return Double positivo digitado
     */
    public static double lerDoublePositivo(String mensagem) {
        double valor;
        do {
            valor = lerDouble(mensagem);
            if (valor < 0) {
                System.out.println("⚠ O valor deve ser positivo!");
            }
        } while (valor < 0);
        return valor;
    }

    /**
     * Solicita confirmação S/N do usuário.
     *
     * @param mensagem Pergunta exibida ao usuário
     * @return true se confirmado, false caso contrário
     */
    public static boolean confirmar(String mensagem) {
        while (true) {
            System.out.print(mensagem + " (S/N): ");
            String resposta = scanner.nextLine().trim().toUpperCase();
            if (resposta.equals("S") || resposta.equals("SIM")) return true;
            if (resposta.equals("N") || resposta.equals("NAO") || resposta.equals("NÃO")) return false;
            System.out.println("⚠ Resposta inválida! Digite S ou N.");
        }
    }

    // ==================== FORMATAÇÃO DE SAÍDA ====================

    /** Pausa a execução e aguarda o usuário pressionar ENTER. */
    public static void pausar() {
        System.out.println();
        System.out.print("Pressione ENTER para continuar...");
        scanner.nextLine();
    }

    /** Exibe uma linha separadora. */
    public static void exibirSeparador() {
        System.out.println("========================================");
    }

    /**
     * Exibe um título formatado em destaque.
     *
     * @param titulo Título a ser exibido
     */
    public static void exibirTitulo(String titulo) {
        System.out.println();
        exibirSeparador();
        System.out.println("  " + titulo.toUpperCase());
        exibirSeparador();
        System.out.println();
    }

    /**
     * Exibe uma mensagem de sucesso.
     *
     * @param mensagem Mensagem a exibir
     */
    public static void exibirSucesso(String mensagem) {
        System.out.println("\n✓ " + mensagem + "\n");
    }

    /**
     * Exibe uma mensagem de erro.
     *
     * @param mensagem Mensagem a exibir
     */
    public static void exibirErro(String mensagem) {
        System.out.println("\n✗ " + mensagem + "\n");
    }

    /**
     * Exibe uma mensagem de aviso.
     *
     * @param mensagem Mensagem a exibir
     */
    public static void exibirAviso(String mensagem) {
        System.out.println("\n⚠ " + mensagem + "\n");
    }

    /** Fecha o Scanner. Deve ser chamado apenas ao encerrar o sistema. */
    public static void fecharScanner() {
        scanner.close();
    }
}
