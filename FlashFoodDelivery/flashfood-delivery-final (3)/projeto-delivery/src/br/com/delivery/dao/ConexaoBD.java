package br.com.delivery.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe responsável pela conexão com o banco de dados PostgreSQL via JDBC.
 *
 * Utiliza o padrão Singleton para garantir uma única instância de conexão
 * durante toda a execução do sistema.
 *
 * Configuração padrão:
 * - Banco:    PostgreSQL
 * - Host:     localhost:5432
 * - Database: flashfood_db
 * - Usuário:  postgres
 * - Senha:    postgres
 *
 * Para alterar, modifique as constantes abaixo ou crie um arquivo .env.
 *
 * @author FlashFood — Projeto Integrado POO + BD
 * @version 3.0
 */
public class ConexaoBD {

    private static final String URL      = "jdbc:postgresql://localhost:5432/flashfood_db";
    private static final String USUARIO  = "postgres";
    private static final String SENHA    = "postgres";

    /** Única instância de conexão (Singleton). */
    private static Connection conexao = null;

    /** Construtor privado — impede instanciação direta. */
    private ConexaoBD() {
    }

    /**
     * Retorna a conexão ativa com o banco de dados.
     * Cria uma nova conexão se ainda não existir ou se a atual estiver fechada.
     *
     * @return Connection ativa com o PostgreSQL
     * @throws RuntimeException se não conseguir conectar
     */
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                // Carrega o driver JDBC do PostgreSQL
                Class.forName("org.postgresql.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
                System.out.println("✓ Conexão com o banco de dados estabelecida.");
            }
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(
                "Driver PostgreSQL não encontrado. Adicione o JAR do driver ao classpath.\n" + e.getMessage());
        } catch (SQLException e) {
            throw new RuntimeException(
                "Erro ao conectar ao banco de dados: " + e.getMessage());
        }
        return conexao;
    }

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     * Deve ser chamado ao encerrar o sistema.
     */
    public static void fecharConexao() {
        if (conexao != null) {
            try {
                if (!conexao.isClosed()) {
                    conexao.close();
                    System.out.println("✓ Conexão com o banco de dados encerrada.");
                }
            } catch (SQLException e) {
                System.out.println("⚠ Erro ao fechar conexão: " + e.getMessage());
            }
        }
    }
}
